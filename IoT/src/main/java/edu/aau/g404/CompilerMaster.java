package edu.aau.g404;

import edu.aau.g404.translator.Translator;
import edu.aau.g404.ContextualAnalyzer.ContextualAnalyzer;
import edu.aau.g404.LexicalAnalyzer.LexiScanner;
import edu.aau.g404.LexicalAnalyzer.ASTBuilder;

public class CompilerMaster {

    private static CompilerMaster instance = null;
    private String helloWorld = "Hello world";
    private LexiScanner lexiScanner;
    private ASTBuilder tokenManager;
    private ContextualAnalyzer contextualAnalyzer;
    private Translator translator;

    private Token ast;


    private CompilerMaster(){
        lexiScanner = new LexiScanner("src/main/java/edu/aau/g404/Main.iota");
        tokenManager = new ASTBuilder();
        contextualAnalyzer = new ContextualAnalyzer();
        translator = new Translator();
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
        //ArrayList tokenList = lexiScanner.scanner(); //Scanner returning an arraylist of tokens
        //lexiScanner.printTokens();
        ast = tokenManager.astBuilder(lexiScanner.scanner()); //Parser returning the root token of the AST

        tokenManager.printTree(ast); // An attempt to print the AST in a readable form
        prettyPrintCodeReverse(ast);

        //contextualAnalyzer.depthFirstTraverser(root);

        contextualAnalyzer.checkForTypeErrors(ast);

        translator.execute(ast);
    }


    public void prettyPrintCodeReverse(Token node){
        if (node.getChildren()!=null){
            for (Token t: node.getChildren()) {
                prettyPrintCodeReverse(t);
            }
        } else {
            //work on the node is done here
            System.out.print(node.getValue());
            if (node.getValue().equals(";")){
                System.out.println("");
            } else {
                System.out.print(" ");
            }
        }
    }
}
