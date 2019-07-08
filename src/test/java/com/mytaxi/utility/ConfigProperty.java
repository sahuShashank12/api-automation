package com.mytaxi.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperty {

    public static Properties loadProperties() {

        Properties prop = new Properties();
        try{
            InputStream input = new FileInputStream("src/test/Resources/config.properties");
            prop.load(input);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
