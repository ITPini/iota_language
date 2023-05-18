package edu.aau.g404.ContextualAnalyzer;

import edu.aau.g404.SymbolTable;
import edu.aau.g404.Token;

import java.util.Map;

public class TypeChecker {

    private Map<String, String> attributeTypes = Map.of(
            "On", "BoolValue",
            "Brightness", "Value",
            "Color", "Color",
            "Movement", "Value");

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
                typeCheckTrigger(node);
                break;
            case "Changes":
                typeCheckAction(node);
                break;
        }
    }

    public void typeCheckTrigger(Token node){
        System.out.println("checking Trigger");
        if (node.getChildren().size() > 1){
            String type1 = node.getChildren().get(0).getChildren().get(0).getValue();
            String type2 = node.getChildren().get(2).getChildren().get(0).getValue();
            if (!type1.equals("Attribute") && !type2.equals("Attribute") && !type1.equals(type2)){
                try {
                    throw new IOTCompilerError(type1 + " and "+ type2+ " are not the same type");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            } else if (type1.equals("Attribute") || type2.equals("Attribute")){
                type1 = type1.equals("Attribute")? attributeTypes.get(node.getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue()) : type1;
                type2 = type2.equals("Attribute")? attributeTypes.get(node.getChildren().get(2).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue()) : type2;
                if (!type1.equals(type2)){
                    try {
                        throw new IOTCompilerError(type1 + " and "+ type2+ " are not the same type");
                    } catch (IOTCompilerError iotCompilerError) {
                        iotCompilerError.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        }
    }

    private void typeCheckAction(Token node) {
        System.out.println("checking of Changes");
        if (node.getChildren().size() > 1){
            String type1 = attributeTypes.get(node.getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue());
            String type2 = node.getChildren().get(node.getChildren().size()-1).getChildren().get(0).getValue();
            if (!type2.equals("Attribute") && !type1.equals(type2)){
                try {
                    throw new IOTCompilerError(type1 + " and "+ type2+ " are not the same type");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            } else if (type2.equals("Attribute")){
                type2 = attributeTypes.get(node.getChildren().get(node.getChildren().size()-1).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue());
                if (!type1.equals(type2)){
                    try {
                        throw new IOTCompilerError(type1 + " and "+ type2+ " are not the same type");
                    } catch (IOTCompilerError iotCompilerError) {
                        iotCompilerError.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        } else {
            try {
                throw new IOTCompilerError("Argument missing! Actions must have an attribute and a change to the given attribute");
            } catch (IOTCompilerError iotCompilerError) {
                iotCompilerError.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void defineDeviceName(String name, String type){
        SymbolTable.addValue(name,type);
    }


}
