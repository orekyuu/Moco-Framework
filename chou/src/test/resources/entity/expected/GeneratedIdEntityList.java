import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public final class GeneratedIdEntityList extends EntityList<GeneratedIdEntityList, GeneratedIdEntity> {
    GeneratedIdEntityList(Select select) {
        super(select);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<GeneratedIdEntity> getMapper() {
        return GeneratedIdEntities.MAPPER;
    }
}