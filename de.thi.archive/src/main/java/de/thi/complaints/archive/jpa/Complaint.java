package de.thi.complaints.archive.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Complaint
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String complaintText;
	private String responseText;
	private Integer sentiment;

	public Complaint()
	{
		// no args
	}

	public Complaint(String complaintText, String responseText, Integer sentiment)
	{
		this.complaintText = complaintText;
		this.responseText = responseText;
		this.sentiment = sentiment;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getComplaintText()
	{
		return complaintText;
	}

	public void setComplaintText(String complaintText)
	{
		this.complaintText = complaintText;
	}

	public String getResponseText()
	{
		return responseText;
	}

	public void setResponseText(String responseText)
	{
		this.responseText = responseText;
	}

	public Integer getSentiment()
	{
		return sentiment;
	}

	public void setSentiment(Integer sentiment)
	{
		this.sentiment = sentiment;
	}
}
