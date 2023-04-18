package edu.aau.g404;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class LexiScanner {

    private Map keyTable;

    private FileReader file;

    private int charPointer;

    public LexiScanner(String path) {
        try {
            file = new FileReader(path);
        } catch (FileNotFoundException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
        keyTable = new HashMap<String, String>();
        keyTable.put("Begin", "Type");
        keyTable.put("Trigger", "Type");
        keyTable.put("Action", "Type");
        keyTable.put("End", "Type");
        keyTable.put("Light", "Type");
        keyTable.put("Sensor", "Type");
        //keyTable.put("Automation", "Type");
    }


    public void scanner() {
        char currentChar;
        String currentWord = "";
        while ((currentChar = this.readNextChar()) != '$') {
            if (isLetter(currentChar) || isDigit(currentChar) || currentChar == '-' || currentChar == '_') {
                currentWord += currentChar;
                while (isLetter(currentChar = this.readNextChar()) || isDigit(currentChar) || currentChar == '-' || currentChar == '_'){
                    currentWord += currentChar;
                }
                System.out.print(currentWord);
                currentWord = "";
            }
            if (currentChar == ' ' || currentChar == '\n') {
                printChar(currentChar);
            } else {//if (!isLetter(currentChar) && currentChar != '-' && currentChar != '_') {
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
