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

public final class UniqueTestEntities {
    public static final Select.QueryResultMapper<UniqueTestEntity> MAPPER = new Select.QueryResultMapper<UniqueTestEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = TableClassHelper.getDeclaredField(UniqueTestEntity.class, "id");
                text = TableClassHelper.getDeclaredField(UniqueTestEntity.class, "text");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public UniqueTestEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            UniqueTestEntity record = new UniqueTestEntity();
            id.set(record, resultSet.getInt("id"));
            text.set(record, resultSet.getString("text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("unique_test_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<UniqueTestEntity> ID = new IntAttribute<>(TABLE.intCol("id"), UniqueTestEntity::getId);

    public static final StringAttribute<UniqueTestEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), UniqueTestEntity::getText);

    public static void create(@Nonnull UniqueTestEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(TEXT, entity))));
        insert.executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor());
    }

    @Nonnull
    public static UniqueTestEntityList all() {
        return new UniqueTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<UniqueTestEntity> first() {
        return all().first();
    }

    @Nonnull
    public static Optional<UniqueTestEntity> first(@Nonnull Relation<UniqueTestEntity>... relations) {
        return all().preload(relations).first();
    }

    @Nullable
    public static UniqueTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nullable
    public static UniqueTestEntity firstOrNull(@Nonnull Relation<UniqueTestEntity>... relations) {
        return first(relations).orElse(null);
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findById(@Nonnull int key) {
        return all().where(ID.eq(key)).first();
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findById(@Nonnull int key,
                                                      @Nonnull Relation<UniqueTestEntity>... relations) {
        return all().where(ID.eq(key)).preload(relations).first();
    }

    @Nullable
    public static UniqueTestEntity findOrNullById(@Nonnull int key) {
        return all().where(ID.eq(key)).firstOrNull();
    }

    @Nullable
    public static UniqueTestEntity findOrNullById(@Nonnull int key,
                                                  @Nonnull Relation<UniqueTestEntity>... relations) {
        return all().where(ID.eq(key)).preload(relations).firstOrNull();
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findByText(@Nonnull String key) {
        return all().where(TEXT.eq(key)).first();
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findByText(@Nonnull String key,
                                                        @Nonnull Relation<UniqueTestEntity>... relations) {
        return all().where(TEXT.eq(key)).preload(relations).first();
    }

    @Nullable
    public static UniqueTestEntity findOrNullByText(@Nonnull String key) {
        return all().where(TEXT.eq(key)).firstOrNull();
    }

    @Nullable
    public static UniqueTestEntity findOrNullByText(@Nonnull String key,
                                                    @Nonnull Relation<UniqueTestEntity>... relations) {
        return all().where(TEXT.eq(key)).preload(relations).firstOrNull();
    }
}
