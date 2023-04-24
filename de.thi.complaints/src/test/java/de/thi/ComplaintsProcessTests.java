package de.thi;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;
import org.kie.kogito.Model;
import org.kie.kogito.process.BaseEventDescription;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ComplaintsProcessTests
{
	@Inject
	@Named("complaints")
	Process<? extends Model> complaintsProcess;

	@Test
	public void shouldStartProcess()
	{
		//given
		assertNotNull(complaintsProcess);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("complaintText", "Too much salt.");

		Model model = complaintsProcess.createModel();
		model.fromMap(parameters);

		ProcessInstance<?> processInstance = complaintsProcess.createInstance(model);

		//when
		processInstance.start();

		//then
		assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.status());
		assertEquals("Get Sentiment", ((BaseEventDescription)processInstance.events().toArray()[0]).getNodeName());
	}
}