package net.orekyuu.moco.feeling.visitor;

import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.attributes.*;
import net.orekyuu.moco.feeling.node.*;

public abstract class SqlVisitor {

    public String escape(String str) {
        return "`" + str + "`";
    }

    public abstract void visit(BooleanAttribute node, SqlContext context);
    public abstract void visit(DecimalAttribute node, SqlContext context);
    public abstract void visit(FloatAttribute node, SqlContext context);
    public abstract void visit(IntAttribute node, SqlContext context);
    public abstract void visit(StringAttribute node, SqlContext context);
    public abstract void visit(TimeAttribute node, SqlContext context);

    public abstract void visit(FromClause node, SqlContext context);
    public abstract void visit(WhereClause node, SqlContext context);

    public abstract void visit(SqlLiteral node, SqlContext context);

    public abstract void visit(SqlBindParam node, SqlContext context);

    public abstract void visit(SqlAnd node, SqlContext context);
    public abstract void visit(SqlOr node, SqlContext context);

    public abstract void visit(SqlEq node, SqlContext context);
    public abstract void visit(SqlNoteq node, SqlContext context);
    public abstract void visit(SqlGt node, SqlContext context);
    public abstract void visit(SqlGteq node, SqlContext context);
    public abstract void visit(SqlLt node, SqlContext context);
    public abstract void visit(SqlLteq node, SqlContext context);
    public abstract void visit(SqlIn node, SqlContext context);
    public abstract void visit(SqlOn node, SqlContext context);
    public abstract void visit(SqlJoinClause node, SqlContext context);
    public abstract void visit(SqlJoin node, SqlContext context);
    public abstract void visit(SqlInnerJoin node, SqlContext context);
    public abstract void visit(SqlOuterJoin node, SqlContext context);
    public abstract void visit(SqlLimit node, SqlContext context);
    public abstract void visit(SqlOffset node, SqlContext context);

    public abstract void visit(SqlNodeArray node, SqlContext context);

    public abstract void visit(Select select, SqlContext context);
    public abstract void visit(Insert insert, SqlContext context);
}
