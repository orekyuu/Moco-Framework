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
import net.orekyuu.moco.feeling.exposer.Converter;
import net.orekyuu.moco.feeling.exposer.DatabaseColumnType;
import net.orekyuu.moco.feeling.exposer.Exposer;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class GeneratedIdEntities {
    public static final Select.QueryResultMapper<GeneratedIdEntity> MAPPER = new Select.QueryResultMapper<GeneratedIdEntity>() {
        private Field id;

        private Field text;

        {
            try {
                id = GeneratedIdEntity.class.getDeclaredField("id");
                id.setAccessible(true);
                text = GeneratedIdEntity.class.getDeclaredField("text");
                text.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public GeneratedIdEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            GeneratedIdEntity record = new GeneratedIdEntity();
            id.set(record, new Exposer<>(DatabaseColumnType.INT, Converter.raw()).expose(resultSet, "id"));
            text.set(record, new Exposer<>(DatabaseColumnType.STRING, Converter.raw()).expose(resultSet, "text"));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("generated_id_entities", MAPPER)._integer("id")._string("text").build();

    public static final IntAttribute<GeneratedIdEntity> ID = new IntAttribute<>(TABLE.intCol("id"), GeneratedIdEntity::getId);

    public static final StringAttribute<GeneratedIdEntity> TEXT = new StringAttribute<>(TABLE.stringCol("text"), GeneratedIdEntity::getText);

    public static void create(@Nonnull GeneratedIdEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(TEXT.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(TEXT.getAccessor().get(entity), TEXT.bindType()))));
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
