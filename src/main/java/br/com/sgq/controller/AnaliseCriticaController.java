/**
 * 
 */
package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.AnaliseCritica;
import br.com.sgq.model.Conclusao;
import br.com.sgq.model.ItemAnaliseCritica;
import br.com.sgq.model.Resultado;
import br.com.sgq.model.SecaoAnaliseCritica;
import br.com.sgq.service.AnaliseCriticaService;
import br.com.sgq.service.ConclusaoService;
import br.com.sgq.service.ResultadoService;
import br.com.sgq.utils.DataUtil;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

/**
 * @author Anderson
 *
 */
@ViewScoped
@Controller
public class AnaliseCriticaController {
	
	private List<AnaliseCritica> analises;
	
	@Autowired
	private AnaliseCriticaService analiseCriticaService;
	
	@Autowired
	private ResultadoService resultadoService;
	
	@Autowired
	private ConclusaoService conclusaoService;
	
	private AnaliseCritica analiseCritica;
	
	private List<Resultado> resultados;
	
	private List<Conclusao> conclusoes;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}
	
	private void inicializarObjetosDaTela() {
		this.inicializarAnalises();
		this.inicializarAnaliseCritica();
		this.inicializarResultados();
		this.inicializarConclusoes();
	}
	
	private void inicializarAnalises() {
		analises = analiseCriticaService.listar();
	}
	
	private void inicializarAnaliseCritica() {
		analiseCritica = analiseCriticaService.inicializarNovaAnaliseCritica();
	}
	
	private void inicializarResultados() {
		resultados = resultadoService.listar();
	}
	
	private void inicializarConclusoes() {
		conclusoes = conclusaoService.listar();
	}
	
	public String redirectNovaAnaliseCritica() {
		return FacesUtil.sendRedirect("/paginas/analisecritica/novaAnaliseCritica");
	}

	public void adicionarAnaliseCritica() {
		if(validarCamposObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').show();");
		}
	}
	
	/**
	 * @return Redirect para a página principal de Análise Crítica. 
	 */
	public String confirmSalvarAnaliseCritica() {
		RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').hide();");
		this.analiseCritica.setDataInclusao(DataUtil.getDataAtual());
		this.analiseCritica.setNumero(analiseCriticaService.construirNumeroDaAnaliseCritica(DataUtil.getDataAtual()));
		analiseCriticaService.salvar(analiseCritica);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formAnaliseCritica");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		return this.redirectAnaliseCritica();
	}
	
	public Boolean validarCamposObrigatorios() {
		Boolean existeCompoNulo = false;
		for (SecaoAnaliseCritica secaoAc : analiseCritica.getSecoesAnaliseCritica()) {
			if(secaoAc.getConclusao() == null
					|| secaoAc.getReferencias().isEmpty()
					|| secaoAc.getReferencias() == null) {
				existeCompoNulo = true;
			}
			
			for (ItemAnaliseCritica itemAc : secaoAc.getItensAnaliseCritica()) {
				if(itemAc.getResultado() == null) {
					existeCompoNulo = true;
				}
			}
		}
		
		return existeCompoNulo;
	}
	
	public String redirectAnaliseCritica() {
		return FacesUtil.sendRedirect("/paginas/analisecritica/analiseCritica");
	}
	
	public String cancelarNovaAnaliseCritica() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formAnaliseCritica");
		return this.redirectAnaliseCritica();
	}
	
	public List<AnaliseCritica> getAnalises() {
		return analises;
	}

	public void setAnalises(List<AnaliseCritica> analises) {
		this.analises = analises;
	}

	public AnaliseCritica getAnaliseCritica() {
		return analiseCritica;
	}

	public void setAnaliseCritica(AnaliseCritica analiseCritica) {
		this.analiseCritica = analiseCritica;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public List<Conclusao> getConclusoes() {
		return conclusoes;
	}

	public void setConclusoes(List<Conclusao> conclusoes) {
		this.conclusoes = conclusoes;
	}

}
