package br.com.camel1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.camel1.TenantContext;
import br.com.camel1.service.MultitenantService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private MultitenantService service;

//	@Autowired
//	private MultitenantConnection connection;

	@RequestMapping(value = "/consulta/{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> editarContato(@PathVariable("nome") String nome, Model model, HttpServletRequest request) {

//		TenantContext.setCurrentTenant(connection.getDataSourceMapConnection(nome, "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@localhost:1521:xe",
//				"diario_quartz", "cotinet2013"));
		
		TenantContext.setCurrentTenant(nome);

		return ResponseEntity.ok(service.salvaTodos(nome));
	}

}
