package de.thi;

import de.thi.jpa.Complaint;
import de.thi.jpa.ComplaintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class Archiver
{
	private static final Logger LOG = LoggerFactory.getLogger(Archiver.class);

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