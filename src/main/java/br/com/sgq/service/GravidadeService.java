package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Gravidade;
import br.com.sgq.repository.GravidadeRepository;
import br.com.sgq.utils.OrdenacaoUtil;

@Service
public class GravidadeService {

	@Autowired
	private GravidadeRepository gravidadeRepository;
	
	public List<Gravidade> listar() {
		List<Gravidade> gravidades = gravidadeRepository.findAll();
		OrdenacaoUtil.ordenarLista(gravidades, false, "id");
		return gravidades;
	}
}
