package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Resultado;
import br.com.sgq.repository.ResultadoRepository;

@Service
public class ResultadoService {
	
	@Autowired
	private ResultadoRepository resultadoRepository;
	
	public List<Resultado> listar() {
		return resultadoRepository.findAll();
	}

}
