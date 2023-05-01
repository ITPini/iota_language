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
        start = new Token("Start", "Start");
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
        keyTable.put("IDValue", "Identifier");
        keyTable.put("Identifier", "Initiations");
        keyTable.put("Field", "Attribute");
        keyTable.put("ScopeStart", "Automations");
        keyTable.put("ScopeEnd", "Automations");
        keyTable.put("Key", "Expr");

        //Multiple possible non-terminal
        keyTable.put("Name", ""); // Identifier, PackageName, Initiations, Attribute
        keyTable.put("Attribute", ""); //can be Expr or Change
        keyTable.put("Expr", ""); //can be Bool or Change
        keyTable.put("EOL", ""); //can be Triggers, Actions, Automations, Package, or Initiations
        keyTable.put("", ""); //can be Triggers, Actions, ScopeStart, ScopeEnd or Automations

        //terminals

    }


    public Token astBuilder(ArrayList<Token> tokenList) {
        ArrayList<Token> currentBranch = new ArrayList<>();
        for (Token e : tokenList) {
            currentBranch.add(e);
            System.out.println(e.getValue());//Snitch!
            if (e.getValue().equals(";")) { //Handle the creation of the AST for one line of code at a time

                generateLeftMostBranch(currentBranch); //Creates the left most branch of the AST containing this line of code
                for (Token t : currentBranch) {
                    System.out.println("Working on: " + t.getValue());//Snitch!
                    currentToken = t;
                    if (currentBranch.get(0) != t) {
                        if (!t.getType().equals("")) {
                            currentToken = new Token(keyTable.get(t.getType()), t.getType());
                            currentToken.addChild(t);
                        }
                        while (currentToken != start) {
                            System.out.println(currentToken.getValue());//Snitch
                            int count = currentBranch.indexOf(t);
                            Token tokenCheck = t;
                            while (currentToken.getParent() == null && count >= 0) {
                                //System.out.println(" - loop time!");
                                if (!currentToken.getType().equals("")) {
                                    if (tokenCheck.getValue() == currentToken.getType()) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (tokenCheck.getParent() != null) {
                                        tokenCheck = tokenCheck.getParent();
                                    } else {
                                        count--;
                                        if (count >= 0) {
                                            tokenCheck = currentBranch.get(count);
                                        }
                                    }
                                } else {//This needs work
                                    if (currentToken.getValue().equals("Name") &&
                                            (tokenCheck.getValue().equals("Identifier") ||
                                                    tokenCheck.getValue().equals("PackageName") ||
                                                    tokenCheck.getValue().equals("Initiations") ||
                                                    tokenCheck.getValue().equals("Attribute"))) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Attribute") &&
                                            (tokenCheck.getValue().equals("Expr") ||
                                                    tokenCheck.getValue().equals("Change"))) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Expr") &&
                                            (tokenCheck.getValue().equals("Bool") ||
                                                    tokenCheck.getValue().equals("Changes"))) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("EOL") &&
                                            (tokenCheck.getValue().equals("Triggers") ||
                                                    tokenCheck.getValue().equals("Actions") ||
                                                    tokenCheck.getValue().equals("Automations") ||
                                                    tokenCheck.getValue().equals("Package") ||
                                                    tokenCheck.getValue().equals("Initiations"))) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("") &&
                                            (tokenCheck.getValue().equals("Triggers") ||
                                                    tokenCheck.getValue().equals("Actions") ||
                                                    tokenCheck.getValue().equals("Automations") ||
                                                    tokenCheck.getValue().equals("ScopeStart") ||
                                                    tokenCheck.getValue().equals("ScopeEnd"))) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (tokenCheck.getParent() != null) {
                                        tokenCheck = tokenCheck.getParent();
                                    } else {
                                        count--;
                                        if (count >= 0) {
                                            tokenCheck = currentBranch.get(count);
                                        }
                                    }
                                }
                            }
                            if (currentToken.getParent() == null) {
                                previousToken = new Token(keyTable.get(currentToken.getType()), currentToken.getType());
                                previousToken.addChild(currentToken);
                                currentToken = previousToken;
                            } else {
                                currentToken = start;
                            }
                        }
                    }

                }
                currentBranch.clear();
            }
        }
        System.out.println("All done!");
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

    private void generateLeftMostBranch(ArrayList<Token> currentBranch) {
        currentToken = new Token(keyTable.get(currentBranch.get(0).getType()), currentBranch.get(0).getType());
        currentToken.addChild(currentBranch.get(0));
        while (currentToken.getType() != "Start") {
            previousToken = new Token(keyTable.get(currentToken.getType()), currentToken.getType());
            previousToken.addChild(currentToken);
            currentToken = previousToken;
        }
        start.addChild(currentToken);
    }


    public void printTree(Token rootToken) {
        int dept = 0;
        printLeaf(rootToken, dept);
    }

    private void printLeaf(Token token, int dept) {
        if (token.getChildren() != null) {
            for (Token e : token.getChildren()) {
                printLeaf(e, dept + 1);
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
