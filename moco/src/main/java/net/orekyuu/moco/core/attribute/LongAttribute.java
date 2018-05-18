package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LongAttribute<OWNER> extends Attribute<OWNER> {
    public LongAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    @Override
    public Class<?> bindType() {
        return Long.class;
    }

    public Predicate eq(long value) {
        return new Predicate(attribute.eq(new SqlBindParam<>(value, Long.class)));
    }

    public Predicate in(long value) {
        return eq(value);
    }

    public Predicate in(long ... value) {
        List<SqlBindParam> paramList = LongStream.of(value)
                .distinct()
                .mapToObj(i -> new SqlBindParam<>(i, Long.class))
                .collect(Collectors.toList());
        return new Predicate(attribute.in(paramList));
    }

    public Predicate not(long value) {
        return new Predicate(attribute.noteq(new SqlBindParam<>(value, Long.class)));
    }

    public Predicate gt(long value) {
        return new Predicate(attribute.gt(new SqlBindParam<>(value, Long.class)));
    }

    public Predicate gteq(long value) {
        return new Predicate(attribute.gteq(new SqlBindParam<>(value, Long.class)));
    }

    public Predicate lt(long value) {
        return new Predicate(attribute.lt(new SqlBindParam<>(value, Long.class)));
    }

    public Predicate lteq(long value) {
        return new Predicate(attribute.lteq(new SqlBindParam<>(value, Long.class)));
    }
}
