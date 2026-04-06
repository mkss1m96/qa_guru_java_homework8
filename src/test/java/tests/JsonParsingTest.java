package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class JsonParsingTest {
    private ClassLoader cl = JsonParsingTest.class.getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Тестирование JSON файла")
    void jsonParsingTest() throws Exception {

        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("example.json"),
                StandardCharsets.UTF_8
        )) {
            Example actual = mapper.readValue(reader, Example.class);

            Assertions.assertEquals("Bob", actual.getName());
            Assertions.assertEquals("30", actual.getAge());
            Assertions.assertEquals("true", actual.getIsEmployed());
        }
    }
}
