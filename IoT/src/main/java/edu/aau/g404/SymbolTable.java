package edu.aau.g404;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

        public static Map<String, String> symbolTable = new HashMap<String, String>();

        public static String get(String key){
            return symbolTable.get(key);
        }
        public static void addValue(String key, String value){
            symbolTable.put(key, value);
        }

}
