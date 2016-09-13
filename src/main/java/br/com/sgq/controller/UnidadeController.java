package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Unidade;
import br.com.sgq.service.UnidadeService;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

@Scope("view")
@Controller
public class UnidadeController {
	private Unidade unidade;

	private Unidade unidadeSelecionada;
	
	private List<Unidade> unidades;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarUnidade();
		this.inicializarUnidades();
	}
	
	private void inicializarUnidade() {
		unidade = new Unidade();
	}
	
	public void inicializarUnidades() {
		unidades = unidadeService.listar();
	}
	
	public void adicionarUnidade() {
		if(unidade.getNome().isEmpty() || unidade.getEmpresa() == null) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUnidade').show();");
		}
	}
	
	public void confirmSalvarUnidade() {
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
		Boolean ehEdicao = (this.unidade.getId() == null);
		unidadeService.salvar(unidade);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
	}
	
	public void excluirUnidade() {
		unidadeService.remover(this.unidadeSelecionada);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmarExclusaoUnidade(Unidade unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').hide();");
	}
	
	public void editarUnidade(Unidade unidadeSelecionada) {
		this.unidade = unidadeSelecionada;
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().update("selectEmpresa");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').show();");
	}

	//Gets e Sets ==============================================================================================
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Unidade getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(Unidade unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

}
