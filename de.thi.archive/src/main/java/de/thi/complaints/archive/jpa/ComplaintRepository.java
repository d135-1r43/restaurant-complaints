package de.thi.complaints.archive.jpa;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComplaintRepository implements PanacheRepository<Complaint>
{
	// auto implemented
}
