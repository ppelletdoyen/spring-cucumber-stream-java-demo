package ch.ppe.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import ch.ppe.patients.model.Patient;
import ch.ppe.patients.service.PatientsService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes= PatientsApplication.class)
public class PatientsVaccinatedControlsSteps {
	
	@Autowired
	private PatientsService service;
	
	private List<Patient> patients;
	
	private int nbTetanusVaccinesInjected;
	private int nbRabiesVaccinesInjected;
	private int nbWhoopingCoughVaccinesInjected;
	private int nbPatients;
	
	@Given("^the patients list exist$")
	public void the_patients_list_exist() throws Throwable {
	    patients = service.list();
	    Assert.assertTrue(!patients.isEmpty());
	}
	
	@When("^I want to know if at least one patient has been vaccinated against tetanus$")
	public void i_want_to_know_if_at_least_one_patient_has_been_vaccinated_against_tetanus() throws Throwable {
		nbTetanusVaccinesInjected = patients
				.stream()
				.map(p -> p.getVaccins())
				.flatMap(vaccins -> vaccins.stream())
				.filter(v -> v.getName().startsWith("téta"))
				.collect(Collectors.toList())
				.size();
	}
	
	@Then("^a vaccinated patient against tetanus must exist$")
	public void a_vaccinated_patient_against_tetanus_must_exist() throws Throwable {
		Assert.assertTrue(nbTetanusVaccinesInjected > 0);
	}
	
	@Given("^all the (\\d+) patients must exist$")
	public void all_the_patients_must_exist(int nbPatients) throws Throwable {
		patients = service.list();
		this.nbPatients = patients.size();
		Assert.assertTrue(nbPatients == this.nbPatients);
	}

	@When("^I want to verify that all patients are vaccinated against rabies$")
	public void i_want_to_verify_that_all_patients_are_vaccinated_against_rabies() throws Throwable {
		nbRabiesVaccinesInjected = patients
				.stream()
				.map(p -> p.getVaccins())
				.flatMap(vaccins -> vaccins.stream())
				.filter(v -> v.getName().startsWith("rage"))
				.collect(Collectors.toList())
				.size();
	}

	@Then("^all patients are vaccinated against rabies$")
	public void all_patients_are_vaccinated_against_rabies() throws Throwable {
		Assert.assertTrue(nbRabiesVaccinesInjected == this.nbPatients);
	}
	
	@When("^I want to verify that (\\d+) patients are vaccinated against whooping cough$")
	public void i_want_to_verify_that_patients_are_vaccinated_against_whooping_cough(int arg1) throws Throwable {
		nbWhoopingCoughVaccinesInjected = patients
				.stream()
				.map(p -> p.getVaccins())
				.flatMap(vaccins -> vaccins.stream())
				.filter(v -> v.getName().startsWith("coque"))
				.collect(Collectors.toList())
				.size();
	}

	@Then("^(\\d+) patients are vaccinated against whooping cough$")
	public void patients_are_vaccinated_against_whooping_cough(int nbPatients) throws Throwable {
		Assert.assertTrue(nbWhoopingCoughVaccinesInjected == nbPatients);
	}

}
