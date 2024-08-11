package de.thi;

import de.thi.jpa.Complaint;
import de.thi.jpa.ComplaintRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.junit.jupiter.api.Test;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ArchiveProcessTests
{
	@Inject
	@Named("archivedb")
	Process<? extends Model> archiveProcess;

	@Inject
	ComplaintRepository complaintRepository;

	@Test
	public void shouldArchiveInDatabase()
	{
		// given
		assertNotNull(archiveProcess);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("complaintText", "Too much parsley.");
		parameters.put("responseText", "Thank You. We will reduce the parsley");
		parameters.put("sentiment", Integer.valueOf(3));

		Model model = archiveProcess.createModel();
		model.fromMap(parameters);

		// when
		ProcessInstance<?> processInstance = archiveProcess.createInstance(model);
		processInstance.start();

		// then
		List<Complaint> complaintsFromDatabase = complaintRepository.findAll().list();
		assertThat(complaintsFromDatabase, hasSize(1));
		assertThat(complaintsFromDatabase, contains(hasProperty("complaintText", is("Too much parsley."))));
	}
}
