package edu.aau.g404;

//This class just helps in the construction of the Abstract Syntax Tree (AST)

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreeConstructionWorker {

    private Token previousToken = null;
    private Token currentToken = null;
    private final Token start;
    private Map<String, String> keyTable;

    public TreeConstructionWorker() {
        start = new Token("Start");
        currentToken = start;
        keyTable = new HashMap<String, String>();
        //keywords
        keyTable.put("Package", "Start");
        keyTable.put("Automations", "Start");
        keyTable.put("Triggers", "Automations");
        keyTable.put("Actions", "Automations");
        keyTable.put("Initiations", "Start");
        keyTable.put("TimeValue", "Expr");
        keyTable.put("Value", "Expr");
        keyTable.put("Operator", "Changes");
        keyTable.put("Bool", "BoolExpr");
        keyTable.put("BoolExpr", "Triggers");
        keyTable.put("Type", "Initiations");
        keyTable.put("Changes", "Actions");

        //Multiple possible non-terminal
        keyTable.put("Name", "");
        keyTable.put("Attribute", "");
        keyTable.put("Expr", "");
        keyTable.put("EOL", "");
        keyTable.put("", "");

        //terminals

    }


    public Token astBuilder(ArrayList<Token> tokenList){
        for (Token e: tokenList) {
            if (currentToken.getType() == keyTable.get(e.getType())){
                currentToken.addChild(e);
            } else {
                previousToken = currentToken;
                currentToken = new Token(keyTable.get(e.getType()), e.getType());
                currentToken.addChild(e);
                previousToken.addChild(currentToken);
            }

            if (e.getType()== "Start"){

            } else if(e.getType()== ""){

            }

        }
        return start;

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

    public void printTree(Token rootToken){
        int dept = 0;
        printLeaf(rootToken, dept);
    }

    private void printLeaf(Token token, int dept) {
        if (token.getChildren() != null){
            for (Token e: token.getChildren()) {
                printLeaf(e, dept+1);
            }
        }
        for (int i = 0; i <= dept; i++) {
            System.out.print("   ");
        }
        System.out.println(token.getValue());
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

}
