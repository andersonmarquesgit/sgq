package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Gravidade;
import br.com.sgq.service.GravidadeService;

@ManagedBean
@ViewScoped
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
