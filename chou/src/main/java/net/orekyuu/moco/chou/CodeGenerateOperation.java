package net.orekyuu.moco.chou;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class CodeGenerateOperation {

    public static void run(RoundContext roundContext, Runnable runnable) {
        try {
            runnable.run();
        } catch (CompilerException e) {
            Messager messager = roundContext.getProcessingEnv().getMessager();
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement());
        }
    }
}
