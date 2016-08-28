package br.com.sgq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.AnaliseCritica;
import br.com.sgq.model.Secao;
import br.com.sgq.model.SecaoAnaliseCritica;

@Service
public class SecaoAnaliseCriticaService {

	@Autowired
	private SecaoService secaoService;
	
	@Autowired
	private ItemAnaliseCriticaService itemAnaliseCriticaService;
	
	public List<SecaoAnaliseCritica> construirSecoesAnaliseCritica(AnaliseCritica analiseCritica) {
		List<SecaoAnaliseCritica> secoes = new ArrayList<SecaoAnaliseCritica>();
		for (Secao secao : secaoService.listar()) {
			SecaoAnaliseCritica secaoAnaliseCritica = new SecaoAnaliseCritica();
			secaoAnaliseCritica.setSecao(secao);
			secaoAnaliseCritica.setAnaliseCritica(analiseCritica);
			secaoAnaliseCritica.setItensAnaliseCritica(itemAnaliseCriticaService.construirItensAnaliseCritica(secaoAnaliseCritica, secao));
			secoes.add(secaoAnaliseCritica);
		}
		
		return secoes;
	}

}
