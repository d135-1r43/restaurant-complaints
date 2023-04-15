package de.thi.jpa;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComplaintRepository implements PanacheRepository<Complaint>
{
	// auto implemented
}
