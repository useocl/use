#########################################
# Fixing Functions #
#########################################

function Check-And-Fix-Bag-File {
    $bagFile = Get-ChildItem -Recurse -Filter "Bag.java" | Select-Object -First 1

    if ($bagFile) {   
    $content = Get-Content $bagFile.FullName -Raw
    $needsFixing = $content -match 'boolean contains\(T obj\)' -or
                   $content -match 'boolean remove\(T o\)' -or
                   (-not ($content -match '@Override.*boolean contains\(Object obj\)' -or
                         $content -match '@Override.*boolean remove\(Object o\)'))

        if ($needsFixing) {
            Log-Message "Faulty Bag interface - Applying fix..."
            $content = $content -replace 'boolean contains\(T obj\)', 'boolean contains(Object obj)'
            $content = $content -replace 'boolean remove\(T o\)', 'boolean remove(Object o)'
            $content | Set-Content $bagFile.FullName
        }
    }
}

function Check-And-Fix-DiagramView-File {
    $diagramViewFile = Get-ChildItem -Recurse -Filter "DiagramView.java" | Select-Object -First 1

    if ($diagramViewFile) {   
    $content = Get-Content $diagramViewFile.FullName -Raw
    $needsFixing = $content -match 'new OutputFormat\(doc\);'

        if ($needsFixing) {
            Log-Message "Faulty DiagramView file - Applying fix..."
            $content = $content -replace 'new OutputFormat\(doc\);', 'new OutputFormat();'
            $content | Set-Content $diagramViewFile.FullName
        }
    }
}

function Check-And-Fix-ExtendedJTable-File {
    $extendedJTableFile = Get-ChildItem -Recurse -Filter "ExtendedJTable.java" | Select-Object -First 1

    if ($extendedJTableFile) {
        $content = Get-Content $extendedJTableFile.FullName -Raw
        $needsFixing = $content -match 'public ExtendedJTable\(Vector\<\?\> rowData\, Vector\<\?\> columnNames\)'

        if ($needsFixing) {
            Log-Message "Faulty ExtendedJTable constructor - Applying fix..."
            $content = $content -replace 'public ExtendedJTable\(Vector\<\?\> rowData\, Vector\<\?\> columnNames\)', 'public ExtendedJTable(Vector<? extends Vector<?>> rowData, Vector<?> columnNames)'
            $content | Set-Content $extendedJTableFile.FullName
        }
    }
}

function Remove-Id-Tags {
    Get-ChildItem -Recurse -Filter *.java | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        if ($content -match '\$Id\$') {
            $content = $content -replace '\$Id\$', ''
            $content | Set-Content $_.FullName
        }
    }
    if (Test-Path "build.xml") {
        $content = Get-Content "build.xml" -Raw
        if ($content -match '<!--\$Id\$ -->') {
            $content = $content -replace '<!--\$Id\$-->\s*\n', ''
            $content | Set-Content "build.xml"
        }
    }
}

#########################################
# Dependency Functions #
#########################################

# Downloads necessary jar-Files from Maven Central into a specified dir
function Download-Jar {
    param(
        [string]$GroupId,
        [string]$ArtifactId,
        [string]$Version,
        [string]$TargetDir
    )
    
    $groupPath = $GroupId -replace '\.', '/'
    $jarName = "$ArtifactId-$Version.jar"
    $url = "https://repo1.maven.org/maven2/$groupPath/$ArtifactId/$Version/$jarName"
    $targetPath = Join-Path $TargetDir $jarName
    
    try {
        Invoke-WebRequest -Uri $url -OutFile $targetPath -UseBasicParsing
        Log-Message "Successfully downloaded $jarName"
        return $targetPath
    }
    catch {
        Log-Message "Failed to download $jarName`: $_"
        exit 1
    }
}

# Locally creates shared dependencies dir for all commits and calls Download-Jar
# This avoids downloading jars for every commit
function Setup-Dependencies-In-Shared-Dir {
    param(
        [string]$ResultsDir
    )
    
    # Create a shared lib directory for downloads
    $shared_dep_dir = Join-Path $ResultsDir "dependencies"
    if (-not (Test-Path $shared_dep_dir)) {
        New-Item -ItemType Directory -Force -Path $shared_dep_dir | Out-Null
    }

    # Download dependencies once and store paths in global variables
    $script:ARCHUNIT_JUNIT_PATH = Download-Jar "com.tngtech.archunit" "archunit-junit4" "0.23.1" $shared_dep_dir
    $script:ARCHUNIT_CORE_PATH = Download-Jar "com.tngtech.archunit" "archunit" "0.23.1" $shared_dep_dir
    $script:SLF4J_API_PATH = Download-Jar "org.slf4j" "slf4j-api" "1.7.25" $shared_dep_dir
    $script:SLF4J_SIMPLE_PATH = Download-Jar "org.slf4j" "slf4j-nop" "1.7.25" $shared_dep_dir
    
    Log-Message "Dependencies setup completed"
}

