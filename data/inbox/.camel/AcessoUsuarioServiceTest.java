package br.com.netservicos.diario;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.netservicos.diario.service.AcessoUsuarioService;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository}.
 * 
 * @author Robson Martins 11 de abr de 2017 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AcessoUsuarioServiceTest {

	@Autowired AcessoUsuarioService service;

/*	@Test
	public void testTbAcessoUsuSalvarListarTodos() throws Exception {

		TbAcessoUsu ent = new TbAcessoUsu();
		ent.setDsAcessoUsu("Teste Robson");
		ent.setDsLabel("Teste Label");
		ent.setDtCadastro(new Date());
		ent.setFlAtivo(1);
		service.salva(ent);
		
		
		Iterable<TbAcessoUsu> lista = service.obterTodosAcessoUsuarios();

		for(TbAcessoUsu usu:lista){
			System.out.println(usu);
		}

		assertThat(lista, is(notNullValue()));
	}*/

}
