package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.model.Unidade;
import br.com.sgq.repository.UnidadeRepository;
import br.com.sgq.utils.OrdenacaoUtil;

@Service
public class UnidadeService {
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	public List<Unidade> listar() {
		List<Unidade> unidades = unidadeRepository.findAll();
		OrdenacaoUtil.ordenarLista(unidades, true, "nome");
		return unidades;
	}
	
	@Transactional
	public void salvar(Unidade unidade) {
		unidadeRepository.saveAndFlush(unidade);
	}

	@Transactional
	public void remover(Unidade unidadeSelecionada) {
		unidadeRepository.delete(unidadeSelecionada);
	}
}
