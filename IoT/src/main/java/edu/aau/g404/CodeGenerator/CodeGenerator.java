package edu.aau.g404.CodeGenerator;

import edu.aau.g404.Token;
import edu.aau.g404.api.hue.HueController;
import edu.aau.g404.api.wiz.WiZController;
import edu.aau.g404.core.Automation;
import edu.aau.g404.device.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class CodeGenerator {
    private HueController hueController;
    private WiZController wizController;
    private Map<String, Controller> controller = Map.of("Hue", new HueController(), "Wiz", new WiZController());

    //private Map<String, SmartDevice> deviceCollection = Map.of("Hue", new HueLight(), "Wiz", new WiZLight());
    private ArrayList<String> packagesAllowed = new ArrayList<>();
    private Map<String, DeviceData> devices = new HashMap<>();
    private Automation automation;

    public CodeGenerator() {
        //this.hueController = new HueController("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
        this.automation = new Automation();
    }

    public void execute(Token root){
        traverseTree(root);
        printCode();
    }

    private void traverseTree(Token node){
        String value = node.getValue();

        if(node.getChildren() != null) {
            for (Token child : node.getChildren()) {
                traverseTree(child);
            }
        }

        switch(value) {
            case "Package":
                packagesAllowed.add(node.getChildren().get(1).getChildren().get(0).getValue());
                break;
            case "Initiations":
                devices.put(node.getChildren().get(1).getChildren().get(0).getValue(), addDevice(node));
                break;

        }
    }

    private DeviceData addDevice(Token node) {
        String type = checkType(node);
        String brandType = checkBrandType(type);
        String deviceName = checkDeviceName(node);
        String deviceIdentifier = checkDeviceIdentifier(node);
        return new DeviceData(deviceName, type, brandType, deviceIdentifier);
    }

    private String checkDeviceIdentifier(Token node) {
        return node.getChildren().get(2).getChildren().get(0).getChildren().get(0).getValue();
    }

    private String checkDeviceName(Token node){
        return node.getChildren().get(1).getChildren().get(0).getValue();
    }

    private String checkBrandType(String type) {
        // TODO: Implement this better please
        return type.charAt(3) == '.' ? "Wiz" : "Hue";
    }

    private String checkType(Token node) {
        return node.getChildren().get(0).getChildren().get(0).getValue();
    }

    public void printCode(){
        for (String p : packagesAllowed){
            System.out.println("package " + p + ";");
        }
        for (Map.Entry<String, DeviceData> entry : devices.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }
}
