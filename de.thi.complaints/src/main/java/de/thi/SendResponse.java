package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SendResponse
{
	private static final Logger LOG = LoggerFactory.getLogger(SendResponse.class);

	public void sendResponse(String response)
	{
		LOG.info("Response sent to Guest");
	}
}
