package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringAttribute<OWNER> extends Attribute<OWNER> {
    public StringAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    public Predicate eq(String value) {
        return new Predicate(attribute.eq(new SqlBindParam(value, String.class)));
    }

    public Predicate in(String value) {
        return eq(value);
    }

    public Predicate in(String ... value) {
        List<SqlBindParam> paramList = Stream.of(value)
                .distinct()
                .map(i -> new SqlBindParam(i, String.class))
                .collect(Collectors.toList());
        return new Predicate(attribute.in(paramList));
    }

    public Predicate not(String value) {
        return new Predicate(attribute.noteq(new SqlBindParam(value, String.class)));
    }

    public Predicate gt(String value) {
        return new Predicate(attribute.gt(new SqlBindParam(value, String.class)));
    }

    public Predicate gteq(String value) {
        return new Predicate(attribute.gteq(new SqlBindParam(value, String.class)));
    }

    public Predicate lt(String value) {
        return new Predicate(attribute.lt(new SqlBindParam(value, String.class)));
    }

    public Predicate lteq(String value) {
        return new Predicate(attribute.lteq(new SqlBindParam(value, String.class)));
    }
}
