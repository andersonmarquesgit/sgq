package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{

	public Usuario findByLoginAndPassword(String login, String password);
	public Usuario findByLogin(String login);
}
