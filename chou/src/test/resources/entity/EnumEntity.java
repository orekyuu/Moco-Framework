import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "enum_entities")
class EnumEntity {
    enum Locale {
        JA, EN;
    }

    @Column(name = "id")
    private int id;
    @Column(name = "locale")
    private Locale locale;

    public int getId() {
        return id;
    }

    public Locale getLocale() {
        return locale;
    }
}