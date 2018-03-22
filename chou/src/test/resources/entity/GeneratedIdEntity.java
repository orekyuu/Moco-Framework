import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "generated_id_entities")
class GeneratedIdEntity {
    @Column(name = "id", generatedValue = true)
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