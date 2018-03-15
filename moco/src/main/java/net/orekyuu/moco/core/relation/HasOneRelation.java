package net.orekyuu.moco.core.relation;

import net.orekyuu.moco.core.ConnectionManager;
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

public class HasOneRelation<OWNER, CHILD> extends Relation<OWNER> {
    private final Select.QueryResultMapper<CHILD> mapper;

    public HasOneRelation(Table owner, Attribute ownerKeyAttribute, Table child, Attribute childKeyAttribute, Select.QueryResultMapper<CHILD> mapper) {
        super(owner, ownerKeyAttribute, child, childKeyAttribute);
        this.mapper = mapper;
    }

@Override
public void preload(List<OWNER> records) {
    AttributeValueAccessor accessor = ownerKeyAttribute.getAccessor();

    Set<SqlBindParam> params = records.stream()
            .map((Function<OWNER, Object>) accessor::get)
            .distinct().map(Object::toString)
            .map(it -> new SqlBindParam(it, it.getClass()))
            .collect(Collectors.toSet());

    Select select = child.select();
    select.where(new WhereClause(new SqlIn(childKeyAttribute.ast(), new SqlNodeArray(params))));
    List<CHILD> children = select.executeQuery(ConnectionManager.getConnection(), mapper);

    Map<Object, List<CHILD>> groupedRecords = children.stream()
            .collect(Collectors.groupingBy((Function<Object, Object>) childKeyAttribute.getAccessor()::get));

    for (OWNER record : records) {
        List<CHILD> childList = groupedRecords.getOrDefault(accessor.get(record), new ArrayList<>());
        CHILD child = childList.isEmpty() ? null : childList.get(0);
        System.out.println("parent: " + record);
        System.out.println("  child: " + child);
        System.out.println();
    }
}
}
