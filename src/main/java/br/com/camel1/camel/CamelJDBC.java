package br.com.camel1.camel;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class CamelJDBC extends RouteBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(CamelJDBC.class);
	
	@Autowired
	private CamelContext context;

	@Override
	public void configure() throws Exception {

        rest("/teste")
        .get().to("direct:get")
        ;        
        
		
		if (dataSource().getConnection().isClosed()) {
			LOGGER.error("CONEXÃO CANNOT BE CLOSED");
			System.exit(0);
//		}else{
//			dataSource().getConnection().close();
		}

		
		LOGGER.info("RUNNNING ROTE");

        SimpleRegistry reg = new SimpleRegistry();
        reg.put("dataSource", dataSource());
        
        context = new DefaultCamelContext(reg);
        
//        context.getEndpointRegistry().put("myDataSource", dataSource());
        
//        context.getEndpointRegistry().put("myDataSource", dataSource());
		
	       from("direct:getPersons")
           .setBody(simple("SELECT * FROM TBCIDADE"))
           .log("========>>>>>>LOGGING...")
           .to("jdbc:dataSource");
		
//		 from("file:data/inbox").log("========>>>>>>LOGGING...").to("file:data/outbox");

		LOGGER.info("ENDING ROTE");
	}

	public DataSource dataSource() {

//		return DataSourceBuilder.create().username("diario_quartz").password("cotinet2013")
//				.url("jdbc:oracle:thin:@localhost:1521:xe").driverClassName("oracle.jdbc.OracleDriver").build();
		
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUsername("diario");
        ds.setPassword("cotinet2013");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        return ds;
	}
}