package de.thi.sentiment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatGPTPromptCrafterTests
{
	@Test
	public void shouldCraftANicePrompt()
	{
		// given
		String text = "The meal was terrible, I will never come back.";

		// when
		ChatGPTPromptCrafter promptCrafter = new ChatGPTPromptCrafter();
		String prompt = promptCrafter.craftPrompt(text);

		// then
		String expected = """
			I will provide you with a text and you will provide me with a sentiment.
			The sentiment is a natural number between 0 and 10, while 0 means very angry and 10 means very happy.
			Text: The meal was terrible, I will never come back.
			Sentiment:""";
		assertEquals(prompt, expected);
	}
}


