package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BooleanAttribute<OWNER> extends Attribute<OWNER, Boolean> {
    public BooleanAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    @Override
    public Class<?> bindType() {
        return Boolean.class;
    }

    public Predicate eq(boolean value) {
        return new Predicate(attribute.eq(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate in(boolean value) {
        return eq(value);
    }

    public Predicate in(boolean ... value) {
        Set<Boolean> values = new HashSet<>();
        for (boolean b : value) {
            values.add(b);
        }
        List<SqlBindParam<Boolean>> params = values.stream().map(it -> new SqlBindParam<>(it, Boolean.class)).collect(Collectors.toList());
        return new Predicate(attribute.in(params));
    }

    public Predicate not(boolean value) {
        return new Predicate(attribute.noteq(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate gt(boolean value) {
        return new Predicate(attribute.gt(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate gteq(boolean value) {
        return new Predicate(attribute.gteq(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate lt(boolean value) {
        return new Predicate(attribute.lt(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate lteq(boolean value) {
        return new Predicate(attribute.lteq(new SqlBindParam<>(value, Boolean.class)));
    }

    public Predicate isTrue() {
        return eq(true);
    }

    public Predicate isFalse() {
        return eq(false);
    }
}
