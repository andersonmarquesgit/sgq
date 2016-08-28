package br.com.sgq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.StatusReclamacao;
import br.com.sgq.repository.StatusReclamacaoRepository;

@Service
public class StatusReclamacaoService {
	@Autowired
	private StatusReclamacaoRepository statusReclamacaoRepository;
	
	public StatusReclamacao findOn(Long id) {
		return statusReclamacaoRepository.findOne(id);
	}

}
