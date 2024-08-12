package de.thi.sentiment;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatGPTPromptCrafter
{
	public String craftPrompt(String text)
	{
		StringBuilder prompt = new StringBuilder();
		prompt.append("""
  				I will provide you with a text and you will provide me with a sentiment. 
  				The sentiment is a natural number between 0 and 10, while 0 means very angry and 10 means very happy.
  				""");
		prompt.append("Text: " + text + "\n");
		prompt.append("Sentiment:");
		return prompt.toString();
	}
}
