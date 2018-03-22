import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public final class ColumnVariableNameTestEntityList extends EntityList<ColumnVariableNameTestEntityList, ColumnVariableNameTestEntity> {
    ColumnVariableNameTestEntityList(Select select) {
        super(select);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<ColumnVariableNameTestEntity> getMapper() {
        return ColumnVariableNameTestEntities.MAPPER;
    }
}
