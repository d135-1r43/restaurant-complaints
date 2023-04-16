package de.thi.complaints.archive;

import de.thi.complaints.archive.jpa.Complaint;
import de.thi.complaints.archive.jpa.ComplaintRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/complaints/")
public class ComplaintResource
{
	@Inject
	ComplaintRepository complaintRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Complaint> all()
	{
		return complaintRepository.listAll()
			.stream()
			.toList();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Complaint byId(@PathParam("id") Long id)
	{
		return complaintRepository.findById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public void post(Complaint complaint)
	{
		complaintRepository.persist(complaint);
	}
}
