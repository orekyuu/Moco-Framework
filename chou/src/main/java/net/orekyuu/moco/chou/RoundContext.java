package net.orekyuu.moco.chou;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public class RoundContext {
    private final RoundEnvironment roundEnvironment;
    private final ProcessingEnvironment processingEnvironment;

    public RoundContext(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) {
        this.roundEnvironment = roundEnvironment;
        this.processingEnvironment = processingEnvironment;
    }

    @Nonnull
    public RoundEnvironment getRoundEnv() {
        return roundEnvironment;
    }

    @Nonnull
    public ProcessingEnvironment getProcessingEnv() {
        return processingEnvironment;
    }
}
