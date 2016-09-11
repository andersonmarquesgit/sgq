package br.com.sgq.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

import org.apache.commons.lang.time.DateUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.autenticacao.UserSession;
import br.com.sgq.model.AcaoTomada;
import br.com.sgq.model.AceiteCliente;
import br.com.sgq.model.Cidade;
import br.com.sgq.model.Cliente;
import br.com.sgq.model.Endereco;
import br.com.sgq.model.Estado;
import br.com.sgq.model.Gravidade;
import br.com.sgq.model.Reclamacao;
import br.com.sgq.model.StatusReclamacao;
import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.service.EnderecoService;
import br.com.sgq.service.ReclamacaoService;
import br.com.sgq.service.StatusReclamacaoService;
import br.com.sgq.service.TipoReclamacaoService;
import br.com.sgq.utils.CepUtil;
import br.com.sgq.utils.Constantes;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;
import br.com.sgq.utils.enums.StatusReclamacaoEnum;

@Scope("view")
@Controller
public class ReclamacaoController {

	private static final long serialVersionUID = -7580482456782445297L;

	private List<Reclamacao> reclamacoes;
	private List<Reclamacao> reclamacoesFiltradas;
	private Reclamacao reclamacao;
	private Reclamacao reclamacaoSelecionada;
	private Cliente cliente;
	private Endereco endereco;
	private List<TipoReclamacao> tipoReclamacaoList;
	private Gravidade gravidade;
	private Gravidade complexidade;
	private AcaoTomada acaoTomada;
	private AceiteCliente aceiteCliente;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private Estado estado;
	
	@Autowired
	private TipoReclamacaoService tipoReclamacaoService;

	@Autowired
	private ReclamacaoService reclamacaoService;

	@Autowired
	private StatusReclamacaoService statusReclamacaoService;

	@Autowired
	private UserSession userSession;

