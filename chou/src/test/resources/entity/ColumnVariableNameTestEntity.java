import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "column_variable_name_test_entity")
class ColumnVariableNameTestEntity {
    @Column(name = "id1", generatedValue = true, unique = true, variableName = "ID_TEST")
    private int intCol1;
    @Column(name = "id2", generatedValue = true, variableName = "ID_TEST2")
    private int intCol2;

    @Column(name = "text1", unique = true, variableName = "TEXT_TEST1")
    private String text1;

    @Column(name = "text2", variableName = "TEXT_TEST2")
    private String text2;

    public int getIntCol1() {
        return intCol1;
    }

    public int getIntCol2() {
        return intCol2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
