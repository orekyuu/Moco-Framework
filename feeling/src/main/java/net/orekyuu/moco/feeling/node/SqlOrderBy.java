package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlOrderBy extends SqlUnary {
    private final SqlNodeArray orderigTerms;
    public SqlOrderBy(SqlNodeArray orderigTerms) {
        super(orderigTerms);
        this.orderigTerms = orderigTerms;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
