package edu.aau.g404.LexicalAnalyzer;

//This class just helps in the construction of the Abstract Syntax Tree (AST)

import edu.aau.g404.KeyTable;
import edu.aau.g404.Token;

import javax.print.attribute.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreeConstructionWorker {

    int printDepth = 0;

    private Token previousToken = null;
    private Token currentToken = null;
    private final Token start;
    //private Map<String, String> keyTable;

    public TreeConstructionWorker() {
        start = new Token("Start", "Start");
        currentToken = start;
        //keyTable = new HashMap<String, String>();
        //keywords

        KeyTable.addValue("Package", "Start");
        KeyTable.addValue("Automations", "Start");
        KeyTable.addValue("Triggers", "Automations");
        KeyTable.addValue("Actions", "Automations");
        KeyTable.addValue("Initiations", "Start");
        KeyTable.addValue("TimeValue", "Expr");
        KeyTable.addValue("Value", "Expr");
        KeyTable.addValue("Operator", "Changes");
        KeyTable.addValue("Bool", "BoolExpr");
        KeyTable.addValue("BoolExpr", "Triggers");
        KeyTable.addValue("Type", "Initiations");
        KeyTable.addValue("Changes", "Actions");
        KeyTable.addValue("IDValue", "Identifier");
        KeyTable.addValue("Identifier", "Initiations");
        KeyTable.addValue("Field", "Attribute");
        KeyTable.addValue("ScopeStart", "Automations");
        KeyTable.addValue("ScopeEnd", "Automations");
        KeyTable.addValue("Key", "Expr");
        KeyTable.addValue("PackageName", "Package");
        KeyTable.addValue("AttributeName", "Attribute");
        KeyTable.addValue("BoolOperator", "Bool");
        //Multiple possible non-terminal
        KeyTable.addValue("DeviceName", ""); // Identifier, PackageName, Initiations, Attribute
        KeyTable.addValue("Attribute", ""); //can be Expr or Change
        KeyTable.addValue("Expr", ""); //can be Bool or Change
        KeyTable.addValue("EOL", ""); //can be Triggers, Actions, Automations, Package, or Initiations
        KeyTable.addValue("", ""); //can be Triggers, Actions, ScopeStart, ScopeEnd or Automations

        //terminals

    }


    public Token astBuilder(ArrayList<Token> tokenList) {
        ArrayList<Token> currentBranch = new ArrayList<>();
        for (Token e : tokenList) {
            currentBranch.add(e);
            //System.out.println(e.getValue());//Snitch!
            if (e.getValue().equals(";")) { //Handle the creation of the AST for one line of code at a time

                generateLeftMostBranch(currentBranch); //Creates the left most branch of the AST containing this line of code
                for (Token t : currentBranch) {
                    //System.out.println("Working on: " + t.getValue());//Snitch!
                    currentToken = t;
                    if (currentBranch.get(0) != t) {
                        if (!t.getType().equals("")) {
                            currentToken = new Token(KeyTable.get(t.getType()), t.getType());
                            currentToken.addChild(t);
                        }
                        while (currentToken != start) {
                            //System.out.println(currentToken.getValue());//Snitch
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
                                    if (currentToken.getValue().equals("DeviceName") &&
                                            (tokenCheck.getValue().equals("Identifier") ||
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
                                if (currentToken.getValue().equals("Expr")) {
                                    previousToken = new Token(KeyTable.get("Bool"), "Bool");
                                } else {
                                    previousToken = new Token(KeyTable.get(currentToken.getType()), currentToken.getType());
                                }
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

        if (KeyTable.get(newToken.getType()) != null) {
            previousToken = currentToken;
            currentToken = new Token(KeyTable.get(newToken.getType()));
            previousToken.addChild(currentToken);
            currentToken.addChild(newToken);
        } else {
            currentToken.addChild(newToken);
        }
    }

    private void generateLeftMostBranch(ArrayList<Token> currentBranch) {
        currentToken = new Token(KeyTable.get(currentBranch.get(0).getType()), currentBranch.get(0).getType());
        currentToken.addChild(currentBranch.get(0));
        while (currentToken.getType() != "Start") {
            previousToken = new Token(KeyTable.get(currentToken.getType()), currentToken.getType());
            previousToken.addChild(currentToken);
            currentToken = previousToken;
        }
        start.addChild(currentToken);
    }


    public void printTree(Token rootToken) {

        ArrayList<String[]> valueList = printLeaf(rootToken, new ArrayList<String[]>(), 0);
        for (String[] a : valueList) {
            for (String s: a){
                if (s == null){
                    System.out.printf(" %-15.15s ", "");
                } else {
                    System.out.printf(" %-15.15s ", s);
                }
            }
            System.out.println("");
        }
    }

    private ArrayList<String[]> printLeaf(Token token, ArrayList<String[]> result, int depth) {
        if (result.size()==printDepth){
            result.add(new String[8]);
        }
        result.get(printDepth)[depth] = token.getValue();
        if (token.getChildren() != null) {
            for (Token e : token.getChildren()) {
                result = printLeaf(e, result, depth+1);
            }
        } else {
            printDepth++;
        }
        return result;
    }
/*
    public void prettyPrintTree(Token root, int totalSize) {
        int depth;
        int bottom;
        int size;
        System.out.printf("%10s%n", "print time!");
        for (int i = 0; i < totalSize / 2; i++) {
            System.out.printf("%10s ", "");
        }
        System.out.printf("%10s%n", root.getValue());


        size = root.getChildren().size();
        for (int i = 0; i < (totalSize / 2 - size / 2); i++) {
            System.out.printf("%10s ", "");
        }
        System.out.printf("%5s%5s ", "/", "");
        for (int i = 0; i < size - 2; i++) {
            System.out.printf("%5s%5s ", "|", "");
        }
        System.out.printf("%5s%5s%n", "\\", "");


        size = root.getChildren().size();
        for (int i = 0; i < (totalSize / 2 - size / 2); i++) {
            System.out.printf("%10s ", "");
        }
        for (Token t : root.getChildren()) {
            System.out.printf("%10s ", t.getValue());
        }
        //System.out.printf("%15s%n", "");
    }

 */


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
