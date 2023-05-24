package edu.aau.g404.translator;

import edu.aau.g404.Token;
import edu.aau.g404.api.hue.HueController;
import edu.aau.g404.api.wiz.WiZController;
import edu.aau.g404.core.Automation;
import edu.aau.g404.core.action.Action;
import edu.aau.g404.core.action.light.ColorAction;
import edu.aau.g404.core.action.light.DimmingAction;
import edu.aau.g404.core.action.light.OnAction;
import edu.aau.g404.core.trigger.TimeTrigger;
import edu.aau.g404.core.trigger.Trigger;
import edu.aau.g404.device.Controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Translator {
    private Map<String, Controller> controller = Map.of("Hue", new HueController(), "Wiz", new WiZController());
    private Map<String, Controller> packagesAllowed = new HashMap<>();
    private Map<String, DeviceData> devices = new HashMap<>();
    private ArrayList<Trigger> globalTriggers = new ArrayList<>();
    private Automation automation;

    public Translator() {
        this.automation = new Automation();
    }

    public void execute(Token root){
        traverseTree(root);
        printDevicesOnNetwork();
        printCode();
        automation.start();
    }

    protected void traverseTree(Token node){
        String value = node.getValue();

        if(node.getChildren() != null) {
            for (Token child : node.getChildren()) {
                traverseTree(child);
            }
        }

        switch(value) {
            case "Package":
                if (node.getChildren().size()>3){
                    packagesAllowed.put(node.getChildren().get(1).getChildren().get(0).getValue(), new HueController(node.getChildren().get(2).getChildren().get(0).getValue(), node.getChildren().get(3).getChildren().get(0).getValue()));
                } else {
                    packagesAllowed.put(node.getChildren().get(1).getChildren().get(0).getValue(), controller.get(node.getChildren().get(1).getChildren().get(0).getValue()));
                }
                break;
            case "Initiations":
                devices.put(node.getChildren().get(1).getChildren().get(0).getValue(), addDevice(node));
                break;
            case "Automations":
                System.out.println("This is a " + node.getValue());
                ArrayList<Token> automationNode = node.getChildren();

                for (Token child : automationNode) { // This will technically also check "ScopeStart" and "ScopeEnd"
                    switch (child.getValue()) {
                        case "Triggers":
                            //System.out.println("This is a " + child.getChildren().get(0).getValue());
                            String triggerType = child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue();
                            switch (triggerType) {
                                case "TimeValue":
                                    String timeValue = child.getChildren().get(2).getChildren().get(0).getChildren().get(2).getChildren().get(0).getChildren().get(0).getValue();
                                    //System.out.println("This is a " + timeValue);
                                    globalTriggers.add(new TimeTrigger(LocalTime.parse(timeValue)));
                                    break;
                                case "Attribute":
                                    break;
                            }
                            break;
                        case "Actions":
                            //System.out.println("This is a " + child.getChildren().get(0).getValue());
                            String actionType = child.getChildren().get(2).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue();
                            //System.out.println("This is a " + actionType);
                            switch (actionType) {
                                case "On":
                                    boolean onValue = child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getValue().equals("TRUE");
                                    //System.out.println("Adding on value: " + onValue);
                                    automateIt(child, new OnAction(onValue));
                                    break;
                                case "Brightness":
                                    float brightnessValue;
                                    String operator = "";
                                    if (child.getChildren().get(2).getChildren().get(1).getValue().equals("Operator")) {
                                        operator = child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getValue();
                                        brightnessValue = Float.parseFloat(child.getChildren().get(2).getChildren().get(2).getChildren().get(0).getChildren().get(0).getValue());
                                    } else {
                                        brightnessValue = Float.parseFloat(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getValue());
                                    }

                                    //System.out.println("Adding brightness value: " + brightnessValue);
                                    automateIt(child, new DimmingAction(brightnessValue, operator));
                                    break;
                                case "Color":
                                    int r, g, b;
                                    r = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue());
                                    g = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue());
                                    b = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(4).getChildren().get(0).getValue());
                                    //System.out.println("Adding color value: " + r + " " + g + " " + b);
                                    automateIt(child, new ColorAction(r, g, b));
                                    break;
                            }
                    }
                }
                globalTriggers.clear();

        }
    }

    private void automateIt(Token child, Action action) {
        //System.out.println("Controller brand: " + devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceBrand());
        //System.out.println("Identifier: " + devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceIdentifier());
        automation.addThread(packagesAllowed.get(devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceBrand()),
                devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceIdentifier(),
                new ArrayList<Action>(){{add(action);}}, new ArrayList<Trigger>(globalTriggers));
    }

    private DeviceData addDevice(Token node) {
        String type = checkType(node);
        String deviceIdentifier = checkDeviceIdentifier(node);
        String brandType = checkBrandType(deviceIdentifier);
        String deviceName = checkDeviceName(node);
        return new DeviceData(deviceName, type, brandType, deviceIdentifier);
    }

    private String checkDeviceIdentifier(Token node) {
        return node.getChildren().get(2).getChildren().get(0).getChildren().get(0).getValue();
    }

    private String checkDeviceName(Token node){
        return node.getChildren().get(1).getChildren().get(0).getValue();
    }

    private String checkBrandType(String identifier) {
        // TODO: Implement this better please
        return identifier.charAt(3) == '.' ? "Wiz" : "Hue";
    }

    private String checkType(Token node) {
        return node.getChildren().get(0).getChildren().get(0).getValue();
    }

    public void printCode(){
        for (Map.Entry<String, DeviceData> entry : devices.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    private void printDevicesOnNetwork() {
        for (Map.Entry<String, Controller> entry : packagesAllowed.entrySet()) {
            entry.getValue().printDevices();
        }
    }

    public Map<String, DeviceData> getDevices() {
        return devices;
    }

    public Map<String, Controller> getPackagesAllowed() {
        return packagesAllowed;
    }

    public ArrayList<Trigger> getGlobalTriggers() {
        return globalTriggers;
    }

    public Automation getAutomation() {
        return automation;
    }
}
