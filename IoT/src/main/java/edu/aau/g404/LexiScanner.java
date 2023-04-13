package edu.aau.g404;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LexiScanner {

    private FileReader file;

    private int charPointer;

    public LexiScanner(String path) {
        try {
            file = new FileReader(path);
        } catch (FileNotFoundException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
    }

    public int getCharPointer() {
        return charPointer;
    }

    public void setCharPointer(int charPointer) {
        this.charPointer = charPointer;
    }


    public char readNextChar(){
        try {
            this.charPointer = file.read();
        } catch (IOException e) { //custom error handler should be implemented at some point
            e.printStackTrace();
        }
        if (charPointer==-1){
            return '$';
        } else {
            return (char)charPointer;
        }
    }

    public void printFile(){
        char currentChar;
        while ((currentChar = this.readNextChar()) != '$'){
            System.out.print(currentChar);
        }
    }





}
