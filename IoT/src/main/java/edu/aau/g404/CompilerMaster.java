package edu.aau.g404;

public class CompilerMaster {

    private static CompilerMaster instance = null;
    private String helloWorld = "Hello world";


    private CompilerMaster(){

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
}
