package ch.ppe.patients.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ppe.patients.model.Patient;
import ch.ppe.patients.rest.PatientsController;

@Service
public class PatientsService {
	
	@Autowired
	private PatientsController controller;

	public List<Patient> list() {
		return controller.list();
	}
	
}
