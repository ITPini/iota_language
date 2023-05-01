package edu.aau.g404;

import java.util.ArrayList;

public class Token {

    //Map for type * see AST
    private Token parent = null;
    private String type;
    private String value;
    private ArrayList<Token> children;

    public Token(String type){
        this.type = type;
        children = new ArrayList<Token>();
    }
    public Token(String type, String value){
        this.type = type;
        this.value = value;
        children = new ArrayList<Token>();
    }

    public void addChild(Token newChild){
        children.add(newChild);
        newChild.setParent(this);

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
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Token getParent() {
        return parent;
    }
    public void setParent(Token parent) {
        this.parent = parent;
    }
}
