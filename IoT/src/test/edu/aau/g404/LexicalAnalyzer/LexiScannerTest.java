package edu.aau.g404.LexicalAnalyzer;

import edu.aau.g404.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LexiScannerTest {
    private LexiScanner lexiScanner;
    private File file;

    @BeforeEach
    void init() {
        try {
            file = File.createTempFile("LexiTestProgram", ".txt");
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
    void scanner() {
        String testProgram = "Use Hue;\n" +
                "Begin\n" +
                "Trigger LightSensor;\n" +
                "Action Light;\n" +
                "End;";
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(testProgram);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lexiScanner = new LexiScanner(file.getAbsolutePath());
        ArrayList<Token> tokenTest = lexiScanner.scanner();
        assertEquals(12 , tokenTest.size());
        assertEquals("Package", tokenTest.get(0).getType());
        assertEquals("PackageName", tokenTest.get(1).getType());
        assertEquals("EOL", tokenTest.get(2).getType());
        assertEquals("ScopeStart", tokenTest.get(3).getType());
        assertEquals("Triggers", tokenTest.get(4).getType());
        assertEquals("DeviceName", tokenTest.get(5).getType());
        assertEquals("EOL", tokenTest.get(6).getType());
        assertEquals("Actions", tokenTest.get(7).getType());
        assertEquals("Type", tokenTest.get(8).getType());
        assertEquals("EOL", tokenTest.get(9).getType());
        assertEquals("ScopeEnd", tokenTest.get(10).getType());
        assertEquals("EOL", tokenTest.get(11).getType());
        ASTBuilder astBuilder = new ASTBuilder();
        Token astToken = astBuilder.astBuilder(tokenTest);
        astBuilder.printTree(astToken);
    }
}