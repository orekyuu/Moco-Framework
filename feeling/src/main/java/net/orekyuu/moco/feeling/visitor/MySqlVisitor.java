package net.orekyuu.moco.feeling.visitor;

import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.attributes.*;
import net.orekyuu.moco.feeling.node.*;

import java.util.Iterator;

public class MySqlVisitor extends SqlVisitor {
    @Override
    public void visit(BooleanAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
    }

    @Override
    public void visit(DecimalAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
    }

    @Override
    public void visit(FloatAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
    }

    @Override
    public void visit(IntAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
    }

    @Override
    public void visit(StringAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
    }

    @Override
    public void visit(TimeAttribute node, SqlContext context) {
        context.append(escape(node.getRelation())).append(".").append(escape(node.getName()));
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
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlJoinClause node, SqlContext context) {
        node.getSingleSource().accept(this, context);
        for (SqlJoin join : node.getJoins()) {
            join.accept(this, context);
        }
    }

    @Override
    public void visit(SqlJoin node, SqlContext context) {
        context.append("join ");
        node.table().accept(this, context);
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlInnerJoin node, SqlContext context) {
        context.append("inner join ");
        node.table().accept(this, context);
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlOuterJoin node, SqlContext context) {
        context.append("outer join ");
        node.table().accept(this, context);
        node.expression().accept(this, context);
    }

    @Override
    public void visit(SqlNodeArray node, SqlContext context) {
        Iterator<SqlNode> iterator = node.getNodes().iterator();
        while (iterator.hasNext()) {
            SqlNode sqlNode = iterator.next();
            sqlNode.accept(this, context);

            if (iterator.hasNext()) {
                context.append(",");
            }
        }
    }

    @Override
    public void visit(Select select, SqlContext context) {
        context.append("select ");

        //TODO
        context.append("* ");

        select.getFromClause().accept(this, context);
        WhereClause whereClause = select.getWhereClause();
        if (whereClause != null) {
            whereClause.accept(this, context);
        }
    }
}
