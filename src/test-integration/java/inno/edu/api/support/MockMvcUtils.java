package inno.edu.api.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

public class MockMvcUtils {
    public static String getIdentity(MvcResult mvcResult) throws IOException {
        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(contentAsString);

        return jsonNode.get("id").asText();
    }
}
