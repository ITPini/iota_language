package edu.aau.g404.CodeGenerator;

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

public final class CodeGenerator {
    private HueController hueController;
    private WiZController wizController;
    private Map<String, Controller> controller = Map.of("Hue", new HueController(), "Wiz", new WiZController());
    private Map<String, Action> action = Map.of("On", new OnAction(), "Brightness", new DimmingAction(), "Color", new ColorAction());
    private ArrayList<String> packagesAllowed = new ArrayList<>();
    private Map<String, DeviceData> devices = new HashMap<>();
    private ArrayList<Action> globalActions = new ArrayList<>();
    private ArrayList<Trigger> globalTriggers = new ArrayList<>();
    private Automation automation;

    public CodeGenerator() {
        //this.hueController = new HueController("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
        //this.hueController = new HueController("192.168.0.100", "aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex");
        this.automation = new Automation();
    }

    public void execute(Token root){
        traverseTree(root);
        printCode();
        //automation.addThread(hueController, devices.get("LivingRoomLight1").getDeviceIdentifier(), globalActions, globalTriggers);
        //automation.start();
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
            case "Automations":
                //System.out.println("This is a " + node.getValue());
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
                                    Action onAction = new OnAction(onValue);
                                    //System.out.println("Adding on value: " + onValue);
                                    globalActions.add(onAction);
                                    automateIt(child);
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

                                    Action brightnessAction = new DimmingAction(brightnessValue, operator);
                                    //System.out.println("Adding brightness value: " + brightnessValue);
                                    globalActions.add(brightnessAction);
                                    automateIt(child);
                                    break;
                                case "Color":
                                    int r, g, b;
                                    r = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue());
                                    g = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(2).getChildren().get(0).getValue());
                                    b = Integer.parseInt(child.getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(4).getChildren().get(0).getValue());
                                    Action colorAction = new ColorAction(r, g, b);
                                    //System.out.println("Adding color value: " + r + " " + g + " " + b);
                                    globalActions.add(colorAction);
                                    automateIt(child);
                                    break;
                            }
                    }
                }
                globalTriggers.clear();

        }
    }

    private void automateIt(Token child) {
        automation.addThread(controller.get(devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceBrand()),
                devices.get(child.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue()).getDeviceIdentifier(),
                globalActions, globalTriggers);
        globalActions.clear();
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
        for (Action a : globalActions) {
            System.out.println(a.toString());
        }
        for (Trigger t : globalTriggers) {
            System.out.println(t.toString());
        }
        for (Map.Entry<String, DeviceData> entry : devices.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }
}
