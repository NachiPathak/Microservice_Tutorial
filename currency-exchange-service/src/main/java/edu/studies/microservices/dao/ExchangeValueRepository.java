package edu.studies.microservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.studies.microservices.bean.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
	public ExchangeValue findByFromAndTo(String from, String to);
	

}