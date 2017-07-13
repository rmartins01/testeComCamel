/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.camel1.camel;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry;
import org.apache.camel.spi.Registry;
import org.apache.camel.util.jndi.JndiContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

@Component
public class CamelSQL{
	
	@Autowired
	private CamelContext context;

	public void configure() {
		try {

			JndiContext jndiContext = new JndiContext();
			jndiContext.bind("dataSource", (DataSource) dataSource());
			context = new DefaultCamelContext(jndiContext);
			
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					// TODO Auto-generated method stub
					
					from("direct:start")
					.to("sql:SELECT * FROM TBCIDADE where id=606")
					.log("========>>>>>>LOGGING1...").process(new Processor() {
						
						public void process(Exchange xchg) throws Exception {
							
							Object bodyObj = xchg.getIn().getBody();
							
							if(bodyObj != null && bodyObj instanceof ArrayList){
								
								ArrayList bodyList = (ArrayList)xchg.getIn().getBody();
								
								if(bodyList != null && bodyList.size() >0){
									
									LinkedCaseInsensitiveMap body = (LinkedCaseInsensitiveMap) bodyList.get(0);
									if(body != null && !body.isEmpty()){
										
										System.out.println("Processing....." + body);
										String string = body.get("ID").toString();
										String string2 = body.get("NOME").toString();
										System.out.println(">>>>>>>string: " + string + " string2: " + string2);
									}
								}
								
							}
						}
						
					});
	
				}
			});
			
//			context.sta
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws Exception {

		DataSource dataSource = (DataSource) dataSource();
		JndiContext jndiContext;
			jndiContext = new JndiContext();
			jndiContext.bind("dataSource", dataSource);
			CamelContext camelContext = new DefaultCamelContext(jndiContext);


			// start the route and let it do its work
			camelContext.start();
			Thread.sleep(50000);

			// stop the CamelContext
			camelContext.stop();
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
