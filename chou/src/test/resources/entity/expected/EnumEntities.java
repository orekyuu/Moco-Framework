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
import net.orekyuu.moco.core.attribute.EnumAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.internal.TableClassHelper;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

public final class EnumEntities {
    public static final Select.QueryResultMapper<EnumEntity> MAPPER = new Select.QueryResultMapper<EnumEntity>() {
        private Field id;

        private Field locale;

        {
            try {
                id = TableClassHelper.getDeclaredField(EnumEntity.class, "id");
                locale = TableClassHelper.getDeclaredField(EnumEntity.class, "locale");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public EnumEntity mapping(ResultSet resultSet) throws SQLException,
                ReflectiveOperationException {
            EnumEntity record = new EnumEntity();
            id.set(record, resultSet.getInt("id"));
            String localeResultValue = resultSet.getString("locale");
            EnumEntity.Locale localeResultValue2 = localeResultValue == null ? null : EnumEntity.Locale.valueOf(resultSet.getString("locale"));
            locale.set(record, localeResultValue2);
            return record;
        }
    };

    public static final Table TABLE = new TableBuilder("enum_entities", MAPPER)._integer("id")._string("locale").build();

    public static final IntAttribute<EnumEntity> ID = new IntAttribute<>(TABLE.intCol("id"), EnumEntity::getId);

    public static final EnumAttribute<EnumEntity, EnumEntity.Locale> LOCALE = new EnumAttribute<>(TABLE.stringCol("locale"), EnumEntity::getLocale);

    public static void create(@Nonnull EnumEntity entity) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(ID.ast(), LOCALE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(TableClassHelper.createBindParam(ID, entity), TableClassHelper.createBindParam(LOCALE, entity, o -> ((EnumEntity.Locale)o).name()))));
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

    @Nonnull
    public static Optional<EnumEntity> first(@Nonnull Relation<EnumEntity>... relations) {
        return all().limit(1).preload(relations).toList().stream().findFirst();
    }

    @Nullable
    public static EnumEntity firstOrNull() {
        return first().orElse(null);
    }

    @Nullable
    public static EnumEntity firstOrNull(@Nonnull Relation<EnumEntity>... relations) {
        return first(relations).orElse(null);
    }
}
