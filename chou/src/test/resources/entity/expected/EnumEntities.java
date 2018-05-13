
import java.lang.Enum;
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
import net.orekyuu.moco.core.attribute.EnumAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class EnumEntities {
    public static final Select.QueryResultMapper<EnumEntity> MAPPER = new Select.QueryResultMapper<EnumEntity>() {
        private Field id;

        private Field locale;

        {
            try {
                id = EnumEntity.class.getDeclaredField("id");
                id.setAccessible(true);
                locale = EnumEntity.class.getDeclaredField("locale");
                locale.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public EnumEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            EnumEntity record = new EnumEntity();
            id.set(record, resultSet.getInt("id"));
            locale.set(record, Enum.valueOf(EnumEntity.Locale.class, resultSet.getString("locale")));
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("enum_entities", MAPPER)._integer("id")._string("locale").build();

    public static final IntAttribute<EnumEntity> ID = new IntAttribute<>(TABLE.intCol("id"), EnumEntity::getId);

    public static final EnumAttribute<EnumEntity, EnumEntity.Locale> LOCALE = new EnumAttribute<>(TABLE.stringCol("locale"), EnumEntity::getLocale);

    public static void create(@Nonnull EnumEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(ID.ast(), LOCALE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(ID.getAccessor().get(entity), ID.bindType()), new SqlBindParam(((Enum)(LOCALE.getAccessor().get(entity))).name(), LOCALE.bindType()))));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    @Nonnull
    public static EnumEntityList all() {
        return new EnumEntityList(TABLE);
    }

    @Nonnull
    public static Optional<EnumEntity> first() {
        return all().limit(1).toList().stream().findFirst();
    }

    @Nullable
    public static EnumEntity firstOrNull() {
        return first().orElse(null);
    }
}