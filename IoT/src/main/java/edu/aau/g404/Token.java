package edu.aau.g404;

import java.util.ArrayList;

public class Token {

    //Map for type * see AST
    private String type;
    private ArrayList<Token> children;

    public Token(String type){
        this.type = type;
        children = new ArrayList<Token>();
    }

    public void addChild(Token newChild){
        children.add(newChild);
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<Token> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<Token> children) {
        this.children = children;
    }
}
