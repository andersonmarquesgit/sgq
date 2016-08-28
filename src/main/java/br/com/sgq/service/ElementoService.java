package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Elemento;
import br.com.sgq.repository.ElementoRepository;

@Service
public class ElementoService {

	@Autowired
	private ElementoRepository elementoRepository;
	
	public List<Elemento> list() {
		return elementoRepository.findAll();
	}

}
