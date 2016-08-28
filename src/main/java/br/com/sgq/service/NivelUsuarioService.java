package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.model.NivelUsuario;
import br.com.sgq.repository.NivelUsuarioRepository;
import br.com.sgq.utils.OrdenacaoUtil;

@Service
public class NivelUsuarioService {

	@Autowired
	private NivelUsuarioRepository nivelUsuarioRepository;
	
	public List<NivelUsuario> list() {
		List<NivelUsuario> niveis = nivelUsuarioRepository.findAll();
		OrdenacaoUtil.ordenarLista(niveis, true, "dataInclusao");
		return niveis;
	}

	@Transactional
	public void salvar(NivelUsuario nivelUsuario) {
		nivelUsuarioRepository.saveAndFlush(nivelUsuario);
	}

	@Transactional
	public void remover(NivelUsuario nivelUsuarioSelecionado) {
		nivelUsuarioRepository.delete(nivelUsuarioSelecionado);
	}
}
