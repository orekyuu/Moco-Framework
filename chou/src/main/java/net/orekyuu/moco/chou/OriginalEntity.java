package net.orekyuu.moco.chou;


import com.squareup.javapoet.ClassName;
import net.orekyuu.moco.core.annotations.Table;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OriginalEntity {
    private Table table;
    private PackageElement packageElement;
    private TypeElement originalType;
    private List<ColumnField> columnFields;

    private OriginalEntity(Table table, PackageElement packageElement, TypeElement originalType, List<ColumnField> columnFields) {
        this.table = Objects.requireNonNull(table);
        this.packageElement = Objects.requireNonNull(packageElement);
        this.originalType = Objects.requireNonNull(originalType);
        this.columnFields = Objects.requireNonNull(columnFields);
    }

    public Table getTable() {
        return table;
    }

    public PackageElement getPackageElement() {
        return packageElement;
    }

    public TypeElement getOriginalType() {
        return originalType;
    }

    public ClassName originalClassName() {
        return ClassName.get(originalType);
    }

    public List<ColumnField> getColumnFields() {
        return columnFields;
    }

    public ClassName toTableClassName() {
        String packageName = packageElement.getQualifiedName().toString();
        String simpleName = NamingUtils.multipleName(originalType.getSimpleName().toString());
        return ClassName.get(packageName, simpleName);
    }

    public ClassName toEntityListClassName() {
        String packageName = packageElement.getQualifiedName().toString();
        String simpleName = originalType.getSimpleName().toString() + "List";
        return ClassName.get(packageName, simpleName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OriginalEntity{");
        sb.append("table=").append(table);
        sb.append(", packageElement=").append(packageElement);
        sb.append(", originalType=").append(originalType);
        sb.append(", columnFields=").append(columnFields);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private Table table;
        private PackageElement packageElement;
        private TypeElement originalType;
        private List<ColumnField> columnFields = new ArrayList<>();

        public Builder table(Table table) {
            this.table = table;
            return this;
        }

        public Builder packageElement(PackageElement packageElement) {
            this.packageElement = packageElement;
            return this;
        }

        public Builder originalType(TypeElement originalType) {
            this.originalType = originalType;
            return this;
        }

        public Builder addColumnField(ColumnField columnField) {
            this.columnFields.add(columnField);
            return this;
        }

        public OriginalEntity build() {
            return new OriginalEntity(table, packageElement, originalType, columnFields);
        }
    }
}
