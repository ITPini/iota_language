package edu.aau.g404.ContextualAnalyzer;

import edu.aau.g404.KeyTableReserved;
import edu.aau.g404.Token;

public class TypeChecker {

    public TypeChecker() {
    }


    public void depthFirstTraverser(Token node) {
        if (node.getChildren()!=null){
            for (Token t: node.getChildren()) {
                depthFirstTraverser(t);
            }
        } else {
            if (node.getType().equals("Name") && KeyTableReserved.get(node.getValue())==null){
                try {
                    throw new IOTCompilerError(node.getValue() + " not defined");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            }
        }
        //work on the node is done here
        //System.out.println(node.getValue());
    }
}
