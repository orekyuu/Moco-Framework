package net.orekyuu.moco.chou;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class CodeGenerateOperation {

    public static void run(Messager messager, Runnable runnable) {
        try {
            runnable.run();
        } catch (CompilerException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement());
        }
    }
}
