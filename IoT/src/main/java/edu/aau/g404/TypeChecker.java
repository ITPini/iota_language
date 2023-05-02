package edu.aau.g404;

import java.util.ArrayList;
import java.util.Map;

public class TypeChecker {
    public static void checkTypes(){
        TypeCheckVisitor visitor = new TypeCheckVisitor();
    }
}

    private class TypeCheckVisitor extends TreeConstructionWorker {

        public TypeCheckVisitor() {
            }
    }
