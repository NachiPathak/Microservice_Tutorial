package edu.studies.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.studies.microservices.bean.LimitsConfiguration;

@RestController
public class LimitsConfigController {

	@Autowired
	private LimitsServiceConfiguration limitsServiceConfiguration;

	@GetMapping("/limits")
	public LimitsConfiguration getLimitsConfiguration() {
		return new LimitsConfiguration(limitsServiceConfiguration.getMaximum(), limitsServiceConfiguration.getMinimum());
	}
}
