package net.orekyuu.moco.chou.entity;


import com.squareup.javapoet.ClassName;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.attribute.AttributeField;
import net.orekyuu.moco.core.annotations.Table;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityClass {
    private final Table table;
    private final PackageElement packageElement;
    private final TypeElement entityType;
    private final List<AttributeField> attributeFields;
    private final List<RelationField> relationFields;

    private EntityClass(Table table, PackageElement packageElement, TypeElement entityType, List<AttributeField> attributeFields, List<RelationField> relationFields) {
        this.table = Objects.requireNonNull(table);
        this.packageElement = Objects.requireNonNull(packageElement);
        this.entityType = Objects.requireNonNull(entityType);
        this.attributeFields = Objects.requireNonNull(attributeFields);
        this.relationFields = relationFields;
    }

    public Table getTable() {
        return table;
    }

    public PackageElement getPackageElement() {
        return packageElement;
    }

    public TypeElement getEntityType() {
        return entityType;
    }

    public ClassName getClassName() {
        return ClassName.get(entityType);
    }

    public List<AttributeField> getAttributeFields() {
        return attributeFields;
    }

    public List<RelationField> getRelationFields() {
        return relationFields;
    }

    public ClassName getTableClassName() {
        String packageName = packageElement.getQualifiedName().toString();
        String simpleName = NamingUtils.multipleName(entityType.getSimpleName().toString());
        return ClassName.get(packageName, simpleName);
    }

    public ClassName getEntityListClassName() {
        String packageName = packageElement.getQualifiedName().toString();
        String simpleName = entityType.getSimpleName().toString() + "List";
        return ClassName.get(packageName, simpleName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntityClass{");
        sb.append("table=").append(table);
        sb.append(", packageElement=").append(packageElement);
        sb.append(", entityType=").append(entityType);
        sb.append(", attributeField=").append(attributeFields);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private Table table;
        private PackageElement packageElement;
        private TypeElement originalType;
        private final List<AttributeField> attributeFields = new ArrayList<>();
        private final List<RelationField> relationFields = new ArrayList<>();

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

        public Builder addColumnField(AttributeField attributeField) {
            this.attributeFields.add(attributeField);
            return this;
        }

        public Builder addRelationField(RelationField relationField) {
            this.relationFields.add(relationField);
            return this;
        }

        public EntityClass build() {
            return new EntityClass(table, packageElement, originalType, attributeFields, relationFields);
        }
    }
}
