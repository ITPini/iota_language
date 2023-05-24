package edu.aau.g404.integrationTest;

import edu.aau.g404.LexicalAnalyzer.ASTBuilder;
import edu.aau.g404.LexicalAnalyzer.LexiScanner;
import edu.aau.g404.Token;
import edu.aau.g404.api.wiz.WiZController;
import edu.aau.g404.core.Automation;
import edu.aau.g404.core.action.light.OnAction;
import edu.aau.g404.core.trigger.TimeTrigger;
import edu.aau.g404.translator.Translator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private LexiScanner lexiScanner;
    private ASTBuilder astBuilder;
    private Translator translator;
    private File file;

    @BeforeEach
    void init() {
        astBuilder = new ASTBuilder();
        translator = new Translator();
        try {
            file = File.createTempFile("TestProgram", ".iota");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        if (file.exists()){
            file.delete();
        }
    }

    @Test
    void run(){
        String testProgram = "Use Wiz;\n" +
                "Light MyWizLight \"192.168.0.105\";\n" +
                "Begin(Automation)\n" +
                "Trigger(TIME = 08:00);\n" +
                "Action(MyWizLight.On TRUE);\n" +
                "End(Automation);";

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(testProgram);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lexiScanner = new LexiScanner(file.getAbsolutePath());

        Token root = astBuilder.astBuilder(lexiScanner.scanner());

        translator.execute(root);

        Automation.AutomationThread automationThread = (Automation.AutomationThread) translator.getAutomation().getAutomationThreads().get(0);

        assertEquals(TimeTrigger.class, automationThread.getTriggerList().get(0).getClass());
        assertEquals(OnAction.class, automationThread.getActionList().get(0).getClass());
        assertEquals(WiZController.class, automationThread.getController().getClass());
        assertEquals("192.168.0.105", automationThread.getIdentifier());
    }
}
