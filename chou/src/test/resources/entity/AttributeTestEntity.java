import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;
import java.time.LocalDateTime;

@Table(name = "attribute_test_entity")
class AttributeTestEntity {
    enum Hoge {
        HOGE, FUGA
    }

    @Column(name = "int_value", generatedValue = true, unique = true)
    private int intValue;
    @Column(name = "int_value2", unique = true)
    private Integer intValue2;

    @Column(name = "boolean_value", unique = true)
    private boolean booleanValue;
    @Column(name = "boolean_value2", unique = true)
    private Boolean booleanValue2;

    @Column(name = "string_value", unique = true)
    private String stringValue;

    @Column(name = "enum_value", unique = true)
    private Hoge hogeValue;

    @Column(name = "local_date_time_value")
    private LocalDateTime localDateTimeValue;

    public int getIntValue() {
        return intValue;
    }

    public Integer getIntValue2() {
        return intValue2;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public Boolean getBooleanValue2() {
        return booleanValue2;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Hoge getHogeValue() {
        return hogeValue;
    }

    public LocalDateTime getLocalDateTimeValue() {
        return localDateTimeValue;
    }
}
