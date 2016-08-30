package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Cidade;
import br.com.sgq.model.Estado;
import br.com.sgq.repository.CidadeRespository;
import br.com.sgq.repository.EstadoRespository;

@Service
public class EnderecoService {
	
	@Autowired
	private EstadoRespository estadoRespository;
	
	@Autowired
	private CidadeRespository cidadeRespository;
	
	public List<Estado> listarEstados() {
		return estadoRespository.findAll();
	}

	public List<Cidade> listarCidadesPorEstado(Long idEstado) {
		return cidadeRespository.listarCidadesPorEstado(idEstado);
	}
}
