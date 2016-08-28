package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.TipoDocumento;
import br.com.sgq.repository.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	public List<TipoDocumento> list() {
		return tipoDocumentoRepository.findAll();
	}

}
