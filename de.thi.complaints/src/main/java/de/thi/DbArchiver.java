package de.thi;

import de.thi.jpa.Complaint;
import de.thi.jpa.ComplaintRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DbArchiver
{
	private static final Logger LOG = LoggerFactory.getLogger(DbArchiver.class);

	@Inject
	ComplaintRepository complaintRepository;

	@Transactional
	public void archive(String complaintText, String responseText, Integer sentiment)
	{
		Complaint complaint = new Complaint(complaintText, responseText, sentiment);
		complaintRepository.persist(complaint);
		LOG.info("Complaint '{}' successfully persisted", complaintText);
	}
}