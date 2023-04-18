package edu.aau.g404;

import java.util.HashMap;
import java.util.Map;

public class AST {

    Map tokenType;
    Token astRoot;

    public AST() {
        tokenType = new HashMap<String, String>();
    }
}
