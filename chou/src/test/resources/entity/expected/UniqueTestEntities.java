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

public final class UniqueTestEntities {
    public static final Select.QueryResultMapper<UniqueTestEntity> MAPPER = new Select.QueryResultMapper<UniqueTestEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = UniqueTestEntity.class.getDeclaredField("id");
                id.setAccessible(true);
                text = UniqueTestEntity.class.getDeclaredField("text");
                text.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public UniqueTestEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            UniqueTestEntity record = new UniqueTestEntity();
            id.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "id"));
            text.set(record, new Exposer<>(DatabaseColumnType.STRING, Converter.raw()).expose(resultSet, "text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("unique_test_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<UniqueTestEntity> ID = new IntAttribute<>(TABLE.intCol("id"), UniqueTestEntity::getId);

    public static final StringAttribute<UniqueTestEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), UniqueTestEntity::getText);

    public static void create(@Nonnull UniqueTestEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(TEXT.getAccessor().get(entity), TEXT.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static UniqueTestEntityList all() {
        return new UniqueTestEntityList(TABLE);
    }

    @Nonnull
    public static Optional<UniqueTestEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static UniqueTestEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findById(@Nonnull int key) {
        return all().where(ID.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static UniqueTestEntity findOrNullById(@Nonnull int key) {
        return all().where(ID.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }

    @Nonnull
    public static Optional<UniqueTestEntity> findByText(@Nonnull String key) {
        return all().where(TEXT.eq(key)).limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static UniqueTestEntity findOrNullByText(@Nonnull String key) {
        return all().where(TEXT.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
    }
}
