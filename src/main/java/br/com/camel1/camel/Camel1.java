package br.com.camel1.camel;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Camel1 extends RouteBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(Camel1.class);
	
	@Override
	public void configure() throws Exception {
		LOGGER.info("INICIANDO ROTA");
		
		from("file:data/inbox").log("========>>>>>>LOGANDO...").to("file:data/outbox");
		
		LOGGER.info("FINALIZANDO ROTA");
	}
}