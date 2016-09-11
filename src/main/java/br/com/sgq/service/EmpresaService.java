package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.exceptions.NegocioExcecao;
import br.com.sgq.model.Empresa;
import br.com.sgq.repository.EmpresaRepository;
import br.com.sgq.utils.OrdenacaoUtil;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public List<Empresa> listar() {
		List<Empresa> empresas = empresaRepository.findAll();
		OrdenacaoUtil.ordenarLista(empresas, true, "razaoSocial");
		return empresas;
	}
	
	@Transactional
	public void salvar(Empresa empresa) {
		empresaRepository.saveAndFlush(empresa);
	}

	@Transactional
	public void remover(Empresa empresaSelecionada) {
		empresaRepository.delete(empresaSelecionada);
	}
}
