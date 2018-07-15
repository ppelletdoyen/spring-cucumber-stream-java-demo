package ch.ppe.patients;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import ch.ppe.patients.model.Patient;
import ch.ppe.patients.service.PatientsService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes= PatientsApplication.class)
public class PatientsServiceFeaturesSteps {
	
	@Autowired
	private PatientsService service;
	
	private boolean success;
	
    @Given("^patients service exists$")
    public void patients_service_exists() throws Throwable {
        service.toString();
    }
    
    @When("^I call patients service$")
    public void i_call_patients_service() throws Throwable {
        List<Patient> patients = service.list();
        success = patients != null;
    }
    
    @Then("^it should have been a success$")
    public void it_should_have_been_a_success() throws Throwable {
        Assert.assertTrue(success);
    }

}
