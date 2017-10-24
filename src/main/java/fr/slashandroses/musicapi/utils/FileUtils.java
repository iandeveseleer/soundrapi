package fr.slashandroses.musicapi.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by snr on 19/10/2017.
 */
public class FileUtils {

    public Properties  prop = new Properties();

    public Properties fileUtils()
    {
        try {
            //load a properties file from class path, inside static method
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }

    public String getProperty(String pName)
    {
        return fileUtils().getProperty(pName);
    }
}
