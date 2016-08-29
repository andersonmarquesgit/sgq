package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Usuario;
import br.com.sgq.repository.UserRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UserRepository userRepository;

	public Usuario findOn(Long id) {
		return userRepository.findOne(id);
	}

	public Usuario findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public List<Usuario> list() {
		return userRepository.findAll();
	}

	public void salvar(Usuario usuario) {
		userRepository.saveAndFlush(usuario);
	}
}
