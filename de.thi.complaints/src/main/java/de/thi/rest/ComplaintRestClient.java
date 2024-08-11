package de.thi.rest;

import de.thi.jpa.Complaint;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@ApplicationScoped
@RegisterRestClient(configKey = "complaint")
public interface ComplaintRestClient
{
	@GET
	List<Complaint> all();

	@GET
	@Path("{id}")
	Complaint byId(@PathParam("id") Long id);

	@POST
	void post(Complaint complaint);
}