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

@Path("/complaints/") // Root Path für die ganze Klasse
public class ComplaintResource
{
	@Inject // Kommt über das Pattern Inversion of Control
	ComplaintRepository complaintRepository;

	@GET // GET Endpunkt
	@Produces(MediaType.APPLICATION_JSON) // mach mir n JSON und kein doofes XML
	public List<Complaint> all()
	{
		return complaintRepository.listAll(); // hol alle aus der DB
	}

	@GET // GET Endpunkt
	@Path("{id}") // ... mit der ID im Pfad
	@Produces(MediaType.APPLICATION_JSON) // mach mir n JSON und kein doofes XML
	public Complaint byId(@PathParam("id") Long id) // gib mir die ID als Parameter, dass ich sie in der Methode nutzen kann
	{
		return complaintRepository.findById(id); // finde mir das Ding mit genau dieser ID
	}

	@POST // POST Endpunkt
	@Consumes(MediaType.APPLICATION_JSON) // nimm n JSON und kein doofes XML
	@Transactional // mach bitte ne Datenbank-Transaktion außenrum
	public void post(Complaint complaint) // automagisch der Body
	{
		complaintRepository.persist(complaint); // speichert das Ding in der DB
	}
}
