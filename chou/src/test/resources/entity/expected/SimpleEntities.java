import java.lang.Override;
import java.lang.ReflectiveOperationException;
import java.lang.RuntimeException;
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

public final class SimpleEntities {
    public static final Select.QueryResultMapper<SimpleEntity> MAPPER = new Select.QueryResultMapper<SimpleEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = TableClassHelper.getDeclaredField(SimpleEntity.class, "id");
                text = TableClassHelper.getDeclaredField(SimpleEntity.class, "text");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public SimpleEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            SimpleEntity record = new SimpleEntity();
            id.set(record, resultSet.getInt("id"));
            text.set(record, resultSet.getString("text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("simple_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<SimpleEntity> ID = new IntAttribute<>(TABLE.intCol("id"), SimpleEntity::getId);

    public static final StringAttribute<SimpleEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), SimpleEntity::getText);

    public static void create(@Nonnull SimpleEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(ID.ast(), TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(ID, entity), TableClassHelper.createBindParam(TEXT, entity))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static SimpleEntityList all() {
        return new SimpleEntityList(TABLE);
    }

    @Nonnull
    public static Optional<SimpleEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nonnull
    public static Optional<SimpleEntity> first(@Nonnull Relation<SimpleEntity>... relations) {
        return all().limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static SimpleEntity firstOrNull() {
        return first().orElse(null);
    }
}
