package headspin.basicFunctionalities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
    static Properties prop = new Properties();

    public static String getConfigProperty(String propName){
        InputStream input = null;
        try
        {
            input = new FileInputStream("src/test/resources/config.properties");
            prop.load(input);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        if (prop != null) {
            //System.out.println("Config Utils  : Get config for property : " + propName);
            String propertyValue = prop.getProperty(propName);
            return propertyValue;
        }
        return null;
    }
}
