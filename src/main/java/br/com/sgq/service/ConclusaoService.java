package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Conclusao;
import br.com.sgq.repository.ConclusaoRespository;

@Service
public class ConclusaoService {
	
	@Autowired
	private ConclusaoRespository conclusaoRespository;
	
	public List<Conclusao> listar() {
		return conclusaoRespository.findAll();
	}

}
