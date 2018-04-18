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
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.exposer.Converter;
import net.orekyuu.moco.feeling.exposer.DatabaseColumnType;
import net.orekyuu.moco.feeling.exposer.Exposer;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class ColumnVariableNameTestEntities {
    public static final Select.QueryResultMapper<ColumnVariableNameTestEntity> MAPPER = new Select.QueryResultMapper<ColumnVariableNameTestEntity>() {
        private Field intCol1;

        private Field intCol2;

        private Field text1;

        private Field text2;

        {
            try {
                intCol1 = ColumnVariableNameTestEntity.class.getDeclaredField("intCol1");
                intCol1.setAccessible(true);
                intCol2 = ColumnVariableNameTestEntity.class.getDeclaredField("intCol2");
                intCol2.setAccessible(true);
                text1 = ColumnVariableNameTestEntity.class.getDeclaredField("text1");
                text1.setAccessible(true);
                text2 = ColumnVariableNameTestEntity.class.getDeclaredField("text2");
                text2.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public ColumnVariableNameTestEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            ColumnVariableNameTestEntity record = new ColumnVariableNameTestEntity();
            intCol1.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "id1"));
            intCol2.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "id2"));
            text1.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "text1"));
            text2.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "text2"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("column_variable_name_test_entity", MAPPER)._integer("id1")._integer("id2")._string("text1")._string("text2").build();

    public static final IntAttribute<ColumnVariableNameTestEntity> ID_TEST = new IntAttribute<>(TABLE.intCol("id1"), ColumnVariableNameTestEntity::getIntCol1);

    public static final IntAttribute<ColumnVariableNameTestEntity> ID_TEST2 = new IntAttribute<>(TABLE.intCol("id2"), ColumnVariableNameTestEntity::getIntCol2);

    public static final StringAttribute<ColumnVariableNameTestEntity> TEXT_TEST1 = new StringAttribute<>(TABLE.stringCol("text1"), ColumnVariableNameTestEntity::getText1);

    public static final StringAttribute<ColumnVariableNameTestEntity> TEXT_TEST2 = new StringAttribute<>(TABLE.stringCol("text2"), ColumnVariableNameTestEntity::getText2);

    public static void create(@Nonnull ColumnVariableNameTestEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(TEXT_TEST1.ast(), TEXT_TEST2.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(TEXT_TEST1.getAccessor().get(entity), TEXT_TEST1.bindType()), new SqlBindParam(TEXT_TEST2.getAccessor().get(entity), TEXT_TEST2.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static ColumnVariableNameTestEntityList all() {
        return new ColumnVariableNameTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static ColumnVariableNameTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByIntCol1(@Nonnull int key) {
        return all().where(ID_TEST.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByIntCol1(@Nonnull int key) {
        return all().where(ID_TEST.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByText1(@Nonnull String key) {
        return all().where(TEXT_TEST1.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByText1(@Nonnull String key) {
        return all().where(TEXT_TEST1.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }
}
