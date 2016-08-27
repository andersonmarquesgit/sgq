package br.com.sgq.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.autenticacao.UserSession;
import br.com.sgq.model.Cliente;
import br.com.sgq.model.Endereco;
import br.com.sgq.model.Gravidade;
import br.com.sgq.model.Reclamacao;
import br.com.sgq.model.StatusReclamacao;
import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.service.DocumentoService;
import br.com.sgq.service.ElementoService;
import br.com.sgq.service.ReclamacaoService;
import br.com.sgq.service.StatusReclamacaoService;
import br.com.sgq.service.TipoDocumentoService;
import br.com.sgq.service.TipoReclamacaoService;
import br.com.sgq.utils.CepUtil;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;
import br.com.sgq.utils.enums.StatusReclamacaoEnum;

@ManagedBean
@ViewScoped
@Controller
public class SacController {

	private Reclamacao reclamacao;
	private Cliente cliente;
	private Endereco endereco;
	private String cep;
	private List<TipoReclamacao> tipoReclamacaoList;
	private List<Reclamacao> reclamacoes;
	private Gravidade gravidade;

	@Autowired
	private TipoReclamacaoService tipoReclamacaoService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@Autowired
	private ElementoService elementoService;

	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ReclamacaoService reclamacaoService;
	
	@Autowired
	private StatusReclamacaoService statusReclamacaoService;

	@Autowired
	private UserSession userSession;
	
	@PostConstruct
	public void init() {
		tipoReclamacaoList = tipoReclamacaoService.list();
		this.inicializarReclamacoes();
		this.inicializarObjetos();
	}

	public void inicializarObjetos() {
		reclamacao = new Reclamacao();
		cliente = new Cliente();
		endereco = new Endereco();
		gravidade = null;
		cep = "";
	}
	
	public void inicializarReclamacoes() {
		reclamacoes = reclamacaoService.listar();
	}
	
	public void salvarReclamacao() {
		if(validarCamposObrigatoriosDoCliente() || validarCamposObrigatoriosDaReclamacao()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoReclamacao').show();");
		}
	}
	
	public String confirmarInclusaoReclamacao() {
		this.primeiraEtapaDaReclamacao();
		RequestContext.getCurrentInstance().execute("PF('confirmInclusaoReclamacao').hide();");
		reclamacaoService.salvar(reclamacao);
		this.inicializarObjetos();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formReclamacoes");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		return this.redirectSac();
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetos();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().execute("PF('modalReclamacao').hide();");
	}
	
	public void analisarGravidade(Reclamacao reclamacaoSelecionada) {
		this.reclamacao = reclamacaoSelecionada;
		RequestContext.getCurrentInstance().execute("PF('modalGravidade').show();");
	}
	
	public void salvarAnaliseDeGravidade() {
		if(gravidade == null) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			this.segundaEtapaDaReclamacao();
			RequestContext.getCurrentInstance().execute("PF('modalGravidade').hide();");
			Object[] params = new Object[1];
			params[0] = this.gravidade.getDescricao();
			reclamacaoService.salvar(reclamacao);
			this.inicializarObjetos();
			this.inicializarReclamacoes();
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_GRAVIDADE, params);
		}
	}
	
	public void cancelarAnaliseDeGravidade() {
		this.inicializarObjetos();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().execute("PF('modalGravidade').hide();");
	}
	
	public void primeiraEtapaDaReclamacao() {
		this.cliente.setEndereco(endereco);
		this.reclamacao.setCliente(cliente);
		this.reclamacao.setDataInclusao(new Date());
		
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.ANALISE_GRAVIDADE.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
	}
	
	public void segundaEtapaDaReclamacao() {
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.ACAO_TOMADA.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
		this.reclamacao.setGravidade(gravidade);
	}
	
	public Boolean validarCamposObrigatoriosDoCliente() {
		if(cliente.getNome().isEmpty() ||
				cliente.getTelefone().isEmpty() ||
				cliente.getEmail().isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validarCamposObrigatoriosDaReclamacao() {
		if(reclamacao.getDescricao().isEmpty() || reclamacao.getTipoReclamacao() == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void buscarEndereco(AjaxBehaviorEvent event) {
		endereco = CepUtil.buscarEndereco(this.cep);
	}
	
	public String redirectNovaReclamacao() {
		return FacesUtil.sendRedirect("/paginas/sac/reclamacao/novaReclamacao");
	}
	
	public String redirectSac() {
		return FacesUtil.sendRedirect("/paginas/sac/sac");
	}

	public List<TipoReclamacao> getTipoReclamacaoList() {
		return tipoReclamacaoList;
	}

	public void setTipoReclamacaoList(List<TipoReclamacao> tipoReclamacaoList) {
		this.tipoReclamacaoList = tipoReclamacaoList;
	}

	public Reclamacao getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}


	public List<Reclamacao> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(List<Reclamacao> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	public Gravidade getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravidade gravidade) {
		this.gravidade = gravidade;
	}

}
