import java.lang.Boolean;
import java.lang.Enum;
import java.lang.Integer;
import java.lang.Override;
import java.lang.ReflectiveOperationException;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.EnumAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
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

        private Field booleanValue;

        private Field booleanValue2;

        private Field stringValue;

        private Field hogeValue;

        {
            try {
                intValue = AttributeTestEntity.class.getDeclaredField("intValue");
                intValue.setAccessible(true);
                intValue2 = AttributeTestEntity.class.getDeclaredField("intValue2");
                intValue2.setAccessible(true);
                booleanValue = AttributeTestEntity.class.getDeclaredField("booleanValue");
                booleanValue.setAccessible(true);
                booleanValue2 = AttributeTestEntity.class.getDeclaredField("booleanValue2");
                booleanValue2.setAccessible(true);
                stringValue = AttributeTestEntity.class.getDeclaredField("stringValue");
                stringValue.setAccessible(true);
                hogeValue = AttributeTestEntity.class.getDeclaredField("hogeValue");
                hogeValue.setAccessible(true);
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
            booleanValue.set(record, resultSet.getBoolean("boolean_value"));
            booleanValue2.set(record, resultSet.getBoolean("boolean_value2"));
            stringValue.set(record, resultSet.getString("string_value"));
            hogeValue.set(record, Enum.valueOf(AttributeTestEntity.Hoge.class, resultSet.getString("enum_value")));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("attribute_test_entity", MAPPER)._integer("int_value")._integer("int_value2")._boolean("boolean_value")._boolean("boolean_value2")._string("string_value")._string("enum_value").build();

    public static final IntAttribute<AttributeTestEntity> INT_VALUE = new IntAttribute<>(TABLE.intCol("int_value"), AttributeTestEntity::getIntValue);

    public static final IntAttribute<AttributeTestEntity> INT_VALUE2 = new IntAttribute<>(TABLE.intCol("int_value2"), AttributeTestEntity::getIntValue2);

    public static final BooleanAttribute<AttributeTestEntity> BOOLEAN_VALUE = new BooleanAttribute<>(TABLE.booleanCol("boolean_value"), AttributeTestEntity::isBooleanValue);

    public static final BooleanAttribute<AttributeTestEntity> BOOLEAN_VALUE2 = new BooleanAttribute<>(TABLE.booleanCol("boolean_value2"), AttributeTestEntity::getBooleanValue2);

    public static final StringAttribute<AttributeTestEntity> STRING_VALUE = new StringAttribute<>(TABLE.stringCol("string_value"), AttributeTestEntity::getStringValue);

    public static final EnumAttribute<AttributeTestEntity, AttributeTestEntity.Hoge> HOGE_VALUE = new EnumAttribute<>(TABLE.stringCol("enum_value"), AttributeTestEntity::getHogeValue);

    public static void create(@Nonnull AttributeTestEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(INT_VALUE2.ast(), BOOLEAN_VALUE.ast(), BOOLEAN_VALUE2.ast(), STRING_VALUE.ast(), HOGE_VALUE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(INT_VALUE2.getAccessor().get(entity), INT_VALUE2.bindType()), new SqlBindParam(BOOLEAN_VALUE.getAccessor().get(entity), BOOLEAN_VALUE.bindType()), new SqlBindParam(BOOLEAN_VALUE2.getAccessor().get(entity), BOOLEAN_VALUE2.bindType()), new SqlBindParam(STRING_VALUE.getAccessor().get(entity), STRING_VALUE.bindType()), new SqlBindParam(((Enum)(HOGE_VALUE.getAccessor().get(entity))).name(), HOGE_VALUE.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static AttributeTestEntityList all() {
        return new AttributeTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue(@Nonnull int key) {
        return all().where(INT_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue(@Nonnull int key) {
        return all().where(INT_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByIntValue2(@Nonnull Integer key) {
        return all().where(INT_VALUE2.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByIntValue2(@Nonnull Integer key) {
        return all().where(INT_VALUE2.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue(@Nonnull boolean key) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue(@Nonnull boolean key) {
        return all().where(BOOLEAN_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByBooleanValue2(@Nonnull Boolean key) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByBooleanValue2(@Nonnull Boolean key) {
        return all().where(BOOLEAN_VALUE2.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByStringValue(@Nonnull String key) {
        return all().where(STRING_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByStringValue(@Nonnull String key) {
        return all().where(STRING_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<AttributeTestEntity> findByHogeValue(@Nonnull AttributeTestEntity.Hoge key) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static AttributeTestEntity findOrNullByHogeValue(@Nonnull AttributeTestEntity.Hoge key) {
        return all().where(HOGE_VALUE.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }
}