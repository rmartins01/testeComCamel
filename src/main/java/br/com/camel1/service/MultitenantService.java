package br.com.camel1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.camel1.data.StatusRepository;
import br.com.camel1.data.TbStatus;

@Component
public class MultitenantService {

	@Autowired
	private StatusRepository orderRepository;
	
//	@Autowired
//	private	MultitenantDataSource multitenantDataSource;
	
	@Transactional
	public TbStatus salvaTodos(String nome){
//		multitenantDataSource.setDefaultTargetDataSource(multitenantDataSource.reloadDataSource());
		
		TbStatus newOrder = new TbStatus();
		newOrder.setNome(nome);
		
		orderRepository.deleteAll();

		orderRepository.save(newOrder);
		
		return newOrder;
	}
}
