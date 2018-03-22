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
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class SimpleEntities {
    public static final Select.QueryResultMapper<SimpleEntity> MAPPER = new Select.QueryResultMapper<SimpleEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = SimpleEntity.class.getDeclaredField("id");
                id.setAccessible(true);
                text = SimpleEntity.class.getDeclaredField("text");
                text.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public SimpleEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            SimpleEntity record = new SimpleEntity();
            id.set(record, resultSet.getObject("id"));
            text.set(record, resultSet.getObject("text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("simple_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<SimpleEntity> ID = new IntAttribute<>(TABLE.intCol("id"), SimpleEntity::getId);

    public static final StringAttribute<SimpleEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), SimpleEntity::getText);

    public static void create(@Nonnull SimpleEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(ID.ast(), TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(ID.getAccessor().get(entity), ID.bindType()), new SqlBindParam(TEXT.getAccessor().get(entity), TEXT.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static SimpleEntityList all() {
        return new SimpleEntityList(TABLE.select());
    }

    @Nonnull
    public static Optional<SimpleEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static SimpleEntity firstOrNull() {
        return first().orElse(null);
    }
}