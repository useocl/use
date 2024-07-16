package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FontProperties {
    private Properties properties = new Properties();

    public FontProperties() {
        try (InputStream input = getClass().getResourceAsStream("use-core/src/main/resources/etc/use.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find use.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getControlFont() {
        return properties.getProperty("use.gui.controlFont");
    }

    public String getSystemFont() {
        return properties.getProperty("use.gui.systemFont");
    }

    public String getUserFont() {
        return properties.getProperty("use.gui.userFont");
    }

    public String getSmallFont() {
        return properties.getProperty("use.gui.smallFont");
    }

    public String getEvalFont() {
        return properties.getProperty("use.gui.evalFont");
    }
}
