package net.orekyuu.moco.core.relation;

import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.ReflectUtil;
import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.core.attribute.AttributeValueAccessor;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlIn;
import net.orekyuu.moco.feeling.node.SqlNodeArray;
import net.orekyuu.moco.feeling.node.WhereClause;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HasManyRelation<OWNER, CHILD> extends Relation<OWNER> {
    private final Select.QueryResultMapper<CHILD> mapper;
    private final ReflectUtil.FieldSetter setter;

    public HasManyRelation(Table owner, Attribute ownerKeyAttribute, Table child, Attribute childKeyAttribute, Select.QueryResultMapper<CHILD> mapper, ReflectUtil.FieldSetter setter) {
        super(owner, ownerKeyAttribute, child, childKeyAttribute);
        this.mapper = mapper;
        this.setter = setter;
    }

    @Override
    public void preload(List<OWNER> records) {
        AttributeValueAccessor accessor = ownerKeyAttribute.getAccessor();

        Set<SqlBindParam> params = records.stream()
                .map((Function<OWNER, Object>) accessor::get)
                .distinct().map(Object::toString)
                .map(it -> new SqlBindParam(it, it.getClass()))
                .collect(Collectors.toSet());

        if (params.isEmpty()) {
            return;
        }

        Select select = child.select();
        select.where(new WhereClause(new SqlIn(childKeyAttribute.ast(), new SqlNodeArray(params))));
        List<CHILD> children = select.executeQuery(ConnectionManager.getConnection(), mapper);

        Map<Object, List<CHILD>> groupedRecords = children.stream()
                .collect(Collectors.groupingBy((Function<Object, Object>) childKeyAttribute.getAccessor()::get));

        for (OWNER record : records) {
            List<CHILD> childList = groupedRecords.getOrDefault(accessor.get(record), new ArrayList<>());
            try {
                setter.set(record, childList);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
