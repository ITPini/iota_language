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
        keyTable.put("IDValue", "Identifier");
        keyTable.put("Identifier", "Initiations");
        keyTable.put("Field", "Attribute");

        //Multiple possible non-terminal
        keyTable.put("Name", ""); // Identifier, PackageName, Initiations, Attribute
        keyTable.put("Attribute", ""); //can be Expr or Change
        keyTable.put("Expr", ""); //can be Bool or Change
        keyTable.put("EOL", ""); //can be Triggers, Actions, Automations, Package, or Initiations
        keyTable.put("", ""); //can be Triggers, Actions, or Automations

        //terminals

    }


    public Token astBuilder(ArrayList<Token> tokenList) {
        ArrayList<Token> currentBranch = new ArrayList<>();
        for (Token e : tokenList) {
            currentBranch.add(e);
            if (e.getValue() == ";") {
                currentToken = new Token(keyTable.get(currentBranch.get(0).getType()), currentBranch.get(0).getType());
                currentToken.addChild(currentBranch.get(0));
                while (currentToken.getType() != "Start") {
                    previousToken = new Token(keyTable.get(currentToken.getType()), currentToken.getType());
                    previousToken.addChild(currentToken);
                    currentToken = previousToken;
                }
                start.addChild(currentToken);

                for (Token t : currentBranch) {
                    currentToken = t;
                    if (currentBranch.get(0) != t) {
                        if (t.getType() != "") {
                            currentToken = new Token(keyTable.get(t.getType()), t.getType());
                            currentToken.addChild(t);
                        }
                        while (currentToken != start) {
                            int count = currentBranch.indexOf(t);
                            Token tokenCheck = currentBranch.get(count);
                            while (currentToken.getParent() == null && count >= 0) {
                                if (currentToken.getType() != "") {
                                    if (tokenCheck.getValue() == currentToken.getType()) {
                                        tokenCheck.addChild(currentToken);
                                    } else if (tokenCheck.getParent() != null) {
                                        tokenCheck = tokenCheck.getParent();
                                    } else {
                                        count--;
                                        tokenCheck = currentBranch.get(count);
                                    }
                                } else {//This needs work 
                                    switch (currentToken.getValue()) { //maybe tokenCheck instead of currentToken
                                        case "Name":
                                            if (tokenCheck.getValue() == "Identifier" ||
                                                    tokenCheck.getValue() == "PackageName" ||
                                                    tokenCheck.getValue() == "Initiations" ||
                                                    tokenCheck.getValue() == "Attribute") {
                                                tokenCheck.addChild(currentToken);
                                            } else if (tokenCheck.getParent() != null) {
                                                tokenCheck = tokenCheck.getParent();
                                            } else {
                                                count--;
                                                tokenCheck = currentBranch.get(count);
                                            }
                                            ;
                                            break;
                                        case "Attribute":
                                            ;
                                            break;
                                        case "Expr":
                                            ;
                                            break;
                                        case "EOL":
                                            ;
                                            break;
                                        default:
                                            ;
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

                }

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