# Copies pre-downloaded dependencies to a commit's lib directory
function Add-Dependencies-To-Lib {
    param(
        [string]$TempDir
    )
    $lib_dir = Join-Path $TempDir "lib"
    if (-not (Test-Path $lib_dir)) {
        New-Item -ItemType Directory -Force -Path $lib_dir | Out-Null
    }

    Copy-Item $script:ARCHUNIT_JUNIT_PATH (Join-Path $lib_dir "archunit-junit4-0.23.1.jar")
    Copy-Item $script:ARCHUNIT_CORE_PATH (Join-Path $lib_dir "archunit-0.23.1.jar")
    Copy-Item $script:SLF4J_API_PATH (Join-Path $lib_dir "slf4j-api-1.7.25.jar")
    Copy-Item $script:SLF4J_SIMPLE_PATH (Join-Path $lib_dir "slf4j-simple-1.7.25.jar")
    
    Log-Message "Copied ArchUnit & SLF4J dependencies to lib directory"
}

# Creates new architecture test dir, new ArchTest file and copies pre-stored test content into that file
function Inject-ArchUnit-Test {
    param(
        [string]$TempDir,
        [string]$ArchTestName,
        [string]$ArchTestContent
    )
    $arch_test_dir = Join-Path $TempDir "src\test\org\tzi\use\architecture"
    if (-not (Test-Path $arch_test_dir)) {
        New-Item -ItemType Directory -Force -Path $arch_test_dir | Out-Null
        Log-Message "Created directory: $arch_test_dir"
    }

    $new_test_file = Join-Path $arch_test_dir $ArchTestName
    $utf8NoBomEncoding = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($new_test_file, $ArchTestContent, $utf8NoBomEncoding)
    if (Test-Path $new_test_file) {
        Log-Message "Successfully added test to commit"
    }
}

#########################################
# Debugging Functions #
#########################################

function Detect-Original-Java-Version {
    if (Test-Path "build.xml") {
        $buildXml = Get-Content "build.xml" -Raw
        
        # Check for source version pattern
        if ($buildXml -match 'source="(1\.)?(\d+)"') {
            $sourceVersion = $matches[2]
            Log-Message "Original Java source version detected: $sourceVersion"
        }
        elseif ($buildXml -match 'java\.sourceversion"\s+value="1\.(\d+)"') {
            $sourceVersion = $matches[1]
            Log-Message "Original Java source version detected: $sourceVersion"
        }
        elseif ($buildXml -match 'value="1\.(\d+)"') {
            $sourceVersion = $matches[1]
            Log-Message "Original Java version detected: $sourceVersion"
        }
        else {
            Log-Message "Could not detect Java version from build.xml"
        }
        
        # Also check if ANTLR version is specified
        if ($buildXml -match 'antlr-([\d\.]+)\.jar') {
            $antlrVersion = $matches[1]
            Log-Message "ANTLR version detected: $antlrVersion"
        }
    }
    else {
        Log-Message "build.xml not found, cannot detect Java version"
    }
}

function Print-BuildXml {
    if (Test-Path "build.xml") {
        Write-Host "Contents of build.xml:"
        Get-Content "build.xml" | ForEach-Object { Write-Host $_ }
    } else {
        Write-Host "build.xml not found in the current directory."
    }
}

#########################################
# Updating Functions #
#########################################

# Update to Java 8 because 6 is not compatible with ArchUnit and to increase backwards compatibility
function Update-Java-Version {
    $buildXml = Get-Content "build.xml" -Raw

    $buildXml = $buildXml -replace 'source="(1\.)?(\d+)"', 'source="8"'
    $buildXml = $buildXml -replace 'target="(1\.)?(\d+)"', 'target="8"'
    $buildXml = $buildXml -replace '(java\.targetversion"\s+value=")1\.?\d+(")', 'java.targetversion" value="8"'
    $buildXml = $buildXml -replace '(java\.sourceversion"\s+value=")1\.?\d+(")', 'java.sourceversion" value="8"'
    $buildXml = $buildXml -replace 'value="1\.[0-7]+"', 'value="8"'
    $buildXml = $buildXml -replace 'value="1\.[0-7]+\s*"', 'value="8"'
    
    $buildXml | Set-Content "build.xml"

    $content = Get-Content "build.xml" -Raw
    if ($content -match 'value="8"') {
        Log-Message "Successfully updated Java version to 8"
    } else {
        Log-Message "Warning: Java version update may not have worked"
    }
}

