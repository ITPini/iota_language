package edu.aau.g404;

import java.util.HashMap;
import java.util.Map;

public class KeyTableReserved {

        public static Map<String, String> keyTableReserved= new HashMap<String, String>();

        public static String get(String key){
            return keyTableReserved.get(key);
        }
        public static void addValue(String key, String value){
            keyTableReserved.put(key, value);
        }

}
