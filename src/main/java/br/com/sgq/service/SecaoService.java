package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Secao;
import br.com.sgq.repository.SecaoRepository;

@Service
public class SecaoService {

	@Autowired
	private SecaoRepository secaoRepository;
	
	public List<Secao> listar() {
		return secaoRepository.findAll();
	}

}
