package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.core.UpdateValuePair;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeExpression;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeAttribute<OWNER> extends Attribute<OWNER> {
    public LocalDateTimeAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    @Override
    public Class<?> bindType() {
        return Timestamp.class;
    }

    private Timestamp convert(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    private SqlBindParam<Timestamp> param(LocalDateTime dateTime) {
        return new SqlBindParam<>(convert(dateTime), Timestamp.class);
    }

    public Predicate gt(LocalDateTime value) {
        return new Predicate(attribute.gt(param(value)));
    }

    public Predicate gteq(LocalDateTime value) {
        return new Predicate(attribute.gteq(param(value)));
    }

    public Predicate lt(LocalDateTime value) {
        return new Predicate(attribute.lt(param(value)));
    }

    public Predicate lteq(LocalDateTime value) {
        return new Predicate(attribute.lteq(param(value)));
    }

    public Predicate after(LocalDateTime time) {
        return lt(time);
    }

    public Predicate before(LocalDateTime time) {
        return gt(time);
    }

    public Predicate between(LocalDateTime before, LocalDateTime after) {
        SqlNodeExpression expression = lteq(before).getExpression().and(gt(after).getExpression());
        return new Predicate(expression);
    }

    public Predicate allDay(LocalDate date) {
        return between(date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }

    @SuppressWarnings("unchecked")
    public UpdateValuePair<OWNER> set(Object value) {
        LocalDateTime localDateTime = (LocalDateTime) value;
        return UpdateValuePair.of(this, new SqlBindParam<>(convert(localDateTime), Timestamp.class));
    }
}
