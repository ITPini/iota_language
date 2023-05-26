package edu.aau.g404;

import java.io.*;

public class Compiler {
    public static void main(String[] args) {

        CompilerMaster compilerMaster = CompilerMaster.getInstance();
        compilerMaster.runCompiler();

    }
}