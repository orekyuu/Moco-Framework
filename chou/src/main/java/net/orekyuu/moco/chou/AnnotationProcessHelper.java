package net.orekyuu.moco.chou;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class AnnotationProcessHelper {

    public static TypeMirror getTypeMirrorFromClass(RoundContext context, Class clazz, TypeMirror ... types) {
        ProcessingEnvironment processingEnv = context.getProcessingEnv();

        if (types.length == 0) {
            TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(clazz.getCanonicalName());
            return typeElement.asType();
        } else {
            TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(clazz.getCanonicalName());
            return processingEnv.getTypeUtils().getDeclaredType(typeElement, types);
        }
    }
}
