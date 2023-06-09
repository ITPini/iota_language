package edu.aau.g404.ContextualAnalyzer;

import edu.aau.g404.SymbolTable;
import edu.aau.g404.Token;

import java.util.ArrayList;
import java.util.Map;

public final class TypeChecker {

    private Map<String, String> attributeTypes = Map.of(
            "On", "BoolValue",
            "Brightness", "Value",
            "Color", "Color",
            "Movement", "Value");

    public TypeChecker() {
    }


    public void depthFirstTraverser(Token node) {
        if (node.getValue().equals("Initiations")) { //if declaring device, add it so symbol table
            defineDeviceName(node.getChildren(1).getChildren(0).getValue(),
                    node.getChildren(0).getChildren(0).getValue());
        }
        typeCheck(node);
        if (node.getChildren() != null) { //if it have branches, do a recursive call
            for (Token t : node.getChildren()) {
                depthFirstTraverser(t);
            }
        } else { //if leaf, do this
            checkDeviceInitiated(node);
        }
    }

    private void checkDeviceInitiated(Token node){
        if (node.getType().equals("DeviceName") && SymbolTable.get(node.getValue()) == null) {
            try {
                throw new IOTCompilerError(node.getValue() + " not defined");
            } catch (IOTCompilerError iotCompilerError) {
                iotCompilerError.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void typeCheck(Token node) {
        switch (node.getValue()) {
            case "Bool":
                typeCheckTrigger(node);
                break;
            case "Changes":
                typeCheckAction(node);
                break;
        }
    }

    private void typeCheckTrigger(Token node) {
        System.out.println("checking Trigger");
        if (node.getChildren().size() > 1) {
            Token type1 = node.getChildren(0).getChildren(0);
            Token type2 = node.getChildren(2).getChildren(0);
            checkColor(type1);
            checkColor(type2);

            String type1Val = type1.getValue().equals("Attribute") ? attributeTypes.get(type1.getChildren(2).getChildren(0).getValue()) : type1.getValue();
            String type2Val = type2.getValue().equals("Attribute") ? attributeTypes.get(type2.getChildren(2).getChildren(0).getValue()) : type2.getValue();
            if (!type1Val.equals(type2Val)) {
                try {
                    throw new IOTCompilerError(type1Val + " and " + type2Val + " are not the same type");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    private void typeCheckAction(Token node) {
        System.out.println("checking of Action");
        if (node.getChildren().size() > 1) {
            Token type1 = node.getChildren(0).getChildren(2).getChildren(0);
            Token type2 = node.getChildren(node.getChildren().size() - 1).getChildren(0);
            checkColor(type2);
            String type1Val = attributeTypes.get(type1.getValue());
            String type2Val = type2.getValue().equals("Attribute") ? attributeTypes.get(type2.getChildren(2).getChildren(0).getValue()) : type2.getValue();

            if (!type1Val.equals(type2Val)) {
                try {
                    throw new IOTCompilerError(type1Val + " and " + type2Val + " are not the same type");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
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

    private void checkColor(Token node) {
        if (node.getValue().equals("Color")) {
            ArrayList<Token> colorList = node.getChildren();
            if (!colorList.get(0).getValue().equals(colorList.get(2).getValue()) || !colorList.get(2).getValue().equals(colorList.get(4).getValue())) {
                try {
                    throw new IOTCompilerError("Argument does not have the right format of (xxx, xxx, xxx)");
                } catch (IOTCompilerError iotCompilerError) {
                    iotCompilerError.printStackTrace();
                    System.exit(1);
                }
            }
        }

    }

    private void defineDeviceName(String name, String type) {
        SymbolTable.addValue(name, type);
    }


}
