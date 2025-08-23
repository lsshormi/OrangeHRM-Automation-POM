package me.shormi.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "./config.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream input = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            System.err.println("Error loading config.properties file: " + e.getMessage());
            // Set default values if config file is not found
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        properties = new Properties();
        properties.setProperty("browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "30");
        properties.setProperty("url", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        properties.setProperty("screenshot.path", "./screenshots/");
        properties.setProperty("report.path", "./reports/");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public static String getUrl() {
        return getProperty("url");
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path");
    }

    public static String getReportPath() {
        return getProperty("report.path");
    }
}
