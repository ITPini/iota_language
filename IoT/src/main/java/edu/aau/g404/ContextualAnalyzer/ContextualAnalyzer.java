package edu.aau.g404.ContextualAnalyzer;

import edu.aau.g404.Token;

public final class ContextualAnalyzer {

    private TypeChecker typeChecker;

    private Token root;

    public ContextualAnalyzer(){
        this.typeChecker = new TypeChecker();
    }


    public void checkForTypeErrors(Token token){
        typeChecker.depthFirstTraverser(token);
    }
/*
    public void depthFirstTraverser(Token node){
        if (node.getChildren()!=null){
            for (Token t: node.getChildren()) {
                depthFirstTraverser(t);
            }
        }
        //work on the node is done here
        //System.out.println(node.getValue());
    }
*/

}
