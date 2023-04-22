package de.thi.sentiment;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AiSentiment
{
	@Inject
	ChatGPTPromptCrafter promptCrafter;

	@ConfigProperty(name = "openai.api.key")
	String openAiApiKey;

	public Integer analyzeSentiment(String text)
	{
		if (openAiApiKey == null)
		{
			throw new RuntimeException("You need to provide an OpenAI API key in your configuration at 'openai.api.key'");
		}

		String prompt = promptCrafter.craftPrompt(text);

		CompletionRequest completionRequest = CompletionRequest.builder()
			.prompt(prompt)
			.model("text-davinci-003")
			.maxTokens(60)
			.temperature(0d)
			.frequencyPenalty(0d)
			.presencePenalty(0d)
			.build();

		return new OpenAiService(openAiApiKey)
			.createCompletion(completionRequest)
			.getChoices()
			.stream()
			.findFirst()
			.map(CompletionChoice::getText)
			.map(String::trim)
			.map(Integer::valueOf)
			.orElseThrow(() -> new RuntimeException("Could not determine sentiment with ChatGPT"));
	}
}
