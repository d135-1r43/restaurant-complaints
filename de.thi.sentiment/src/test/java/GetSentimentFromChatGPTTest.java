import de.thi.sentiment.ComplaintRequest;
import de.thi.sentiment.GetSentimentFromChatGPT;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import rest.ChatGPTRestClient;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GetSentimentFromChatGPTTest {

    @Inject
    GetSentimentFromChatGPT getSentimentFromChatGPT;

    @Test
    public void shouldSendRequest(){
        // given
        String complaint = "Too much parsley";

        // when
        Integer sentiment = getSentimentFromChatGPT.getSentimentFromChatGPT(complaint);

        assertEquals(0, sentiment.intValue());
    }
}
