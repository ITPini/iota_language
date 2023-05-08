package edu.aau.g404;

public class ContextualAnalyzer {


    private Token root;

    public ContextualAnalyzer(){
    }


    public void depthFirstTraverser(Token node){
        if (node.getChildren()!=null){
            for (Token t: node.getChildren()) {
                depthFirstTraverser(t);
            }
        }
        //work on the node is done here
        //System.out.println(node.getValue());
    }


}
