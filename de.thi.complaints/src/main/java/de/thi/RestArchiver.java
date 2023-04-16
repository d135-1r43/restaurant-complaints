package de.thi;

import de.thi.jpa.Complaint;
import de.thi.jpa.ComplaintRepository;
import de.thi.rest.ComplaintRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class RestArchiver
{
	private static final Logger LOG = LoggerFactory.getLogger(RestArchiver.class);

	@Inject
	@RestClient
	ComplaintRestClient complaintRestClient;

	public void archive(String complaintText, String responseText, Integer sentiment)
	{
		Complaint complaint = new Complaint(complaintText, responseText, sentiment);
		complaintRestClient.post(complaint);
		LOG.info("Complaint '{}' successfully POSTed", complaintText);
	}
}