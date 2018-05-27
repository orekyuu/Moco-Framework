import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class SimpleEntityList extends EntityList<SimpleEntityList, SimpleEntity> {
    SimpleEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<SimpleEntity> getMapper() {
        return SimpleEntities.MAPPER;
    }

    @Nonnull
    @Override
    protected final SimpleEntityList thisInstance() {
        return this;
    }
}
