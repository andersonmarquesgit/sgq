package br.com.sgq.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.sgq.config.ApplicationConfig;
import br.com.sgq.model.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(locations="classpath:META-INF/teste-context.xml")
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;
	
	@Test
	public void test() {
		Usuario user = new Usuario();
		user.setEmail("teste");
		user.setNome("Anderson");
		
		repository.save(user);
		
		Usuario dbuser = repository.findOne(user.getId());
		assertNotNull(dbuser);
		System.out.println(dbuser.getNome());
	}
}
