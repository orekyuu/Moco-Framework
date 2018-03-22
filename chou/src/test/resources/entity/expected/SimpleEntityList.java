import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public final class SimpleEntityList extends EntityList<SimpleEntityList, SimpleEntity> {
    SimpleEntityList(Select select) {
        super(select);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<SimpleEntity> getMapper() {
        return SimpleEntities.MAPPER;
    }
}
