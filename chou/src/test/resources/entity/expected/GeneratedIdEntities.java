
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
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class GeneratedIdEntities {
    public static final Select.QueryResultMapper<GeneratedIdEntity> MAPPER = new Select.QueryResultMapper<GeneratedIdEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = TableClassHelper.getDeclaredField(GeneratedIdEntity.class, "id");
                text = TableClassHelper.getDeclaredField(GeneratedIdEntity.class, "text");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public GeneratedIdEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            GeneratedIdEntity record = new GeneratedIdEntity();
            id.set(record, resultSet.getInt("id"));
            text.set(record, resultSet.getString("text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("generated_id_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<GeneratedIdEntity> ID = new IntAttribute<>(TABLE.intCol("id"), GeneratedIdEntity::getId);

    public static final StringAttribute<GeneratedIdEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), GeneratedIdEntity::getText);

    public static void create(@Nonnull GeneratedIdEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(TEXT, entity))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static GeneratedIdEntityList all() {
        return new GeneratedIdEntityList(TABLE);
    }

    @Nonnull
    public static Optional<GeneratedIdEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static GeneratedIdEntity firstOrNull() {
        return first().orElse(null);
    }
}
