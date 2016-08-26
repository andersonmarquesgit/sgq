package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Empresa;
import br.com.sgq.service.EmpresaService;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

@ManagedBean
@ViewScoped
@Controller
public class EmpresaController {
	
	private Empresa empresa;

	private Empresa empresaSelecionada;
	
	private List<Empresa> empresas;
	
	@Autowired
	private EmpresaService empresaService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarEmpresa();
		this.inicializarEmpresas();
	}
	
	private void inicializarEmpresa() {
		empresa = new Empresa();
	}
	
	public void inicializarEmpresas() {
		empresas = empresaService.listar();
	}


	public void adicionarEmpresa() {
		if(empresa.getRazaoSocial().isEmpty() || empresa.getCnpj().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoEmpresa').show();");
		}
	}
	
	public void confirmSalvarEmpresa() {
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').hide();");
		Boolean ehEdicao = (this.empresa.getId() == null);
		empresaService.salvar(empresa);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formEmpresas");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formEmpresas");
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').hide();");
	}
	
	public void excluirEmpresa() {
		empresaService.remover(this.empresaSelecionada);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formEmpresas");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmarExclusaoEmpresa(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').hide();");
	}
	
	public void editarEmpresa(Empresa empresaSelecionada) {
		this.empresa = empresaSelecionada;
		RequestContext.getCurrentInstance().update("formEmpresas");
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').show();");
	}

	//Gets e Sets ==============================================================================================
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
}
