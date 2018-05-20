package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BigDecimalAttribute<OWNER> extends Attribute<OWNER, BigDecimal> {
    public BigDecimalAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    @Override
    public Class<?> bindType() {
        return BigDecimal.class;
    }

    public Predicate eq(BigDecimal value) {
        return new Predicate(attribute.eq(new SqlBindParam<>(value, BigDecimal.class)));
    }

    public Predicate in(BigDecimal value) {
        return eq(value);
    }

    public Predicate in(BigDecimal ... value) {
        List<SqlBindParam> paramList = Stream.of(value)
                .distinct()
                .map(i -> new SqlBindParam<>(i, BigDecimal.class))
                .collect(Collectors.toList());
        return new Predicate(attribute.in(paramList));
    }

    public Predicate not(BigDecimal value) {
        return new Predicate(attribute.noteq(new SqlBindParam<>(value, BigDecimal.class)));
    }

    public Predicate gt(BigDecimal value) {
        return new Predicate(attribute.gt(new SqlBindParam<>(value, BigDecimal.class)));
    }

    public Predicate gteq(BigDecimal value) {
        return new Predicate(attribute.gteq(new SqlBindParam<>(value, BigDecimal.class)));
    }

    public Predicate lt(BigDecimal value) {
        return new Predicate(attribute.lt(new SqlBindParam<>(value, BigDecimal.class)));
    }

    public Predicate lteq(BigDecimal value) {
        return new Predicate(attribute.lteq(new SqlBindParam<>(value, BigDecimal.class)));
    }
}
