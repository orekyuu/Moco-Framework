import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class GeneratedIdEntityList extends EntityList<GeneratedIdEntityList, GeneratedIdEntity> {
    GeneratedIdEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<GeneratedIdEntity> getMapper() {
        return GeneratedIdEntities.MAPPER;
    }

    @Nonnull
    @Override
    protected final GeneratedIdEntityList thisInstance() {
        return this;
    }
}
