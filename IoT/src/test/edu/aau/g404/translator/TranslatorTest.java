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
    Translator translator = new Translator();

    @AfterEach
    void tearDown() {
        root = new Token("Start", "Start"); // Reset root after each test
        translator = new Translator();
    }

    @Test
    void traverseTreeInitialisation() {
        root.addChild(new Token("Start", "Initiations"));
        root.getChildren(0).addChild(new Token("Initiations", "Type"));
        root.getChildren(0).getChildren(0).addChild(new Token("Type", "Light"));

        root.getChildren(0).addChild(new Token("Initiations", "DeviceName"));
        root.getChildren(0).getChildren(1).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren(0).addChild(new Token("Initiations", "Identifier"));
        root.getChildren(0).getChildren(2).addChild(new Token("Identifier", "IDValue"));
        root.getChildren(0).getChildren(2).getChildren(0).addChild(new Token("IDValue", "b2ef7371-9321-452a-a70e-49ce5b6cd879"));

        root.getChildren(0).addChild(new Token("", "EOL"));
        root.getChildren(0).getChildren(3).addChild(new Token("EOL", ";"));

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
        root.getChildren(0).addChild(new Token("Package", "Use"));

        root.getChildren(0).addChild(new Token("Package", "PackageName"));
        root.getChildren(0).getChildren(1).addChild(new Token("PackageName", "WiZ"));

        root.getChildren(0).addChild(new Token("", "EOL"));
        root.getChildren(0).getChildren(2).addChild(new Token("EOL", ";"));

        translator.traverseTree(root);

        assertNotNull(translator.getPackagesAllowed());
        assertNull(translator.getDevices().get("LivingRoomLight1"));
        assertEquals(0, translator.getGlobalTriggers().size());

        assertEquals(WiZController.class, translator.getPackagesAllowed().get("WiZ").getClass());
    }

    @Test
    void traverseTreeAutomation(){
        root.addChild(new Token("Start", "Package"));
        root.getChildren(0).addChild(new Token("Package", "Use"));

        root.getChildren(0).addChild(new Token("Package", "PackageName"));
        root.getChildren(0).getChildren(1).addChild(new Token("PackageName", "WiZ"));

        root.getChildren(0).addChild(new Token("", "EOL"));
        root.getChildren(0).getChildren(2).addChild(new Token("EOL", ";"));

        root.addChild(new Token("Start", "Initiations"));
        root.getChildren(1).addChild(new Token("Initiations", "Type"));
        root.getChildren(1).getChildren(0).addChild(new Token("Type", "Light"));

        root.getChildren(1).addChild(new Token("Initiations", "DeviceName"));
        root.getChildren(1).getChildren(1).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren(1).addChild(new Token("Initiations", "Identifier"));
        root.getChildren(1).getChildren(2).addChild(new Token("Identifier", "IDValue"));
        root.getChildren(1).getChildren(2).getChildren(0).addChild(new Token("IDValue", "192.168.0.104"));

        root.getChildren(1).addChild(new Token("", "EOL"));
        root.getChildren(1).getChildren(3).addChild(new Token("EOL", ";"));

        root.addChild(new Token("Start", "Automations"));

        root.getChildren(2).addChild(new Token("Automations", "ScopeStart"));
        root.getChildren(2).getChildren(0).addChild(new Token("ScopeStart", "Begin"));
        root.getChildren(2).getChildren(0).addChild(new Token("ScopeStart", "("));
        root.getChildren(2).getChildren(0).addChild(new Token("ScopeStart", "Automation"));
        root.getChildren(2).getChildren(0).addChild(new Token("ScopeStart", ")"));

        root.getChildren(2).addChild(new Token("Automations", "Triggers"));
        root.getChildren(2).getChildren(1).addChild(new Token("Triggers", "Trigger"));
        root.getChildren(2).getChildren(1).addChild(new Token("Triggers", "("));
        root.getChildren(2).getChildren(1).addChild(new Token("Triggers", "BoolExpr"));
        root.getChildren(2).getChildren(1).addChild(new Token("Triggers", ")"));
        root.getChildren(2).getChildren(1).addChild(new Token("", "EOL"));

        root.getChildren(2).getChildren(1).getChildren(2).addChild(new Token("BoolExpr", "Bool"));

        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).addChild(new Token("Bool", "Expr"));
        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).addChild(new Token("Bool", "BoolOperator"));
        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).addChild(new Token("Bool", "Expr"));
        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).getChildren(0).addChild(new Token("Expr", "TimeValue"));
        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).getChildren(0).getChildren(0).addChild(new Token("TimeValue", "TIME"));

        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).getChildren(1).addChild(new Token("BoolOperator", "="));

        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).getChildren(2).addChild(new Token("Expr", "TimeValue"));
        root.getChildren(2).getChildren(1).getChildren(2).getChildren(0).getChildren(2).getChildren(0).addChild(new Token("TimeValue", "08:00"));

        root.getChildren(2).getChildren(1).getChildren(4).addChild(new Token("EOL", ";"));

        root.getChildren(2).addChild(new Token("Automations", "Actions"));
        root.getChildren(2).getChildren(2).addChild(new Token("Actions", "Action"));
        root.getChildren(2).getChildren(2).addChild(new Token("Actions", "("));
        root.getChildren(2).getChildren(2).addChild(new Token("Actions", "Changes"));
        root.getChildren(2).getChildren(2).addChild(new Token("Actions", ")"));
        root.getChildren(2).getChildren(2).addChild(new Token("", "EOL"));

        root.getChildren(2).getChildren(2).getChildren(2).addChild(new Token("Changes", "Attribute"));
        root.getChildren(2).getChildren(2).getChildren(2).addChild(new Token("Changes", "Expr"));

        root.getChildren(2).getChildren(2).getChildren(2).getChildren(0).addChild(new Token("Attribute", "DeviceName"));
        root.getChildren(2).getChildren(2).getChildren(2).getChildren(0).getChildren(0).addChild(new Token("DeviceName", "LivingRoomLight1"));

        root.getChildren(2).getChildren(2).getChildren(2).getChildren(0).addChild(new Token("Attribute", "."));

        root.getChildren(2).getChildren(2).getChildren(2).getChildren(0).addChild(new Token("Attribute", "AttributeName"));
        root.getChildren(2).getChildren(2).getChildren(2).getChildren(0).getChildren(2).addChild(new Token("AttributeName", "On"));

        root.getChildren(2).getChildren(2).getChildren(2).getChildren(1).addChild(new Token("Expr", "BoolValue"));
        root.getChildren(2).getChildren(2).getChildren(2).getChildren(1).getChildren(0).addChild(new Token("BoolValue", "TRUE"));

        root.getChildren(2).getChildren(2).getChildren(4).addChild(new Token("EOL", ";"));

        root.getChildren(2).addChild(new Token("Automations", "ScopeEnd"));
        root.getChildren(2).getChildren(3).addChild(new Token("ScopeEnd", "End"));
        root.getChildren(2).getChildren(3).addChild(new Token("ScopeEnd", "("));
        root.getChildren(2).getChildren(3).addChild(new Token("ScopeEnd", "Automation"));
        root.getChildren(2).getChildren(3).addChild(new Token("ScopeEnd", ")"));
        root.getChildren(2).addChild(new Token("", "EOL"));
        root.getChildren(2).getChildren(4).addChild(new Token("EOL", ";"));

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