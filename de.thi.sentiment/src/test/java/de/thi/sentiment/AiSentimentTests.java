package de.thi.sentiment;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AiSentimentTests
{
	@Inject
	AiSentiment aiSentiment;

	@Test
	@Disabled("needs an OpenAI API key")
	public void shouldDetermineSentimentWithChatGPT()
	{
		// given
		String text = "Meal was terrible. I will never come back.";

		// when
		Integer sentiment = aiSentiment.analyzeSentiment(text);

		// then
		assertEquals(0, sentiment);
	}
}
