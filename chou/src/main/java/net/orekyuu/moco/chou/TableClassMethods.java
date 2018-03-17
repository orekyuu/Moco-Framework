package net.orekyuu.moco.chou;

import com.squareup.javapoet.*;
import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class TableClassMethods {

    private TableClassMethods() {
        throw new UnsupportedOperationException();
    }

    public static MethodSpec createMethod(OriginalEntity entity) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("create")
                .returns(TypeName.VOID)
                .addParameter(
                        ParameterSpec.builder(TypeName.get(entity.getOriginalType().asType()), "entity")
                                .addAnnotation(AnnotationSpec.builder(Nonnull.class).build())
                                .build())
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T insert = new $T(TABLE)", Insert.class, Insert.class);

        String attrs = entity.getColumnFields().stream()
                .map(ColumnField::tableClassColumnName)
                .map(str -> str + ".ast()")
                .collect(Collectors.joining(", "));
        builder.addStatement("insert.setAttributes(Arrays.asList($L))", attrs);

        CodeBlock.Builder codeBlock = CodeBlock.builder();
        codeBlock.add("insert.setValues(new $T($T.asList(", SqlNodeArray.class, Arrays.class);
        Iterator<ColumnField> columnFieldIterator = entity.getColumnFields().iterator();
        while (columnFieldIterator.hasNext()) {
            ColumnField columnField = columnFieldIterator.next();
            if (columnField.getColumn().generatedValue()) {
                continue;
            }
            codeBlock.add("new $T($L.getAccessor().get(entity), $L.bindType())", SqlBindParam.class, columnField.tableClassColumnName(), columnField.tableClassColumnName());
            if (columnFieldIterator.hasNext()) {
                codeBlock.add(", ");
            }
        }
        builder.addCode(codeBlock.add(")));\n").build());

        builder.addStatement("insert.executeQuery($T.getConnection())", ConnectionManager.class);
        return builder.build();
    }
}
