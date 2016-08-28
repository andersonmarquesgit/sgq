package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.repository.TipoReclamacaoRepository;

@Service
public class TipoReclamacaoService {

	@Autowired
	private TipoReclamacaoRepository tipoReclamacaoRepository;
	
	public List<TipoReclamacao> list() {
		return tipoReclamacaoRepository.findAll();
	}

}
