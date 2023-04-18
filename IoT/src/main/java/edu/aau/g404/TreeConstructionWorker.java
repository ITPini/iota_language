package edu.aau.g404;

//This class just helps in the construction of the Abstract Syntax Tree (AST)

import java.util.HashMap;
import java.util.Map;

public class TreeConstructionWorker {

    Token previousToken = null;
    Token currentToken = null;
    Token start;
    private Map<String, String> keyTable;

    public TreeConstructionWorker() {
        start = new Token("Start");
        currentToken = start;
        keyTable = new HashMap<String, String>();
        //keywords
        keyTable.put("Use", "Package");
        keyTable.put("Begin", "Automations");
        keyTable.put("Trigger", "Triggers");
        keyTable.put("Action", "Actions");
        keyTable.put("Light", "Initiation");
        keyTable.put("Sensor", "Initiation");

        //Special
        keyTable.put("End", "Automations");

        //terminals

    }


    public Token getPreviousToken() {
        return previousToken;
    }

    public void setPreviousToken(Token previousToken) {
        this.previousToken = previousToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }


    public void addToken(Token newToken) {

        if (keyTable.get(newToken.getType()) != null) {
            previousToken = currentToken;
            currentToken = new Token(keyTable.get(newToken.getType()));
            previousToken.addChild(currentToken);
            currentToken.addChild(newToken);
        } else {
            currentToken.addChild(newToken);
        }
    }


}
