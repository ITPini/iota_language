package edu.aau.g404;

import java.util.HashMap;
import java.util.Map;

public class KeyTable {

    public static Map<String, String> keyTable= new HashMap<String, String>();
    public static Map<String, String> keyTableReserved= new HashMap<String, String>();

    public static String get(String key){
        return keyTable.get(key);
    }
    public static void addValue(String key, String value){
        keyTable.put(key, value);
    }


}