# Add necessary archunit and slf4j dependencies to build.xml
function Update-Dependencies-In-Buildxml {
    $buildXml = Get-Content "build.xml" -Raw
    
    $buildXml = $buildXml -replace '(<target name="test-classcycles".*?>)[\s\S]*?</target>', '$1</target>'
    
    if ($buildXml -match "archunit-junit4-0.23.1.jar|archunit-0.23.1.jar") {
        Log-Message "ArchUnit dependencies already present in build.xml"
        return
    }
    
    $propertyInsert = @"
    <property name="archunit.jar" location="`${lib.dir}/archunit-0.23.1.jar"/>
    <property name="archunit-junit4.jar" location="`${lib.dir}/archunit-junit4-0.23.1.jar"/>
    <property name="slf4j-api.jar" location="`${lib.dir}/slf4j-api-1.7.25.jar"/>
    <property name="slf4j-simple.jar" location="`${lib.dir}/slf4j-simple-1.7.25.jar"/>
    <property name="src.test.resources.dir" location="`${src.test.dir}/resources"/>
"@

    $buildXml = $buildXml -replace '(<property name="guava.jar".*?/>)', "`$1`n$propertyInsert"
    $buildXml = $buildXml -replace '(<property name="combinatoricslib\.jar".*?/>)', "`$1`n$propertyInsert"

    $newDeps = ':${archunit.jar}:${archunit-junit4.jar}:${slf4j-api.jar}:${slf4j-simple.jar}:${src.test.resources.dir}'
    
    $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\}:\$\{combinatoricslib\.jar\}:\$\{vtd-xml\.jar\}:\$\{itextpdf\.jar\})', "`$1$newDeps"
    $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\}:\$\{combinatoricslib\.jar\})', "`$1$newDeps"
    
    $buildXml = $buildXml -replace '(classpath path="\$\{antlr\.jar\}:\$\{build\.classes\.dir\}:\$\{gsbase\.jar\}:\$\{jruby\.jar\}:\$\{junit\.jar\}:\$\{guava\.jar\})', "`$1$newDeps"
    $buildXml = $buildXml -replace '(classpath path="\$\{antlr\.jar\}:\$\{build\.classes\.dir\}:\$\{gsbase\.jar\}:\$\{jruby\.jar\}:\$\{junit\.jar\})', "`$1$newDeps"
    
    $buildXml | Set-Content "build.xml" -Force
    Log-Message "Successfully added ArchUnit dependencies to build.xml"
}

#########################################
# Adding Missing Build.xml Contents Functions #
#########################################

# Add a test-junit target if it doesn't exist
function Add-Missing-Test-Target-To-Buildxml {
    $buildXml = Get-Content "build.xml" -Raw
    
    if ($buildXml -notmatch '<target\s+name="test-junit"') {
        $testJunitTarget = @"
    <target name="test-junit" depends="build" description="Runs the JUnit tests">
        <junit fork="yes" haltonfailure="no" printsummary="yes">
            <classpath>
                <pathelement location="`${build.classes.dir}"/>
                <pathelement path="`${archunit.jar}:`${archunit-junit4.jar}:`${slf4j-api.jar}:`${slf4j-simple.jar}"/>
                <pathelement path="`${junit.jar}"/>
                <!-- Add other classpath elements as needed -->
            </classpath>
            <formatter type="plain"/>
            <test name="`${test.case}" if="test.case"/>
            <batchtest todir="`${build.dir}" unless="test.case">
                <fileset dir="`${src.test.dir}">
                    <include name="**/*Test.java"/>
                </fileset>
            </batchtest>
        </junit>
    </target>
"@
        
        # Add test target before closing bracket of project
        if ($buildXml -match '</project>') {
            $buildXml = $buildXml -replace '</project>', "$testJunitTarget`n</project>"
            $buildXml | Set-Content "build.xml" -Force
            Log-Message "Added test-junit target to build.xml"
        }
    }
}

# Adds missing reference of combinatoricslib in build.xml, relies on it being present in the lib dir
function Add-CombinatoricsLib-If-Missing {
    $buildXml = Get-Content "build.xml" -Raw
    
    if ($buildXml -notmatch "combinatoricslib\.jar") {
        $propertyLine = '  <property name="combinatoricslib.jar" location="${lib.dir}/combinatoricslib-0.2.jar"/>'
        $buildXml = $buildXml -replace '(<property name="jruby\.jar".*?/>)', "`$1`n$propertyLine"
        
        $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{jruby\.jar\})"', '$1:${combinatoricslib.jar}"'
        $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\})"', '$1:${combinatoricslib.jar}"'
        
        $buildXml | Set-Content "build.xml" -Force
        Log-Message "Added combinatoricslib dependency to build.xml"
    }
}