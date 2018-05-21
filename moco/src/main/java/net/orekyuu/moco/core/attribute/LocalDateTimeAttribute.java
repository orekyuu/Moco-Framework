package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.core.UpdateValuePair;
import net.orekyuu.moco.feeling.node.SqlAnd;
import net.orekyuu.moco.feeling.node.SqlBetween;
import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeAttribute<OWNER> extends Attribute<OWNER, LocalDateTime> {
    public LocalDateTimeAttribute(net.orekyuu.moco.feeling.attributes.Attribute attribute, AttributeValueAccessor<OWNER> accessor) {
        super(attribute, accessor);
    }

    @Override
    public Class<?> bindType() {
        return Timestamp.class;
    }

    private Timestamp convert(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
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
        SqlBetween between = new SqlBetween(attribute, new SqlAnd(param(before), param(after)));
        return new Predicate(between);
    }

    public Predicate allDay(LocalDate date) {
        return between(date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }

    @SuppressWarnings("unchecked")
    @Override
    public UpdateValuePair<OWNER, LocalDateTime> set(LocalDateTime value) {
        return UpdateValuePair.of(this, new SqlBindParam<>(convert(value), Timestamp.class));
    }
}
