package edu.aau.g404.LexicalAnalyzer;

import edu.aau.g404.SymbolTable;
import edu.aau.g404.Token;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class LexiScanner {

    private FileReader file;

    private int charPointer;

    private ArrayList<Token> codeAsTokens;

    public LexiScanner(String path) {
        try {
            file = new FileReader(path);
        } catch (FileNotFoundException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
        codeAsTokens = new ArrayList<>();

        //keywords
        SymbolTable.addValue("Use", "Package");
        SymbolTable.addValue("Begin", "ScopeStart");
        SymbolTable.addValue("Trigger", "Triggers");
        SymbolTable.addValue("Action", "Actions");
        SymbolTable.addValue("End", "ScopeEnd");
        SymbolTable.addValue("Light", "Type");
        SymbolTable.addValue("Sensor", "Type");
        SymbolTable.addValue("and", "BoolExpr");
        SymbolTable.addValue("or", "BoolExpr");
        SymbolTable.addValue("TIME", "Key");
        SymbolTable.addValue("Automation", "");




        //terminals

    }


    public ArrayList<Token> scanner() {
        String errorMessage = "";
        char currentChar;
        //char nextChar;
        String currentWord = "";
        //int count = 0;
        System.out.println("Begin scanning!");

        while ((currentChar = this.readNextChar()) != '$' && errorMessage == "") {
            //count++;
            //System.out.println(count);
            if (isLetter(currentChar)) {
                currentWord += currentChar;
                while (isLetter(currentChar = this.readNextChar()) || isDigit(currentChar) || currentChar == '-' || currentChar == '_') {
                    currentWord += currentChar;
                }
                if (SymbolTable.get(currentWord) != null) {
                        codeAsTokens.add(new Token(SymbolTable.get(currentWord), currentWord));
                } else {
                    codeAsTokens.add(new Token("Name", currentWord));
                }
            } else if (currentChar == '"') {
                //IDValue
                currentWord +=currentChar;
                while ((currentChar = readNextChar()) != '"'){
                    if (currentChar == '-'||currentChar == '_'||isDigit(currentChar)||isLetter(currentChar)){
                        currentWord += currentChar;
                    } else {
                        errorMessage = "ERROR: " + currentChar + " character not valid in IDValue";
                    }
                }
                currentWord+=currentChar;
                currentChar=readNextChar();
                codeAsTokens.add(new Token("IDValue", currentWord));

            } else if (isDigit(currentChar)) {
                //time or numerical value
                currentWord += currentChar;
                while (isDigit(currentChar = this.readNextChar()) || currentChar == ':' || currentChar == '.') {
                    currentWord += currentChar;
                    if (currentChar == ':') {
                        for (int i = 0; i < 2; i++) {
                            currentChar = this.readNextChar();
                            if (isDigit(currentChar)) {
                                currentWord += currentChar;
                            } else {
                                errorMessage = "ERROR TIME must have the format DD:DD, missing number after :";
                            }
                        }
                        //Token is TimeValue
                        codeAsTokens.add(new Token("TimeValue", currentWord));
                    }

                }
                if (codeAsTokens.get(codeAsTokens.size() - 1).getType() != "TimeValue") {
                    //check if time, if not, it is Value
                    codeAsTokens.add(new Token("Value", currentWord));
                }

            }
            if (currentChar == ' ' || currentChar == '\n' || currentChar == '\t' || currentChar == '\r') {
                //Blank space handler
                //printChar(currentChar);
            } else {
                switch (currentChar) {
                    case '*':
                    case '+':
                    case '-':
                        codeAsTokens.add(new Token("Operator", "" + currentChar));
                        break;
                    case '=': case '<': case '>':
                        codeAsTokens.add(new Token("Bool", "" + currentChar));
                        break;
                    case '.':
                        //Attribute
                        codeAsTokens.add(new Token("Attribute", "" + currentChar));
                        break;
                    case ',':
                        // Group or several values
                        codeAsTokens.add(new Token("Identifier", "" + currentChar));
                        break;
                    case ';':
                        //End Of Line
                        codeAsTokens.add(new Token("EOL", "" + currentChar));
                        break;
                    case '(': case ')':
                        codeAsTokens.add(new Token("", "" + currentChar));
                        break;

                    default:
                        errorMessage = "ERROR: Invalid char, " + currentChar + " not allowed";
                }

            }
            currentWord = "";
        }
        lastChecker();
        if (errorMessage != "") {
            System.out.println(errorMessage);
        }
        return codeAsTokens;
    }

    private void lastChecker() {
        // Should check if numbers of ( matched numbers of ) and other things
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

    public void printTokens(){
        for (Token e: codeAsTokens) {
            System.out.print(e.getValue() + " ");
            if(e.getType()=="EOL") {System.out.println(" ");}
        }
        System.out.println("Number of tokens = " + codeAsTokens.size());
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
