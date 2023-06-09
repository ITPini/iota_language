package edu.aau.g404.LexicalAnalyzer;

import edu.aau.g404.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ASTBuilderTest {

    private ASTBuilder astBuilder;
    private ArrayList<Token> testTokensList = new ArrayList<>(Arrays.asList(
            new Token("Package", "Use"),
            new Token("PackageName", "Hue"),
            new Token("EOL", ";"),
            new Token("Type","Light"),
            new Token("DeviceName","LivingRoomLight1"),
            new Token("IDValue", "b2ef7371-9321-452a-a70e-49ce5b6cd879"),
            new Token("EOL", ";")));

    @Test
    void astBuilder() {
        astBuilder = new ASTBuilder();
        Token astToken = astBuilder.astBuilder(testTokensList);
        astBuilder.printTree(astToken);

        assertEquals("Use", astToken.getChildren(0).getChildren(0).getValue());
        assertEquals("Hue", astToken.getChildren(0).getChildren(1).getChildren(0).getValue());
        assertEquals(";", astToken.getChildren(0).getChildren(2).getChildren(0).getValue());
        assertEquals("Light", astToken.getChildren(1).getChildren(0).getChildren(0).getValue());
        assertEquals("LivingRoomLight1", astToken.getChildren(1).getChildren(1).getChildren(0).getValue());
        assertEquals("b2ef7371-9321-452a-a70e-49ce5b6cd879", astToken.getChildren(1).getChildren(2).getChildren(0).getChildren(0).getValue());
        assertEquals(";", astToken.getChildren(1).getChildren(3).getChildren(0).getValue());

    }
}