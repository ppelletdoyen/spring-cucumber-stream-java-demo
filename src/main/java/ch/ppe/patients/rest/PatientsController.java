package ch.ppe.patients.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.ppe.patients.model.Patient;

@RestController
public class PatientsController {

	@Value("${url.json.server}")
	private String urlJsonServer;

	@RequestMapping("/list")
	public List<Patient> list() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		ResponseEntity<List<Patient>> rateResponse = restTemplate.exchange(urlJsonServer, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Patient>>() {
				});
		return rateResponse.getBody();
	}

}
