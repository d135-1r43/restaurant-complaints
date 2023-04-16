package de.thi.rest;

import de.thi.jpa.Complaint;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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