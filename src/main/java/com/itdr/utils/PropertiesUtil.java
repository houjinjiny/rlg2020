package com.itdr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties p;
    static {
        InputStream in=PropertiesUtil.class.getClassLoader().getResourceAsStream("initParam.properties");
        p=new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        String s=p.getProperty(key);
        return s;
    }
}
