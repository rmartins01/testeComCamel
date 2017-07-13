package br.com.camel1;

import javax.sql.DataSource;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

//@Component
//@DependsOn(value={"dataSource"})
//@Scope(value = "prototype")
public class MultitenantDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
    	
    	return TenantContext.getCurrentTenant();
    	
//    	Map<Object, Object> currentTenant = TenantContext.getCurrentTenant();
//    	setTargetDataSources(currentTenant);
//    	afterPropertiesSet();
//        return currentTenant;
    }
    
    public DataSource reloadDataSource(){
    	return super.determineTargetDataSource();
    }
}
