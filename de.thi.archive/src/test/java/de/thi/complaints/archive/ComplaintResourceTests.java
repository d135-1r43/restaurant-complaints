package de.thi.complaints.archive;

import de.thi.complaints.archive.jpa.Complaint;
import de.thi.complaints.archive.jpa.ComplaintRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@QuarkusTest
@TestHTTPEndpoint(ComplaintResource.class)
public class ComplaintResourceTests
{
	@Inject
	ComplaintRepository complaintRepository;

	@BeforeEach
	@Transactional
	public void emptyTheDatabase()
	{
		complaintRepository.deleteAll();
	}

	@Test
	public void shouldReturnEmptyList()
	{
		when()
			.get()
			.then()
			.statusCode(200)
			.body(is("[]"));
	}

	@Test
	public void shouldPersistAComplaint()
	{
		String json = """
			{
			  "complaintText": "Too little salt",
			  "responseText": "We are sorry",
			  "sentiment": 2
			}
			""";

		// when
		given()
			.contentType(ContentType.JSON)
			.body(json)
			.when().post()
			.then()
			.statusCode(204);

		// then
		List<Complaint> complaints = complaintRepository.listAll();
		assertThat(complaints, hasSize(1));
		assertThat(complaints, contains(hasProperty("complaintText", is("Too little salt"))));
	}
}
