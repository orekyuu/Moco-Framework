import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;

public final class ColumnVariableNameTestEntityList extends EntityList<ColumnVariableNameTestEntityList, ColumnVariableNameTestEntity> {
    ColumnVariableNameTestEntityList(Table table) {
        super(table);
    }

    @Nonnull
    @Override
    public Select.QueryResultMapper<ColumnVariableNameTestEntity> getMapper() {
        return ColumnVariableNameTestEntities.MAPPER;
    }
}
