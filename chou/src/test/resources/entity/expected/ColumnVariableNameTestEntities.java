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
import net.orekyuu.moco.core.internal.TableClassHelper;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class ColumnVariableNameTestEntities {
    public static final Select.QueryResultMapper<ColumnVariableNameTestEntity> MAPPER = new Select.QueryResultMapper<ColumnVariableNameTestEntity>() {
        private Field intCol1;

        private Field intCol2;

        private Field text1;

        private Field text2;

        {
            try {
                intCol1 = TableClassHelper.getDeclaredField(ColumnVariableNameTestEntity.class, "intCol1");
                intCol2 = TableClassHelper.getDeclaredField(ColumnVariableNameTestEntity.class, "intCol2");
                text1 = TableClassHelper.getDeclaredField(ColumnVariableNameTestEntity.class, "text1");
                text2 = TableClassHelper.getDeclaredField(ColumnVariableNameTestEntity.class, "text2");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public ColumnVariableNameTestEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            ColumnVariableNameTestEntity record = new ColumnVariableNameTestEntity();
            intCol1.set(record, resultSet.getInt("id1"));
            intCol2.set(record, resultSet.getInt("id2"));
            text1.set(record, resultSet.getString("text1"));
            text2.set(record, resultSet.getString("text2"));
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
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(TEXT_TEST1, entity), TableClassHelper.createBindParam(TEXT_TEST2, entity))));
        insert.executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor());
    }

    @Nonnull
    public static ColumnVariableNameTestEntityList all() {
        return new ColumnVariableNameTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> first() {
        return all().first();
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> first(@Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return all().preload(relations).first();
    }

    @Nullable
    public static ColumnVariableNameTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nullable
    public static ColumnVariableNameTestEntity firstOrNull(@Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return first(relations).orElse(null);
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByIntCol1(@Nonnull int key) {
        return all().where(ID_TEST.eq(key)).first();
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByIntCol1(@Nonnull int key,
                                                                       @Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return all().where(ID_TEST.eq(key)).preload(relations).first();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByIntCol1(@Nonnull int key) {
        return all().where(ID_TEST.eq(key)).firstOrNull();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByIntCol1(@Nonnull int key,
                                                                   @Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return all().where(ID_TEST.eq(key)).preload(relations).firstOrNull();
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByText1(@Nonnull String key) {
        return all().where(TEXT_TEST1.eq(key)).first();
    }

    @Nonnull
    public static Optional<ColumnVariableNameTestEntity> findByText1(@Nonnull String key,
                                                                     @Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return all().where(TEXT_TEST1.eq(key)).preload(relations).first();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByText1(@Nonnull String key) {
        return all().where(TEXT_TEST1.eq(key)).firstOrNull();
    }

    @Nullable
    public static ColumnVariableNameTestEntity findOrNullByText1(@Nonnull String key,
                                                                 @Nonnull Relation<ColumnVariableNameTestEntity>... relations) {
        return all().where(TEXT_TEST1.eq(key)).preload(relations).firstOrNull();
    }
}