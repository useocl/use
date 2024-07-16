package test;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class CssEditor {

    public static void editCssWithProperties(String propertiesFilePath, String cssTemplateFilePath, String outputCssFilePath) throws IOException {
        // Lesen der .properties Datei
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFilePath));

        // Lesen der .css Vorlage
        String cssTemplate = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(cssTemplateFilePath)));

        // Ersetzen der Platzhalter in der .css Vorlage
        String cssContent = cssTemplate
                .replace("{controlFont}", properties.getProperty("use.gui.controlFont"))
                .replace("{systemFont}", properties.getProperty("use.gui.systemFont"))
                .replace("{userFont}", properties.getProperty("use.gui.userFont"))
                .replace("{smallFont}", properties.getProperty("use.gui.smallFont"));

        // Schreiben der generierten .css Datei
        try (FileWriter writer = new FileWriter(outputCssFilePath)) {
            writer.write(cssContent);
        }
    }
}
