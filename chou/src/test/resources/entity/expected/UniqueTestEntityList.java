import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public final class UniqueTestEntityList extends EntityList<UniqueTestEntityList, UniqueTestEntity> {
    UniqueTestEntityList(Select select) {
        super(select);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<UniqueTestEntity> getMapper() {
        return UniqueTestEntities.MAPPER;
    }
}
