package br.com.camel1;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class MultitenantConnection {

	@Autowired
	private DataSource dataSource;

	public Map<Object, Object> getDataSourceMapConnection(String nome, String driverClass, String url, String userName,
			String password) {

		if (dataSource instanceof MultitenantDataSource && dataSource != null) {

			DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
			dataSourceBuilder.driverClassName(driverClass).url(url).username(userName).password(password);

			Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
			dataSourceMap.put(nome, dataSourceBuilder.build());

			return dataSourceMap;
//			MultitenantDataSource ds = (MultitenantDataSource) dataSource;
//			ds.setTargetDataSources(dataSourceMap);
//			ds.afterPropertiesSet();
		}
		
		return null;
	}
	
	
}
