import de.thi.sentiment.ComplaintRequest;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GetSentimentFromChatGPTTest {

    @Inject
    @Named("sentiment")
    Process<? extends Model> sentimentsProcess;

    @Inject
    @RestClient
    ChatGPTRestClient chatGPTRestClient;

    @Test
    public void shouldSendRequest(){

        //given
        assertNotNull(sentimentsProcess);

        //when
        ComplaintRequest complaintRequest = new ComplaintRequest();
        complaintRequest.setModel("gpt-3.5-turbo");
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("role", "user");
        messages.put("content", "Act as a sentiment analysis. I will provide you with a text for a complaint and you give me a number between 0 and 10. 0 = very angry. 10 = nice feedback. Complaint: My noodles are frozen.");
        complaintRequest.setMessages(messages);
        JSONObject jsonObject = new JSONObject(complaintRequest);
        System.out.println("Hier die JSON: " + jsonObject);

        //then
        //sentimentRestClient.post(complaintRequest, "Bearer {{API_KEY}}");
    }


}
