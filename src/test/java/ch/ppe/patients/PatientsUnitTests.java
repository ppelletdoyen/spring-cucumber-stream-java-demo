package ch.ppe.patients;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ppe.patients.model.Patient;
import ch.ppe.patients.model.Vaccin;
import ch.ppe.patients.service.PatientsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PatientsApplication.class})
public class PatientsUnitTests {

	List<Patient> patients;
	
	@Autowired
	private PatientsService service;
	
	@Before
	public void before() {
		this.patients = service.list();
	}
	
	@Test
	public void distinctPlus18() {
		List<Patient> distinctPlus18 = patients
				.stream()
				.filter(p -> p.getAge() >= 18)
				.distinct()
				.collect(Collectors.toList());
		assertTrue(distinctPlus18.size() == 2);
	}
	
	@Test
	public void ageAverage() {
		double nbPatients = patients
				.stream()
				.collect(Collectors.toList())
				.size();
		double sum = patients
				.stream()
				.mapToInt(p -> p.getAge())
				.sum();
		double sumByReduce = patients
				.stream()
				.map(Patient::getAge)
				.reduce(0, Integer::sum);
		double ageAverage = patients
				.stream()
				.collect(Collectors.averagingInt(p -> p.getAge()));
		assertTrue(sum == sumByReduce);
		assertTrue(sumByReduce / nbPatients == ageAverage);
	} 
	
	@Test
	public void sortedByName() {
		List<Patient> sortedByName = patients
				.stream()
				.sorted()
				.collect(Collectors.toList());
		assertFalse("Pierre-Louis PELLET-DOYEN".equals(sortedByName.get(0).getName()));
		assertTrue("Ayoub BEN-KHALED".equals(sortedByName.get(0).getName()));
	}
	
	@Test
	public void startWithUppPierre() {
		List<String> startWithUppPierre = patients
				.stream()
				.map(p -> p.getName())
				.map(String::toUpperCase)
				.filter(upp -> upp.startsWith("PIERRE"))
				.collect(Collectors.toList());
		assertFalse(startWithUppPierre.get(0).startsWith("AYOUB"));
		assertTrue(startWithUppPierre.get(0).startsWith("PIERRE"));
	}
	
	@Test
	public void convertToMapOfPatientWithDuplicate() {
		Map<String, Patient> convertToMapOfPatientWithDuplicate = patients
				.stream()
				.collect(Collectors.toMap(p -> p.getName(), p -> p, (p1, p2) -> {
					Patient mergeDuplicate = new Patient();
					mergeDuplicate.setName(p1.getName() + "|" + p2.getName());
					mergeDuplicate.setAge(p1.getAge() + p2.getAge());
					return mergeDuplicate;
				}));
		assertTrue(convertToMapOfPatientWithDuplicate.get("Ayoub BEN-KHALED") instanceof Patient);
		assertTrue(convertToMapOfPatientWithDuplicate.get("Ayoub BEN-KHALED").getAge() == 2);
	}
	
	@Test
	public void groupingByAge2() {
		Map<Integer, List<Patient>> groupingByAge2 = patients
				.stream()
				.collect(Collectors.groupingBy(p -> Integer.valueOf(p.getAge())));
		Optional<Patient> ayoub = groupingByAge2.get(2)
				.stream()
				.filter(p -> "Ayoub BEN-KHALED".equals(p.getName()))
				.findFirst();
		assertTrue(ayoub.get() instanceof Patient);	
		assertTrue("Ayoub BEN-KHALED".equals(ayoub.get().getName()));
		assertFalse("Pascal PELLET-DOYEN".equals(ayoub.get().getName()));
	}
	
	@Test
	public void flatMapVaccins() {
		Optional<Vaccin> flatMapVaccins = patients
				.stream()
				.map(p -> p.getVaccins())
				.flatMap(vaccins -> vaccins.stream())
				.filter(v -> v.getName().startsWith("téta"))
				.findFirst();
		assertTrue(flatMapVaccins.isPresent());
		assertTrue("tétanos".equals(flatMapVaccins.get().getName()));
	}
	
	@Test
	public void testToString() {
		Optional<Patient> patient = patients
				.stream()
				.findFirst();
		assertTrue(patient.get().toString().startsWith("Patient [name="));
	}
	   
}
