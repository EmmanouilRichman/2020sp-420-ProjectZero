package projectzero.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UmlClassYamlMapperTest {

    @Test
    void testReadsAndWritesClassesToFile(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("animals.yaml");

        UmlClass dog = new UmlClass("dog");
        UmlClass cat = new UmlClass("cat");
        UmlClass bat = new UmlClass("bat");

        dog.addField(new Field("color"));

        List<UmlClass> animals = Arrays.asList(dog, cat, bat);
        Map<String, UmlClass> animalsMap = animals.stream().collect(Collectors.toMap(animal -> animal.getName(), animal -> animal));

        UmlClassYamlMapper umlClassYamlMapper = new UmlClassYamlMapper();
        umlClassYamlMapper.write(path.toString(), animalsMap);

        Map<String, UmlClass> readAnimalsMap = umlClassYamlMapper.read(path.toString());

        Assertions.assertTrue(animalsMap.equals(readAnimalsMap));
        Assertions.assertTrue(animalsMap.get("dog").getFieldMap().containsKey("color"));
    }
}