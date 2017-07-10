package br.com.camel1.camel;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelJDBCWithoutSpring {

	private static final Logger LOGGER = LoggerFactory.getLogger(CamelJDBCWithoutSpring.class);

	public static void main(String[] args) {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				DataSource dataSource = (DataSource) dataSource();
				JndiContext jndiContext;
				try {
					jndiContext = new JndiContext();
					jndiContext.bind("dataSource", dataSource);
					CamelContext camelContext = new DefaultCamelContext(jndiContext);
					try {

						camelContext.addRoutes(new RouteBuilder() {

							@Override
							public void configure() throws Exception {

								if (dataSource.getConnection().isClosed()) {
									LOGGER.error("CONEXÃO CANNOT BE CLOSED");
									System.exit(0);
								}

								LOGGER.info("RUNNNING ROTE");

//								from("timer://dataSource?fixedRate=true&amp;period=3s")
								from("direct:projects")
										.to("jdbc:dataSource")
										.setBody(constant("SELECT * FROM TBCIDADE"))
										.split(body()).to("mock:result")
										.log("========>>>>>>LOGGING1...").process(new Processor() {

											public void process(Exchange xchg) throws Exception {

												Map<String, Object> row = xchg.getIn().getBody(Map.class);
												System.out.println("Processing....." + row);

												String string = row.get("ID").toString();
												String string2 = row.get("NOME").toString();

												System.out.println(">>>>>>>string: " + string + " string2: " + string2);
											}

										});
								;

								// from("direct:dataSource").log("========>>>>>>LOGGING2...");

								LOGGER.info("ENDING ROTE");
							}
						});

					} finally {
						// camelContext.stop();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t.start();
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static DataSource dataSource() {

		// return
		// DataSourceBuilder.create().username("diario_quartz").password("cotinet2013")
		// .url("jdbc:oracle:thin:@localhost:1521:xe").driverClassName("oracle.jdbc.OracleDriver").build();

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.OracleDriver");
		ds.setUsername("diario");
		ds.setPassword("cotinet2013");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		return ds;
	}
}