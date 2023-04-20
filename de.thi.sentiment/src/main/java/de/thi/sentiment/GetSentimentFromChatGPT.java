package de.thi.sentiment;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.ChatGPTRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class GetSentimentFromChatGPT {

    @Inject
    @RestClient
    ChatGPTRestClient chatGPTRestClient;

    private static final Logger LOG = LoggerFactory.getLogger(GetSentimentFromChatGPT.class);

    public void getSentimentFromChatGPT(String text){
        ComplaintRequest complaintRequest = new ComplaintRequest();
        complaintRequest.setModel("gpt-3.5-turbo");
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("role", "user");
        messages.put("content", "Act as a sentiment analysis. I will provide you with a text for a complaint and you give me a number between 0 and 10. 0 = very angry. 10 = nice feedback. Complaint: My noodles are frozen.");
        complaintRequest.setMessages(messages);

        LOG.info("Task successfully triggered. Text: " + complaintRequest);
        chatGPTRestClient.post(complaintRequest, "Bearer {{API_KEY}}");
    }

}
