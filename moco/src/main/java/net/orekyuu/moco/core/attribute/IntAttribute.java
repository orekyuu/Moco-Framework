package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntAttribute extends Attribute {
    public IntAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute) {
        super(attribute);
    }

    public Predicate eq(int value) {
        return new Predicate(attribute.eq(new SqlBindParam(value, Integer.class)));
    }

    public Predicate in(int value) {
        return eq(value);
    }

    public Predicate in(int ... value) {
        List<SqlBindParam> paramList = IntStream.of(value)
                .distinct()
                .mapToObj(i -> new SqlBindParam(i, Integer.class))
                .collect(Collectors.toList());
        return new Predicate(attribute.in(paramList));
    }

    public Predicate not(int value) {
        return new Predicate(attribute.noteq(new SqlBindParam(value, Integer.class)));
    }

    public Predicate gt(int value) {
        return new Predicate(attribute.gt(new SqlBindParam(value, Integer.class)));
    }

    public Predicate gteq(int value) {
        return new Predicate(attribute.gteq(new SqlBindParam(value, Integer.class)));
    }

    public Predicate lt(int value) {
        return new Predicate(attribute.lt(new SqlBindParam(value, Integer.class)));
    }

    public Predicate lteq(int value) {
        return new Predicate(attribute.lteq(new SqlBindParam(value, Integer.class)));
    }
}
