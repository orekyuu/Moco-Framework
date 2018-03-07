package net.orekyuu.moco.feeling.node;

public class SqlLiteral implements SqlNode, Predicatable {
    private String text;

    public SqlLiteral(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
