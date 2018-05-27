import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class UniqueTestEntityList extends EntityList<UniqueTestEntityList, UniqueTestEntity> {
    UniqueTestEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<UniqueTestEntity> getMapper() {
        return UniqueTestEntities.MAPPER;
    }

    @Nonnull
    @Override
    protected final UniqueTestEntityList thisInstance() {
        return this;
    }
}
