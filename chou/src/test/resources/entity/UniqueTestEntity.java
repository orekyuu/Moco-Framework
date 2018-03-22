import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "unique_test_entities")
class UniqueTestEntity {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "text", unique = true)
    private String text;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
