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
        keyTable.put("Light", "Type");
        keyTable.put("Sensor", "Type");
        keyTable.put("Begin", "Type");
        keyTable.put("Trigger", "Type");
        keyTable.put("Action", "Type");
        keyTable.put("Light", "Type");
        keyTable.put("Light", "Type");
    }

    public int getCharPointer() {
        return charPointer;
    }

    public void setCharPointer(int charPointer) {
        this.charPointer = charPointer;
    }

    public void Scanner() {
        char currentChar;
        while ((currentChar = this.readNextChar()) != '$') {
            if (currentChar == ' ' || currentChar == '\n') {
            } else if (!isLetter(currentChar) && currentChar != '-' && currentChar != '_') {
                switch (currentChar) {
                    case '\"':
                        ;
                        break;
                    case '*':
                        ;
                        break;
                    case '+':
                        ;
                        break;
                    case '.':
                        ;
                        break;
                    case ',':
                        ;
                        break;
                    case ';':
                        ;
                        break;
                    default:
                }

            } else {
                String currentWord = "";
                currentWord += currentChar;
                while (isLetter(currentChar = this.readNextChar()) || isDigit(currentChar) || currentChar == '-' || currentChar == '_') {
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


}
