package framework;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.Properties;

public class Loader {
    private static final String propPath ="D:\\MY\\Automation\\NBA_Project\\NBA\\src\\main\\resources\\NBA.properties";      //"../../../NBA/src/main/resourcesNBA.properties";
    private static Properties proper = new Properties();
    private static File propFile = new File(propPath);
    static FileWriter fileWriter = null;
    private static PropertiesConfiguration config;

    public static String loadProperty(String name) {

        try {
            proper.load(new FileInputStream(propFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String value = "";
        if (name != null) {
            value = proper.getProperty(name);
        }
        return value;
    }

    //add new properties to file
    public static void updateProperty(String propName, String propValue){
        try {
            config=new PropertiesConfiguration(propPath);
            config.setProperty(propName, propValue);
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}
