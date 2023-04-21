package edu.aau.g404;

import java.util.ArrayList;

public class CompilerMaster {

    private static CompilerMaster instance = null;
    private String helloWorld = "Hello world";
    private LexiScanner lexiScanner;
    private TreeConstructionWorker tokenManager;


    private CompilerMaster(){
        lexiScanner = new LexiScanner("src/main/java/edu/aau/g404/TestProgram.txt");
        tokenManager = new TreeConstructionWorker();
    }

    public static CompilerMaster getInstance(){
        if(instance == null){
            instance = new CompilerMaster();
        }
        return instance;
    }

    public void printHelloWorld(){
        System.out.println(helloWorld);
    }



    public void runCompiler(){
        ArrayList tokenList = lexiScanner.scanner();
        lexiScanner.printTokens();
        Token root = tokenManager.astBuilder(tokenList);
        tokenManager.printTree(root);
    }
}
