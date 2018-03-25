package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlOrderigTerm implements SqlNode {

    public enum OrderType {
        ASC, DESC
    }

    private final Attribute attribute;
    private final OrderType orderType;

    public SqlOrderigTerm(Attribute attribute, OrderType orderType) {
        this.attribute = attribute;
        this.orderType = orderType;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public OrderType getOrderType() {
        return orderType;
    }
}