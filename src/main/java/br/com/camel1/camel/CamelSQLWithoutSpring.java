package br.com.camel1.camel;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelSQLWithoutSpring {

	private static final Logger LOGGER = LoggerFactory.getLogger(CamelSQLWithoutSpring.class);

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

				                from("timer://myTimer?period=2000")
						        .to("sql:SELECT * FROM TBCIDADE?outputType=StreamList&outputClass=org.apache.camel.component.sql.ProjectModel")
						        .to("log:stream")
						        .split(body()).streaming()
						            .to("log:row")
						            .to("mock:result")
						        .end();
								
								
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