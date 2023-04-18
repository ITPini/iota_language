package edu.aau.g404;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class LexiScanner {

    private Map<String, String> keyTable;

    private FileReader file;

    private int charPointer;

    private TreeConstructionWorker tokenManager = new TreeConstructionWorker();

    public LexiScanner(String path) {
        try {
            file = new FileReader(path);
        } catch (FileNotFoundException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
        keyTable = new HashMap<String, String>();
        //keywords
        keyTable.put("Use", "Package");
        keyTable.put("Begin", "Automations");
        keyTable.put("Trigger", "Triggers");
        keyTable.put("Action", "Actions");
        keyTable.put("End", "Automations");
        keyTable.put("Light", "Type");
        keyTable.put("Sensor", "Type");

        //terminals

    }


    public void scanner() {
        char currentChar;
        String currentWord = "";
        while ((currentChar = this.readNextChar()) != '$') {
            if (isLetter(currentChar) || isDigit(currentChar) || currentChar == '-' || currentChar == '_') {
                currentWord += currentChar;
                while (isLetter(currentChar = this.readNextChar()) || isDigit(currentChar) || currentChar == '-' || currentChar == '_') {
                    currentWord += currentChar;
                }
                tokenManager.addToken(new Token(currentWord));
                tokenManager.setCurrentToken(new Token(currentWord));
                switch (keyTable.get(currentWord)) {
                    case "Package":
                        ;
                        break;
                    case "Automations":
                        ;
                        break;
                    case "Triggers":
                        ;
                        break;
                    case "Actions":
                        ;
                        break;
                    case "Type":
                        ;
                        break;
                    default:
                }

                System.out.print(currentWord);
                currentWord = "";
            }
            if (currentChar == ' ' || currentChar == '\n') {
                printChar(currentChar);
            } else {
                switch (currentChar) {
                    case '\"':
                        printChar(currentChar);
                        break;
                    case '*':
                        printChar(currentChar);
                        break;
                    case '+':
                        printChar(currentChar);
                        break;
                    case '.':
                        printChar(currentChar);
                        break;
                    case ',':
                        printChar(currentChar);
                        break;
                    case ';':
                        printChar(currentChar);
                        break;
                    default:
                        printChar(currentChar);
                }

            }
        }
    }




    public char readNextChar() {
        try {
            this.charPointer = file.read();
        } catch (IOException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
        if (charPointer == -1) {
            return '$';
        } else {
            return (char) charPointer;
        }
    }

    public void printFile() {
        char currentChar;
        while ((currentChar = this.readNextChar()) != '$') {
            System.out.print(currentChar);
        }
    }

    public void printChar(char c) {
        System.out.print(c);
    }

    public int getCharPointer() {
        return charPointer;
    }

    public void setCharPointer(int charPointer) {
        this.charPointer = charPointer;
    }


}
