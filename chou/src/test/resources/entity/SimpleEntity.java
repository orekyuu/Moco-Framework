import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "simple_entities")
class SimpleEntity {
    @Column(name = "id")
    private int id;
    @Column(name = "text")
    private String text;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}