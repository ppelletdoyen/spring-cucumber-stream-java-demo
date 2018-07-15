package ch.ppe.patients;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({ "ch.ppe.patients" })
@PropertySource("application.properties")
public class PatientsApplication {
}
