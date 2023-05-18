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
        KeyTable.addValue("BoolValue", "Expr");
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
        KeyTable.addValue("Color", "Expr");
        KeyTable.addValue("ColorValue", "Color");
        //Multiple possible non-terminal
        KeyTable.addValue("DeviceName", ""); // Identifier, PackageName, Initiations, Attribute
        KeyTable.addValue("Attribute", ""); //can be Expr or Change
        KeyTable.addValue("Expr", ""); //can be Bool or Change
        KeyTable.addValue("EOL", ""); //can be Triggers, Actions, Automations, Package, or Initiations
        KeyTable.addValue("(", ""); //can be Triggers, Actions, ScopeStart, ScopeEnd, or Automations
        KeyTable.addValue(")", ""); //can be Triggers, Actions, ScopeStart, ScopeEnd, or Automations
        KeyTable.addValue(",", ""); //Can be Identifier, or Color

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
                    //printTree(start); //Snitch, prints tree after every leaf added
                    currentToken = t;
                    if (currentBranch.get(0) != t) {
                        //add a parent node if known
                        if (!(t.getType().equals("") || t.getType().equals("Attribute") ||
                                t.getType().equals("Color") || t.getType().equals("Identifier"))) {
                            currentToken = new Token(KeyTable.get(t.getType()), t.getType());
                            currentToken.addChild(t);
                        }
                        //add an additional parent node if that node is known to be an Expr node
                        if (currentToken.getType().equals("Expr")) {
                            previousToken = currentToken;
                            currentToken = new Token(KeyTable.get(previousToken.getType()), previousToken.getType());
                            currentToken.addChild(previousToken);
                        }
                        //Adding token to the AST
                        while (currentToken != start) {
                            int count = currentBranch.indexOf(t);
                            Token tokenChecker = t;
                            //searches the tree so far to see if the current node or leaf should be added to one of the existing nodes
                            while (currentToken.getParent() == null && count >= 0) {
                                //System.out.println(currentToken.getType() + " " + currentToken.getValue() + "       " + t.getValue()); //snitch
                                if (!currentToken.getType().equals("")) {
                                    if (tokenChecker.getValue() == currentToken.getType()) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (tokenChecker.getParent() != null) {
                                        tokenChecker = tokenChecker.getParent();
                                    } else {
                                        count--;
                                        if (count >= 0) {
                                            tokenChecker = currentBranch.get(count);
                                        }
                                    }
                                } else {//This needs work
                                    if (currentToken.getValue().equals("DeviceName") &&
                                            (tokenChecker.getValue().equals("Identifier") ||
                                                    tokenChecker.getValue().equals("Initiations") ||
                                                    tokenChecker.getValue().equals("Attribute"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Attribute") &&
                                            (tokenChecker.getValue().equals("Expr") ||
                                                    tokenChecker.getValue().equals("Changes"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Attribute") &&
                                            tokenChecker.getValue().equals("Actions")) {
                                        tokenChecker.addChild(new Token(KeyTable.get("Changes"), "Changes"));
                                        tokenChecker.getChildren().get(2).addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Attribute") &&
                                            tokenChecker.getValue().equals("Triggers")) {
                                        previousToken = currentToken;
                                        currentToken = new Token(KeyTable.get("Expr"), "Expr");
                                        currentToken.addChild(previousToken);
                                    } else if (currentToken.getValue().equals("Expr") &&
                                            (tokenChecker.getValue().equals("Bool") ||
                                                    tokenChecker.getValue().equals("Changes"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("EOL") &&
                                            (tokenChecker.getValue().equals("Triggers") ||
                                                    tokenChecker.getValue().equals("Actions") ||
                                                    tokenChecker.getValue().equals("Automations") ||
                                                    tokenChecker.getValue().equals("Package") ||
                                                    tokenChecker.getValue().equals("Initiations"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if ((currentToken.getValue().equals("(") || currentToken.getValue().equals(")")) &&
                                            (tokenChecker.getValue().equals("Triggers") ||
                                                    tokenChecker.getValue().equals("Actions") ||
                                                    tokenChecker.getValue().equals("Automations") ||
                                                    tokenChecker.getValue().equals("ScopeStart") ||
                                                    tokenChecker.getValue().equals("ScopeEnd"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (currentToken.getValue().equals("Automation") &&
                                            (tokenChecker.getValue().equals("ScopeStart") ||
                                                    tokenChecker.getValue().equals("ScopeEnd"))) {
                                        tokenChecker.addChild(currentToken);
                                    } else if (tokenChecker.getParent() != null) {
                                        tokenChecker = tokenChecker.getParent();
                                    } else {
                                        count--;
                                        if (count >= 0) {
                                            tokenChecker = currentBranch.get(count);
                                        }
                                    }
                                }
                            }

                            if (currentToken.getParent() == null) {
                                if (currentToken.getValue().equals("Expr")) {
                                    previousToken = new Token(KeyTable.get("Bool"), "Bool");
                                } else if (currentToken.getValue().equals("DeviceName")) { //Does not check for Identifier!! Need Fix!!
                                    previousToken = new Token(KeyTable.get("Attribute"), "Attribute");
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

/*
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

 */

    private void generateLeftMostBranch(ArrayList<Token> currentBranch) {
        //System.out.println("Generating left most branch for: " + currentBranch.get(0).getValue()); //snitch
        currentToken = new Token(KeyTable.get(currentBranch.get(0).getType()), currentBranch.get(0).getType());
        currentToken.addChild(currentBranch.get(0));

        while (!currentToken.getType().equals("Start")) {
            if (currentToken.getType().equals("Automations") && !currentToken.getValue().equals("ScopeStart")){
                start.getChildren().get(start.getChildren().size()-1).addChild(currentToken);
                currentToken = currentToken.getParent();
                return;
            } else {
                previousToken = new Token(KeyTable.get(currentToken.getType()), currentToken.getType());
                previousToken.addChild(currentToken);
                currentToken = previousToken;
            }
        }
        start.addChild(currentToken);
    }


    public void printTree(Token rootToken) {

        ArrayList<String[]> valueList = printLeaf(rootToken, new ArrayList<String[]>(), 0);
        for (String[] a : valueList) {
            for (String s : a) {
                if (s == null) {
                    System.out.printf(" %-15.15s ", "");
                } else {
                    System.out.printf(" %-15.15s ", s);
                }
            }
            System.out.println("");
        }
        printDepth = 0;
    }

    private ArrayList<String[]> printLeaf(Token token, ArrayList<String[]> result, int depth) {
        if (result.size() == printDepth) {
            result.add(new String[9]);
        }
        result.get(printDepth)[depth] = "t: " + token.getValue();
        if (token.getChildren() != null) {
            for (Token e : token.getChildren()) {
                result = printLeaf(e, result, depth + 1);
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
