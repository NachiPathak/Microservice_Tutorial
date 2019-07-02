package edu.studies.microservices.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import edu.studies.microservices.bean.CurrencyConversionBean;
import edu.studies.microservices.proxy.CurrencyConversionProxy;

@RestController
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;

	@Autowired
	private CurrencyConversionProxy conversionProxy;

	@GetMapping("/currency-convertor-service/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();

		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000//currency-exchange-service/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean response = responseEntity.getBody();
		CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(response.getId(), from, to,
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()));
		currencyConversionBean.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return currencyConversionBean;

	}

	@GetMapping("/currency-convertor-service-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean response = conversionProxy.retrieveExchangeValue(from, to);

		logger.info("{}", response);
		
		CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(response.getId(), from, to,
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()));
		currencyConversionBean.setPort(response.getPort());
		return currencyConversionBean;

	}
}
