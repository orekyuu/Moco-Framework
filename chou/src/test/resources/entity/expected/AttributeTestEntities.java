import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.ReflectiveOperationException;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.attribute.BigDecimalAttribute;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.EnumAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.LocalDateTimeAttribute;
import net.orekyuu.moco.core.attribute.LongAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.core.internal.TableClassHelper;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class AttributeTestEntities {
    public static final Select.QueryResultMapper<AttributeTestEntity> MAPPER = new Select.QueryResultMapper<AttributeTestEntity>() {
        private Field intValue;

        private Field intValue2;

        private Field longValue;

        private Field longValue2;

        private Field bigDecimalValue;

        private Field booleanValue;

        private Field booleanValue2;

        private Field stringValue;

        private Field hogeValue;

        private Field localDateTimeValue;

        {
            try {
                intValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "intValue");
                intValue2 = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "intValue2");
                longValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "longValue");
                longValue2 = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "longValue2");
                bigDecimalValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "bigDecimalValue");
                booleanValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "booleanValue");
                booleanValue2 = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "booleanValue2");
                stringValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "stringValue");
                hogeValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "hogeValue");
                localDateTimeValue = TableClassHelper.getDeclaredField(AttributeTestEntity.class, "localDateTimeValue");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public AttributeTestEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            AttributeTestEntity record = new AttributeTestEntity();
            intValue.set(record, resultSet.getInt("int_value"));
            intValue2.set(record, resultSet.getInt("int_value2"));
            longValue.set(record, resultSet.getLong("long_value"));
            longValue2.set(record, resultSet.getLong("long_value2"));
            bigDecimalValue.set(record, resultSet.getBigDecimal("big_decimal_value"));
            booleanValue.set(record, resultSet.getBoolean("boolean_value"));
            booleanValue2.set(record, resultSet.getBoolean("boolean_value2"));
            stringValue.set(record, resultSet.getString("string_value"));
            String enum_valueResultValue = resultSet.getString("enum_value");
            AttributeTestEntity.Hoge enum_valueResultValue2 = enum_valueResultValue == null ? null : AttributeTestEntity.Hoge.valueOf(resultSet.getString("enum_value"));
            hogeValue.set(record, enum_valueResultValue2);
            localDateTimeValue.set(record, Optional.ofNullable(resultSet.getTimestamp("local_date_time_value")).map(t -> t.toLocalDateTime()).orElse(null));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("attribute_test_entity", MAPPER)._integer("int_value")._integer("int_value2")._long("long_value")._long("long_value2")._decimal("big_decimal_value")._boolean("boolean_value")._boolean("boolean_value2")._string("string_value")._string("enum_value")._datetime("local_date_time_value").build();

    public static final IntAttribute<AttributeTestEntity> INT_VALUE = new IntAttribute<>(TABLE.intCol("int_value"), AttributeTestEntity::getIntValue);

    public static final IntAttribute<AttributeTestEntity> INT_VALUE2 = new IntAttribute<>(TABLE.intCol("int_value2"), AttributeTestEntity::getIntValue2);

    public static final LongAttribute<AttributeTestEntity> LONG_VALUE = new LongAttribute<>(TABLE.longCol("long_value"), AttributeTestEntity::getLongValue);

    public static final LongAttribute<AttributeTestEntity> LONG_VALUE2 = new LongAttribute<>(TABLE.longCol("long_value2"), AttributeTestEntity::getLongValue2);

    public static final BigDecimalAttribute<AttributeTestEntity> BIG_DECIMAL_VALUE = new BigDecimalAttribute<>(TABLE.decimalCol("big_decimal_value"), AttributeTestEntity::getBigDecimalValue);

    public static final BooleanAttribute<AttributeTestEntity> BOOLEAN_VALUE = new BooleanAttribute<>(TABLE.booleanCol("boolean_value"), AttributeTestEntity::isBooleanValue);

    public static final BooleanAttribute<AttributeTestEntity> BOOLEAN_VALUE2 = new BooleanAttribute<>(TABLE.booleanCol("boolean_value2"), AttributeTestEntity::getBooleanValue2);

    public static final StringAttribute<AttributeTestEntity> STRING_VALUE = new StringAttribute<>(TABLE.stringCol("string_value"), AttributeTestEntity::getStringValue);

    public static final EnumAttribute<AttributeTestEntity, AttributeTestEntity.Hoge> HOGE_VALUE = new EnumAttribute<>(TABLE.stringCol("enum_value"), AttributeTestEntity::getHogeValue);

    public static final LocalDateTimeAttribute<AttributeTestEntity> LOCAL_DATE_TIME_VALUE = new LocalDateTimeAttribute<>(TABLE.timeCol("local_date_time_value"), AttributeTestEntity::getLocalDateTimeValue);

    public static void create(@Nonnull AttributeTestEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(INT_VALUE2.ast(), LONG_VALUE2.ast(), BIG_DECIMAL_VALUE.ast(), BOOLEAN_VALUE.ast(), BOOLEAN_VALUE2.ast(), STRING_VALUE.ast(), HOGE_VALUE.ast(), LOCAL_DATE_TIME_VALUE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(INT_VALUE2, entity), TableClassHelper.createBindParam(LONG_VALUE2, entity), TableClassHelper.createBindParam(BIG_DECIMAL_VALUE, entity), TableClassHelper.createBindParam(BOOLEAN_VALUE, entity), TableClassHelper.createBindParam(BOOLEAN_VALUE2, entity), TableClassHelper.createBindParam(STRING_VALUE, entity), TableClassHelper.createBindParam(HOGE_VALUE, entity, o -> ((AttributeTestEntity.Hoge)o).name()), new SqlBindParam(Timestamp.valueOf((LocalDateTime)LOCAL_DATE_TIME_VALUE.getAccessor().get(entity)), LOCAL_DATE_TIME_VALUE.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor());
    }

    @Nonnull
    public static AttributeTestEntityList all() {
        return new AttributeTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> first(@Nonnull Relation<AttributeTestEntity>... relations) {
        return all().limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity firstOrNull(@Nonnull Relation<AttributeTestEntity>... relations) {
        return first(relations).orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue(@Nonnull int key) {
        return all().where(INT_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue(@Nonnull int key,
                                                               @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(INT_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue(@Nonnull int key) {
        return all().where(INT_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue(@Nonnull int key,
                                                           @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(INT_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue2(@Nonnull Integer key) {
        return all().where(INT_VALUE2.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue2(@Nonnull Integer key,
                                                                @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(INT_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue2(@Nonnull Integer key) {
        return all().where(INT_VALUE2.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue2(@Nonnull Integer key,
                                                            @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(INT_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByLongValue(@Nonnull long key) {
        return all().where(LONG_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByLongValue(@Nonnull long key,
                                                                @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(LONG_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByLongValue(@Nonnull long key) {
        return all().where(LONG_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByLongValue(@Nonnull long key,
                                                            @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(LONG_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByLongValue2(@Nonnull Long key) {
        return all().where(LONG_VALUE2.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByLongValue2(@Nonnull Long key,
                                                                 @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(LONG_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByLongValue2(@Nonnull Long key) {
        return all().where(LONG_VALUE2.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByLongValue2(@Nonnull Long key,
                                                             @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(LONG_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBigDecimalValue(@Nonnull BigDecimal key) {
        return all().where(BIG_DECIMAL_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBigDecimalValue(@Nonnull BigDecimal key,
                                                                      @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BIG_DECIMAL_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBigDecimalValue(@Nonnull BigDecimal key) {
        return all().where(BIG_DECIMAL_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBigDecimalValue(@Nonnull BigDecimal key,
                                                                  @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BIG_DECIMAL_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue(@Nonnull boolean key) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue(@Nonnull boolean key,
                                                                   @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue(@Nonnull boolean key) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue(@Nonnull boolean key,
                                                               @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue2(@Nonnull Boolean key) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue2(@Nonnull Boolean key,
                                                                    @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue2(@Nonnull Boolean key) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue2(@Nonnull Boolean key,
                                                                @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByStringValue(@Nonnull String key) {
        return all().where(STRING_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByStringValue(@Nonnull String key,
                                                                  @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(STRING_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByStringValue(@Nonnull String key) {
        return all().where(STRING_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByStringValue(@Nonnull String key,
                                                              @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(STRING_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByHogeValue(@Nonnull AttributeTestEntity.Hoge key) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByHogeValue(@Nonnull AttributeTestEntity.Hoge key,
                                                                @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByHogeValue(@Nonnull AttributeTestEntity.Hoge key) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nullable
    public static AttributeTestEntity findOrNullByHogeValue(@Nonnull AttributeTestEntity.Hoge key,
                                                            @Nonnull Relation<AttributeTestEntity>... relations) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).preload(relations).toList().stream().findFirst().orElse(null);
    }
}