. "..\general_utilities.ps1"

#########################################
# Dependency Functions #
#########################################

function Ensure-Dependencies-For-Maven {
    param(
        [string]$TempDir
    )

    $pom_files = @("pom.xml", "use-core/pom.xml", "use-gui/pom.xml")
    
    foreach ($pom_file in $pom_files) {
        $full_path = Join-Path $TempDir $pom_file
        if (Test-Path $full_path) {
            Log-Message "Checking and adding dependencies in $full_path"
            $pom = [xml](Get-Content $full_path)

            if ($null -eq $pom.project) {
                Log-Message "Project node not found in $full_path. Exiting..."
                exit 1
            }

            if ($null -eq $pom.project.dependencies) {
                Log-Message "No dependencies in pom"
                $dependencies = $pom.CreateElement("dependencies", $pom.DocumentElement.NamespaceURI)
                $pom.project.AppendChild($dependencies)
            } else {
                $dependencies = $pom.project.dependencies
            }
            
            $archunit_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "archunit-junit5" }
            if (-not $archunit_present) {
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>com.tngtech.archunit</groupId><artifactId>archunit-junit5</artifactId><version>1.0.1</version>"
                $dependencies.AppendChild($new_dep)
            }

            $junit_jupiter_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "junit-jupiter" }
            if (-not $junit_jupiter_present) {
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><version>5.8.2</version><scope>test</scope>"
                $dependencies.AppendChild($new_dep)
            }

            $slf4j_nop_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "slf4j-nop" }
            if (-not $slf4j_nop_present) {
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>org.slf4j</groupId><artifactId>slf4j-nop</artifactId><version>1.7.32</version><scope>test</scope>"
                $dependencies.AppendChild($new_dep)
            }

            $build = $pom.project.build
            if ($build -and $build.plugins) {
                $helper_plugin = $build.plugins.plugin | Where-Object { $_.artifactId -eq "build-helper-maven-plugin" }
                if ($helper_plugin) {
                    Log-Message "Deactivating build-helper-plugin in $full_path"
                    foreach ($execution in $helper_plugin.executions.execution) {
                        if ($execution.id -eq "add-test-source") {
                            $execution.phase = "none"
                        }
                    }
                }
            }
            $pom.Save($full_path)
        }
        else {
            Log-Message "pom.xml not found at $full_path. Exiting..."
            exit 1
        }
    }
}

#########################################
# Updating Functions #
#########################################

function Update-Java-Version-For-Maven {

    $pomFiles = @(
        "$TEMP_DIR\pom.xml",
        "$TEMP_DIR\use-core\pom.xml",
        "$TEMP_DIR\use-gui\pom.xml",
        "$TEMP_DIR\use-assembly\pom.xml"
    )

    foreach ($pomFile in $pomFiles) {
        if (Test-Path $pomFile) {
            $content = Get-Content $pomFile -Raw

            $content = $content -replace '<maven\.compiler\.source>15</maven\.compiler\.source>', '<maven.compiler.source>14</maven.compiler.source>'
            $content = $content -replace '<maven\.compiler\.target>15</maven\.compiler\.target>', '<maven.compiler.target>14</maven.compiler.target>'
            
            $content | Set-Content $pomFile
            Log-Message "Changed problematic Java Version 15 to 14"
        }
        else {
            Log-Message "Warning: ${pomFile} not found."
        }
    }
}