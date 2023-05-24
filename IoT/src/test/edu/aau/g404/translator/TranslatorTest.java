package edu.aau.g404.translator;

import edu.aau.g404.Token;
import edu.aau.g404.api.wiz.WiZController;
import edu.aau.g404.core.Automation;
import edu.aau.g404.core.action.light.OnAction;
import edu.aau.g404.core.trigger.TimeTrigger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {
    Token root = new Token("Start", "Start");

    @AfterEach
    void tearDown() {
        root = new Token("Start", "Start"); // Reset root after each test
    }

    @Test
    void traverseTreeInitialisation() {
        root.addChild(new Token("Start", "Initiations"));
        root.getChildren().get(0).addChild(new Token("Initiations", "Type"));
        root.getChildren().get(0).getChildren().get(0).addChild(new Token("Type", "Light"));

        root.getChildren().get(0).addChild(new Token("Initiations", "DeviceName"));
        root.getChildren().get(0).getChildren().get(1).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren().get(0).addChild(new Token("Initiations", "Identifier"));
        root.getChildren().get(0).getChildren().get(2).addChild(new Token("Identifier", "IDValue"));
        root.getChildren().get(0).getChildren().get(2).getChildren().get(0).addChild(new Token("IDValue", "b2ef7371-9321-452a-a70e-49ce5b6cd879"));

        root.getChildren().get(0).addChild(new Token("", "EOL"));
        root.getChildren().get(0).getChildren().get(3).addChild(new Token("EOL", ";"));

        Translator translator = new Translator();
        translator.traverseTree(root);

        DeviceData deviceData = translator.getDevices().get("LivingRoomLight1");
        assertEquals("LivingRoomLight1", deviceData.getDeviceName());
        assertEquals("b2ef7371-9321-452a-a70e-49ce5b6cd879", deviceData.getDeviceIdentifier());
        assertEquals("Light", deviceData.getDeviceType());
        assertEquals("Hue", deviceData.getDeviceBrand());

        assertNull(translator.getDevices().get("LivingRoomLight2"));
    }

    @Test
    void traverseTreeUse(){
        root.addChild(new Token("Start", "Package"));
        root.getChildren().get(0).addChild(new Token("Package", "Use"));

        root.getChildren().get(0).addChild(new Token("Package", "PackageName"));
        root.getChildren().get(0).getChildren().get(1).addChild(new Token("PackageName", "Wiz"));

        root.getChildren().get(0).addChild(new Token("", "EOL"));
        root.getChildren().get(0).getChildren().get(2).addChild(new Token("EOL", ";"));

        Translator translator = new Translator();
        translator.traverseTree(root);

        assertNotNull(translator.getPackagesAllowed());
        assertNull(translator.getDevices().get("LivingRoomLight1"));
        assertEquals(0, translator.getGlobalTriggers().size());

        assertEquals(WiZController.class, translator.getPackagesAllowed().get("Wiz").getClass());
    }

    @Test
    void traverseTreeAutomation(){
        root.addChild(new Token("Start", "Package"));
        root.getChildren().get(0).addChild(new Token("Package", "Use"));

        root.getChildren().get(0).addChild(new Token("Package", "PackageName"));
        root.getChildren().get(0).getChildren().get(1).addChild(new Token("PackageName", "Wiz"));

        root.getChildren().get(0).addChild(new Token("", "EOL"));
        root.getChildren().get(0).getChildren().get(2).addChild(new Token("EOL", ";"));

        root.addChild(new Token("Start", "Initiations"));
        root.getChildren().get(1).addChild(new Token("Initiations", "Type"));
        root.getChildren().get(1).getChildren().get(0).addChild(new Token("Type", "Light"));

        root.getChildren().get(1).addChild(new Token("Initiations", "DeviceName"));
        root.getChildren().get(1).getChildren().get(1).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren().get(1).addChild(new Token("Initiations", "Identifier"));
        root.getChildren().get(1).getChildren().get(2).addChild(new Token("Identifier", "IDValue"));
        root.getChildren().get(1).getChildren().get(2).getChildren().get(0).addChild(new Token("IDValue", "192.168.0.104"));

        root.getChildren().get(1).addChild(new Token("", "EOL"));
        root.getChildren().get(1).getChildren().get(3).addChild(new Token("EOL", ";"));

        root.addChild(new Token("Start", "Automations"));

        root.getChildren().get(2).addChild(new Token("Automations", "ScopeStart"));
        root.getChildren().get(2).getChildren().get(0).addChild(new Token("ScopeStart", "Begin"));
        root.getChildren().get(2).getChildren().get(0).addChild(new Token("ScopeStart", "("));
        root.getChildren().get(2).getChildren().get(0).addChild(new Token("ScopeStart", "Automation"));
        root.getChildren().get(2).getChildren().get(0).addChild(new Token("ScopeStart", ")"));

        root.getChildren().get(2).addChild(new Token("Automations", "Triggers"));
        root.getChildren().get(2).getChildren().get(1).addChild(new Token("Triggers", "Trigger"));
        root.getChildren().get(2).getChildren().get(1).addChild(new Token("Triggers", "("));
        root.getChildren().get(2).getChildren().get(1).addChild(new Token("Triggers", "BoolExpr"));
        root.getChildren().get(2).getChildren().get(1).addChild(new Token("Triggers", ")"));
        root.getChildren().get(2).getChildren().get(1).addChild(new Token("", "EOL"));

        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).addChild(new Token("BoolExpr", "Bool"));

        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).addChild(new Token("Bool", "Expr"));
        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).addChild(new Token("Bool", "BoolOperator"));
        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).addChild(new Token("Bool", "Expr"));
        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).getChildren().get(0).addChild(new Token("Expr", "TimeValue"));
        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).addChild(new Token("TimeValue", "TIME"));

        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).getChildren().get(1).addChild(new Token("BoolOperator", "="));

        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).getChildren().get(2).addChild(new Token("Expr", "TimeValue"));
        root.getChildren().get(2).getChildren().get(1).getChildren().get(2).getChildren().get(0).getChildren().get(2).getChildren().get(0).addChild(new Token("TimeValue", "08:00"));

        root.getChildren().get(2).getChildren().get(1).getChildren().get(4).addChild(new Token("EOL", ";"));

        root.getChildren().get(2).addChild(new Token("Automations", "Actions"));
        root.getChildren().get(2).getChildren().get(2).addChild(new Token("Actions", "Action"));
        root.getChildren().get(2).getChildren().get(2).addChild(new Token("Actions", "("));
        root.getChildren().get(2).getChildren().get(2).addChild(new Token("Actions", "Changes"));
        root.getChildren().get(2).getChildren().get(2).addChild(new Token("Actions", ")"));
        root.getChildren().get(2).getChildren().get(2).addChild(new Token("", "EOL"));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).addChild(new Token("Changes", "Attribute"));
        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).addChild(new Token("Changes", "Expr"));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(0).addChild(new Token("Attribute", "DeviceName"));
        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(0).getChildren().get(0).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(0).addChild(new Token("Attribute", "."));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(0).addChild(new Token("Attribute", "AttributeName"));
        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(0).getChildren().get(2).addChild(new Token("AttributeName", "On"));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(1).addChild(new Token("Expr", "BoolValue"));
        root.getChildren().get(2).getChildren().get(2).getChildren().get(2).getChildren().get(1).getChildren().get(0).addChild(new Token("BoolValue", "TRUE"));

        root.getChildren().get(2).getChildren().get(2).getChildren().get(4).addChild(new Token("EOL", ";"));

        root.getChildren().get(2).addChild(new Token("Automations", "ScopeEnd"));
        root.getChildren().get(2).getChildren().get(3).addChild(new Token("ScopeEnd", "End"));
        root.getChildren().get(2).getChildren().get(3).addChild(new Token("ScopeEnd", "("));
        root.getChildren().get(2).getChildren().get(3).addChild(new Token("ScopeEnd", "Automation"));
        root.getChildren().get(2).getChildren().get(3).addChild(new Token("ScopeEnd", ")"));
        root.getChildren().get(2).addChild(new Token("", "EOL"));
        root.getChildren().get(2).getChildren().get(4).addChild(new Token("EOL", ";"));

        Translator translator = new Translator();
        translator.traverseTree(root);

        Automation.AutomationThread automationThread = (Automation.AutomationThread) translator.getAutomation().getAutomationThreads().get(0);

        assertEquals(TimeTrigger.class, automationThread.getTriggerList().get(0).getClass());
        assertEquals(OnAction.class, automationThread.getActionList().get(0).getClass());
        assertEquals(WiZController.class, automationThread.getController().getClass());
        assertEquals("192.168.0.104", automationThread.getIdentifier());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            translator.getAutomation().getAutomationThreads().get(1);
        });
    }

}