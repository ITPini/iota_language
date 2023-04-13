package edu.aau.g404;

public class CompilerMaster {

    private static CompilerMaster instance = null;
    private String helloWorld = "Hello world";
    private LexiScanner lexiScanner;


    private CompilerMaster(){
        lexiScanner = new LexiScanner("src/main/java/edu/aau/g404/TestProgram.txt");
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
        lexiScanner.printFile();
    }
}
