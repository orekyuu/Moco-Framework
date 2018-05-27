import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class AttributeTestEntityList extends EntityList<AttributeTestEntityList, AttributeTestEntity> {
    AttributeTestEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<AttributeTestEntity> getMapper() {
        return AttributeTestEntities.MAPPER;
    }

    @Nonnull
    @Override
    protected final AttributeTestEntityList thisInstance() {
        return this;
    }
}
