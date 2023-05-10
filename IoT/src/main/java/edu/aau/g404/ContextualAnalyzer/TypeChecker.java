package edu.aau.g404.ContextualAnalyzer;

import edu.aau.g404.SymbolTable;
import edu.aau.g404.Token;

public class TypeChecker {

    public TypeChecker() {
    }


    public void depthFirstTraverser(Token node) {
        if (node.getValue().equals("Initiations")){
            defineDeviceName(node.getChildren().get(1).getChildren().get(0).getValue(),
                    node.getChildren().get(0).getChildren().get(0).getValue());
        }
        typeCheck(node);
        if (node.getChildren()!=null){ //if it have branches, do a recursive call
            for (Token t: node.getChildren()) {
                depthFirstTraverser(t);
            }
        } else { //if leaf, do this
            if (node.getType().equals("DeviceName") && SymbolTable.get(node.getValue())==null){
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
    public void typeCheck(Token node){
        String nodeValue = node.getValue();
        switch(nodeValue) {
            case "Bool":
                typeCheckBool(node);
                break;
        }
    }
    public void typeCheckBool(Token node){
        System.out.println("checking of bool");
        if (node.getChildren().size() > 1){
            String type1 = node.getChildren().get(0).getChildren().get(0).getValue();
            String type2 = node.getChildren().get(2).getChildren().get(0).getValue();
            if (type1 != "Attribute" && type2 != "Attribute" && type1 != type2){
                try {
                    throw new IOTCompilerError(type1 + " and "+ type2+ " are not the same type");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
    public void defineDeviceName(String name, String type){
        SymbolTable.addValue(name,type);
    }


}
