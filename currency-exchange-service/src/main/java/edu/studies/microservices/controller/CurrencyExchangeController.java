package edu.studies.microservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.studies.microservices.bean.ExchangeValue;
import edu.studies.microservices.dao.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping("/currency-exchange-service/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		ExchangeValue value = repository.findByFromAndTo(from, to);
		ExchangeValue exchangeValue = new ExchangeValue(1001L, value.getFrom(), value.getTo(),
				value.getConversionMultiple());
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		logger.info("{}", exchangeValue);

		return exchangeValue;
	}
}
