package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ajopaul on 23/8/17.
 */
public class SystemProperties {
    static Properties properties;
    static {
        properties = new Properties();
        try {
            InputStream input = new FileInputStream("properties.file");
            properties.load(input);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropValue(String key){
        return properties.getProperty(key);
    }
}
