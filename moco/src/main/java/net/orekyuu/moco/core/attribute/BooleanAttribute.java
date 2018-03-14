package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanAttribute<OWNER> extends Attribute<OWNER> {
    public BooleanAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    public Predicate eq(boolean value) {
        return new Predicate(attribute.eq(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate in(boolean value) {
        return eq(value);
    }

    public Predicate in(boolean ... value) {
        List<SqlBindParam> paramList = Stream.of(value)
                .distinct()
                .map(i -> new SqlBindParam(i, Boolean.class))
                .collect(Collectors.toList());
        return new Predicate(attribute.in(paramList));
    }

    public Predicate not(boolean value) {
        return new Predicate(attribute.noteq(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate gt(boolean value) {
        return new Predicate(attribute.gt(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate gteq(boolean value) {
        return new Predicate(attribute.gteq(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate lt(boolean value) {
        return new Predicate(attribute.lt(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate lteq(boolean value) {
        return new Predicate(attribute.lteq(new SqlBindParam(value, Boolean.class)));
    }

    public Predicate isTrue() {
        return eq(true);
    }

    public Predicate isFalse() {
        return eq(false);
    }
}
