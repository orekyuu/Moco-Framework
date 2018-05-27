import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class EnumEntityList extends EntityList<EnumEntityList, EnumEntity> {
    EnumEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<EnumEntity> getMapper() {
        return EnumEntities.MAPPER;
    }

    @Nonnull
    @Override
    protected final EnumEntityList thisInstance() {
        return this;
    }
}
