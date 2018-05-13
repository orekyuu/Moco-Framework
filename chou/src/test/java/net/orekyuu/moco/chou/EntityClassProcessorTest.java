package net.orekyuu.moco.chou;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import net.orekyuu.moco.chou.entity.TableProcessor;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class EntityClassProcessorTest {

    @Test
    public void compileEntityClass() {
        Compilation compilation = javac()
                .withProcessors(new TableProcessor())
                .compile(JavaFileObjects.forResource("entity/SimpleEntity.java"));

        CompilationSubject.assertThat(compilation).succeeded();

        CompilationSubject.assertThat(compilation).generatedSourceFile("SimpleEntities")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/SimpleEntities.java"));
        CompilationSubject.assertThat(compilation).generatedSourceFile("SimpleEntityList")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/SimpleEntityList.java"));
    }

    @Test
    public void compileEnumEntityClass() {
        Compilation compilation = javac()
                .withProcessors(new TableProcessor())
                .compile(JavaFileObjects.forResource("entity/EnumEntity.java"));

        CompilationSubject.assertThat(compilation).succeeded();

        CompilationSubject.assertThat(compilation).generatedSourceFile("EnumEntities")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/EnumEntities.java"));
        CompilationSubject.assertThat(compilation).generatedSourceFile("EnumEntityList")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/EnumEntityList.java"));
    }

    @Test
    public void compileGeneratedIdEntityClass() {
        Compilation compilation = javac()
                .withProcessors(new TableProcessor())
                .compile(JavaFileObjects.forResource("entity/GeneratedIdEntity.java"));

        CompilationSubject.assertThat(compilation).succeeded();

        CompilationSubject.assertThat(compilation).generatedSourceFile("GeneratedIdEntities")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/GeneratedIdEntities.java"));
        CompilationSubject.assertThat(compilation).generatedSourceFile("GeneratedIdEntityList")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/GeneratedIdEntityList.java"));
    }

    @Test
    public void compileUniqueTestEntityClass() {
        Compilation compilation = javac()
                .withProcessors(new TableProcessor())
                .compile(JavaFileObjects.forResource("entity/UniqueTestEntity.java"));

        CompilationSubject.assertThat(compilation).succeeded();

        CompilationSubject.assertThat(compilation).generatedSourceFile("UniqueTestEntities")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/UniqueTestEntities.java"));
        CompilationSubject.assertThat(compilation).generatedSourceFile("UniqueTestEntityList")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/UniqueTestEntityList.java"));
    }

    @Test
    public void compileColumnVariableNameTestEntityClass() {
        Compilation compilation = javac()
                .withProcessors(new TableProcessor())
                .compile(JavaFileObjects.forResource("entity/ColumnVariableNameTestEntity.java"));

        CompilationSubject.assertThat(compilation).succeeded();

        CompilationSubject.assertThat(compilation).generatedSourceFile("ColumnVariableNameTestEntities")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/ColumnVariableNameTestEntities.java"));
        CompilationSubject.assertThat(compilation).generatedSourceFile("ColumnVariableNameTestEntityList")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("entity/expected/ColumnVariableNameTestEntityList.java"));
    }
}
