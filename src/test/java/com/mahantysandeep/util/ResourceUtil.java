package com.mahantysandeep.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by smahanty on 3/31/17.
 */
public class ResourceUtil {

    public static String getTestResource(String resource) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(resource);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String content = "";
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