	@Autowired
	private ChartBean chartBean;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		cliente = new Cliente();
		endereco = new Endereco();
		gravidade = null;
		complexidade = null;
		tipoReclamacaoList = tipoReclamacaoService.list();
		this.inicializarReclamacao();
		this.inicializarReclamacoes();
		this.inicializarAcaoTomada();
		this.inicializarAceiteCliente();
		this.inicializarEstados();
	}

	private void inicializarReclamacao() {
		reclamacao = new Reclamacao();
	}

	private void inicializarReclamacoes() {
		reclamacoes = reclamacaoService.listar();
	}

	private void inicializarAcaoTomada() {
		acaoTomada = new AcaoTomada();
		acaoTomada.setProcede(true);
	}

	private void inicializarAceiteCliente() {
		aceiteCliente = new AceiteCliente();
	}

	private void inicializarEstados() {
		estados = enderecoService.listarEstados();
	}
	
	public void adicionarReclamacao() {
		if (validarCamposObrigatoriosDoCliente()
				|| validarCamposObrigatoriosDaReclamacao()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmInclusaoReclamacao').show();");
		}
	}

	public Boolean validarCamposObrigatoriosDoCliente() {
		if (cliente.getNome().isEmpty() || cliente.getTelefone().isEmpty()
				|| cliente.getEmail().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean validarCamposObrigatoriosDaReclamacao() {
		if (reclamacao.getDescricao().isEmpty()
				|| reclamacao.getTipoReclamacao() == null
				|| reclamacao.getDataPrazoResposta() == null
				|| reclamacao.getGravidade() == null
				|| reclamacao.getComplexidade() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Redirect para a página principal de Sac. Método responsável pela
	 *         primeira etapa da Reclamação {@link Reclamacao}, ou seja, o
	 *         cadastro inicial
	 */
	public String confirmSalvarReclamacao() {
		this.primeiraEtapaDaReclamacao();
		RequestContext.getCurrentInstance().execute(
				"PF('confirmInclusaoReclamacao').hide();");
		reclamacaoService.salvar(reclamacao);
		reclamacaoService.enviarEmail(this.reclamacao);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formReclamacoes");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		this.atualizarDadosDoDashboard();
		return this.redirectSac();
	}
	
	private void atualizarDadosDoDashboard(){
		chartBean.carregarTotaisPorStatus();
		chartBean.criarChartReclamacoes();
		chartBean.criarPieChartReclamacoes();
	}

	public String cancelarNovaReclamacao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		return this.redirectSac();
	}

	public void primeiraEtapaDaReclamacao() {
		Date dataAtual = new Date();
		this.cliente.setEndereco(endereco);
		this.reclamacao.setCliente(cliente);
		this.reclamacao.setDataInclusao(dataAtual);
		this.reclamacao.setNumero(reclamacaoService
				.construirNumeroDaReclamacao(dataAtual));
		this.reclamacao.setUsuario(userSession.obterUsuarioLogado());

		// StatusReclamacao statusReclamacao = statusReclamacaoService
		// .findOn(StatusReclamacaoEnum.ANALISE_GRAVIDADE.getId());
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.ACAO_TOMADA.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
	}

	public void adicionarGravidade(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').show();");
	}

	public void salvarAnaliseDeGravidade() {
		if (validarCamposObrigatoriosGravidade()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmGravidadeReclamacao').show();");
		}
	}

	/**
	 * Método responsável pela segunda etapa da Reclamação {@link Reclamacao},
	 * ou seja, a realização da análise da gravidade {@link Gravidade}
	 */
	public void confirmGravidadeReclamacao() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').hide();");
		this.segundaEtapaDaReclamacao();
		Object[] params = new Object[1];
		params[0] = this.gravidade.getDescricao();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_GRAVIDADE,
				params);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalGravidade");
	}

	public void cancelarAnaliseDeGravidade() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalGravidade");
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').hide();");

	}

	public void segundaEtapaDaReclamacao() {
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.ACAO_TOMADA.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
		this.reclamacao.setGravidade(gravidade);
		this.reclamacao.setComplexidade(complexidade);
	}

	public Boolean validarCamposObrigatoriosGravidade() {
		return (this.gravidade == null || this.complexidade == null);
	}

	public void analisarAcao(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').show();");
		RequestContext.getCurrentInstance().update("numeroReclamacao");
	}

	public void salvarAnaliseDeAcao() {
		if (validarCamposObrigatoriosAcao()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAcaoReclamacao').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAcao() {
		return (this.acaoTomada.getProcede() == null
				|| this.acaoTomada.getDescricao() == null || this.acaoTomada
					.getDescricao() == "");
	}

	/**
	 * Método responsável pela terceira etapa da Reclamação {@link Reclamacao},
	 * ou seja, a realização da análise da ação a ser tomada {@link Gravidade}
	 */
	public void confirmAcaoReclamacao() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').hide();");
		this.terceiraEtapaDaReclamacao();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACAO);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalAcaoTomada");
	}

	public void cancelarAnaliseDeAcao() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalAcaoTomada");
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').hide();");
	}

	public void terceiraEtapaDaReclamacao() {
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.ACEITE_CLIENTE.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
		this.reclamacao.setAcaoTomada(acaoTomada);
	}

	public void analisarAceite(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalAceiteCliente').show();");
	}

	public void salvarAnaliseDeAceite() {
		if (validarCamposObrigatoriosAceite()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAceiteReclamacao').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAceite() {
		Boolean descricaoObrigatoria = false;
		if (this.aceiteCliente.getAceite() == false) {
			descricaoObrigatoria = this.aceiteCliente.getDescricao() == null
					|| this.aceiteCliente.getDescricao().isEmpty();
		}

		return (this.aceiteCliente.getAceite() == null || descricaoObrigatoria);
	}

	/**
	 * Método responsável pela quarta etapa da Reclamação {@link Reclamacao}, ou
	 * seja, a realização da análise do aceite do cliente {@link AceiteCliente}
	 */
	public void confirmAceiteReclamacao() {
		RequestContext.getCurrentInstance().execute("PF('modalAceiteCliente').hide();");
		this.quartaEtapaDaReclamacao();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACEITE);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalAceiteCliente");
	}

	public void cancelarAnaliseDeAceite() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalAceiteCliente");
		RequestContext.getCurrentInstance().execute(
				"PF('modalAceiteCliente').hide();");
	}

	public void quartaEtapaDaReclamacao() {
		StatusReclamacao statusReclamacao = statusReclamacaoService
				.findOn(StatusReclamacaoEnum.CONCLUIDA.getId());
		this.reclamacao.setStatusReclamacao(statusReclamacao);
		this.reclamacao.setAceiteCliente(aceiteCliente);
	}

	public String redirectNovaReclamacao() {
		return FacesUtil.sendRedirect("/paginas/sac/reclamacao/novaReclamacao");
	}

	public String redirectSac() {
		return FacesUtil.sendRedirect("/paginas/sac/sac");
	}

	public void listarCidadesPorEstado() {
		cidades = enderecoService.listarCidadesPorEstado(this.estado.getId());
	}
	
	public boolean filterByData(Object value, Object filter, Locale locale) {
		if (filter == null) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return DateUtils.truncatedEquals((Date) filter, (Date) value,
				Calendar.DATE);
	}
	
	public String getRemediacaoSugerida(){
		return this.verificarTextoNA(this.reclamacao.getRemediacaoSugerida());
	}
	
	public String getDescricao(){
		return this.verificarTextoNA(this.reclamacao.getDescricao());
	}
	
	public String verificarTextoNA(String texto) {
		if(texto == null || texto.isEmpty()) {
			return Constantes.NA;
		}
		
		return texto;
	}

	// Gets e Sets
	// ==============================================================================================
	public List<Reclamacao> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(List<Reclamacao> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	public Reclamacao getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
	}

	public Reclamacao getReclamacaoSelecionada() {
		return reclamacaoSelecionada;
	}

	public void setReclamacaoSelecionada(Reclamacao reclamacaoSelecionada) {
		this.reclamacaoSelecionada = reclamacaoSelecionada;
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

	public List<TipoReclamacao> getTipoReclamacaoList() {
		return tipoReclamacaoList;
	}

	public void setTipoReclamacaoList(List<TipoReclamacao> tipoReclamacaoList) {
		this.tipoReclamacaoList = tipoReclamacaoList;
	}

	public Gravidade getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravidade gravidade) {
		this.gravidade = gravidade;
	}

	public Gravidade getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(Gravidade complexidade) {
		this.complexidade = complexidade;
	}

	public AcaoTomada getAcaoTomada() {
		return acaoTomada;
	}

	public void setAcaoTomada(AcaoTomada acaoTomada) {
		this.acaoTomada = acaoTomada;
	}

	public AceiteCliente getAceiteCliente() {
		return aceiteCliente;
	}

	public void setAceiteCliente(AceiteCliente aceiteCliente) {
		this.aceiteCliente = aceiteCliente;
	}

	public List<Reclamacao> getReclamacoesFiltradas() {
		return reclamacoesFiltradas;
	}

	public void setReclamacoesFiltradas(List<Reclamacao> reclamacoesFiltradas) {
		this.reclamacoesFiltradas = reclamacoesFiltradas;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
