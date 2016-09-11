package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Gravidade;
import br.com.sgq.service.GravidadeService;

@Scope("view")
@Controller
public class GravidadeController {

	@Autowired
	private GravidadeService gravidadeService;
	
	private List<Gravidade> gravidades;
	
	@PostConstruct
	public void init() {
		gravidades = gravidadeService.listar();
	}

	public List<Gravidade> getGravidades() {
		return gravidades;
	}

	public void setGravidades(List<Gravidade> gravidades) {
		this.gravidades = gravidades;
	}
}
