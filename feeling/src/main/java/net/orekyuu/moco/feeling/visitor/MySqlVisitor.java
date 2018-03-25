package net.orekyuu.moco.feeling.visitor;

import net.orekyuu.moco.feeling.Delete;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.attributes.*;
import net.orekyuu.moco.feeling.node.*;

import java.util.Iterator;

public class MySqlVisitor extends SqlVisitor {
    @Override
    public void visit(BooleanAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(DecimalAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(FloatAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(IntAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(StringAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(TimeAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName())).append(" ");
    }

    @Override
    public void visit(FromClause node, SqlContext context) {
        context.append("from ");
        node.getSqlJoinClause().accept(this, context);
    }

    @Override
    public void visit(WhereClause node, SqlContext context) {
        context.append("where ");
        node.getExpression().accept(this, context);
    }

    @Override
    public void visit(SqlLiteral node, SqlContext context) {
        context.append(node.getText()).append(" ");
    }

    @Override
    public void visit(SqlBindParam node, SqlContext context) {
        context.appendParams(node).append(" ");
    }

    @Override
    public void visit(SqlAnd node, SqlContext context) {
        node.left().accept(this, context);
        context.append("and ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlOr node, SqlContext context) {
        node.right().accept(this, context);
        context.append("or ");
        node.left().accept(this, context);
    }

    @Override
    public void visit(SqlEq node, SqlContext context) {
        node.left().accept(this, context);
        context.append("= ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlNoteq node, SqlContext context) {
        node.left().accept(this, context);
        context.append("!= ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlGt node, SqlContext context) {
        node.left().accept(this, context);
        context.append("< ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlGteq node, SqlContext context) {
        node.left().accept(this, context);
        context.append("<= ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlLt node, SqlContext context) {
        node.left().accept(this, context);
        context.append("> ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlLteq node, SqlContext context) {
        node.left().accept(this, context);
        context.append(">= ");
        node.right().accept(this, context);
    }

    @Override
    public void visit(SqlIn node, SqlContext context) {
        node.left().accept(this, context);
        context.append("in ");
        context.append("(");
        node.right().accept(this, context);
        context.append(")");
    }

    @Override
    public void visit(SqlOn node, SqlContext context) {
        context.append("on ");
        node.node().accept(this, context);
    }

    @Override
    public void visit(SqlJoinClause node, SqlContext context) {
        node.getSingleSourceNode().accept(this, context);
        for (SqlJoin join : node.getJoins()) {
            join.accept(this, context);
        }
    }

    @Override
    public void visit(SqlJoin node, SqlContext context) {
        context.append("join ");
        context.append("`");
        context.append(node.tableLiteral().getText());
        context.append("` ");
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlInnerJoin node, SqlContext context) {
        context.append("inner join ");
        node.tableLiteral().accept(this, context);
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlOuterJoin node, SqlContext context) {
        context.append("outer join ");
        node.tableLiteral().accept(this, context);
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlLimit node, SqlContext context) {
        context.append("limit ");
        node.node().accept(this, context);
    }

    @Override
    public void visit(SqlOffset node, SqlContext context) {
        context.append("offset ");
        node.node().accept(this, context);
    }

    @Override
    public void visit(SqlOrderBy sqlOrderBy, SqlContext context) {
        context.append("order by ");
        sqlOrderBy.node().accept(this, context);
    }

    @Override
    public void visit(SqlOrderigTerm sqlOrderigTerm, SqlContext context) {
        sqlOrderigTerm.getAttribute().accept(this, context);
        switch (sqlOrderigTerm.getOrderType()) {
            case ASC: context.append("asc "); break;
            case DESC: context.append("desc "); break;
            default: throw new IllegalArgumentException(sqlOrderigTerm.getOrderType().name());
        }
    }

    @Override
    public void visit(SqlNodeArray node, SqlContext context) {
        Iterator<SqlNode> iterator = node.getNodes().iterator();
        while (iterator.hasNext()) {
            SqlNode sqlNode = iterator.next();
            sqlNode.accept(this, context);

            if (iterator.hasNext()) {
                context.append(", ");
            }
        }
    }

    @Override
    public void visit(Select select, SqlContext context) {
        context.append("select ");

        Iterator<Attribute> resultColumns = select.getResultColumn().iterator();
        appendAttributArray(context, resultColumns);

        select.getFromClause().accept(this, context);

        select.getWhereClause().ifPresent(whereClause -> whereClause.accept(this, context));
        select.getOrderBy().ifPresent(orderBy -> orderBy.accept(this, context));
        select.getLimit().ifPresent(sqlLimit -> sqlLimit.accept(this, context));
        select.getOffset().ifPresent(sqlOffset -> sqlOffset.accept(this, context));
    }

    @Override
    public void visit(Insert insert, SqlContext context) {
        context.append("insert into ");
        context.append(insert.getTable().getTableName());
        context.append("(");
        Iterator<Attribute> attributeIterator = insert.getAttributes().iterator();
        appendAttributArray(context, attributeIterator);
        context.append(") ");
        context.append("values ");

        context.append("(");
        insert.getValues().accept(this, context);
        context.append(")");
    }

    @Override
    public void visit(Delete delete, SqlContext context) {
        context.append("delete ");
        delete.getFromClause().accept(this, context);
        if (delete.getWhereClause() != null) {
            delete.getWhereClause().accept(this, context);
        }
        delete.getOrderBy().ifPresent(orderBy -> orderBy.accept(this, context));
        if (delete.getLimit() != null) {
            delete.getLimit().accept(this, context);
        }
    }

    private void appendAttributArray(SqlContext context, Iterator<Attribute> attributeIterator) {
        while (attributeIterator.hasNext()) {
            Attribute attribute = attributeIterator.next();
            attribute.accept(this, context);

            if (attributeIterator.hasNext()) {
                context.append(", ");
            }
        }
    }
}
