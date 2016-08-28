package br.com.sgq.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.LineChartModel;
import org.chartistjsf.model.chart.LineChartSeries;
import org.chartistjsf.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Gravidade;
import br.com.sgq.model.Reclamacao;
import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.service.ReclamacaoService;
import br.com.sgq.service.TipoReclamacaoService;
import br.com.sgq.utils.DataUtil;
import br.com.sgq.utils.enums.GravidadeEnum;
import br.com.sgq.utils.enums.StatusReclamacaoEnum;

@ManagedBean
@ViewScoped
@Controller
public class ChartBean implements Serializable{

	private static final long serialVersionUID = 600753791164951038L;

	private BarChartModel chartReclamacoes;
	private PieChartModel pieChartReclamacoes;
	private LineChartModel lineChartReclamacoes;
	
	private List<Integer> listaAnos;
	private Integer anoSelecionado;
	private Integer totalDefinir;
	private Integer totalBaixa;
	private Integer totalMedia;
	private Integer totalAlta;
	
	@Autowired
	private ReclamacaoService reclamacaoService;
	
	@Autowired
	private TipoReclamacaoService tipoReclamacaoService;

	@PostConstruct
	public void init() {
		anoSelecionado = DataUtil.getAnoAtual();
		listaAnos = DataUtil.getListaAnos(anoSelecionado-4);
		criarChartReclamacoes();
		criarPieChartReclamacoes();
		criarLineChartReclamacoes();
		carregarTotaisPorStatus();
	}

	public void carregarTotaisPorStatus() {
		List<Reclamacao> reclamacoesPorMesEAno = reclamacaoService.listarPorMesEAno(DataUtil.getMesAtual(), DataUtil.getAnoAtual());
		this.totalDefinir = 0;
		this.totalBaixa = 0;
		this.totalMedia = 0;
		this.totalAlta = 0;
		
		for (Reclamacao reclamacao : reclamacoesPorMesEAno) {
			if(reclamacao.getGravidade().getId() == GravidadeEnum.BAIXA.getId()){
				totalBaixa += 1;
			}else  if(reclamacao.getGravidade().getId() == GravidadeEnum.MEDIA.getId()){
				totalMedia += 1;
			}else if(reclamacao.getGravidade().getId() == GravidadeEnum.ALTA.getId()){
				totalAlta += 1;
			}else{
				totalDefinir += 1;
			}
		}
	}

	public void selecionarAno() {
		criarChartReclamacoes();
		criarPieChartReclamacoes();
	}
	private void criarLineChartReclamacoes() {
		lineChartReclamacoes = initLineChartReclamacoes();
		lineChartReclamacoes.setAspectRatio(AspectRatio.OCTAVE);
		lineChartReclamacoes.setShowTooltip(true);
		lineChartReclamacoes.setAnimateAdvanced(true);
	}
	
	private LineChartModel initLineChartReclamacoes() {
		LineChartModel model = new LineChartModel();
		LineChartSeries totalReclamacoes = new LineChartSeries();
		totalReclamacoes.setName("Total");
		
		for (Integer ano : listaAnos){
			model.addLabel(ano);
		}
		
		for (Integer ano : listaAnos) {
			Long valor = reclamacaoService.calculaReclamacoesPorAno(ano);
			totalReclamacoes.set(valor);
		}
		
		model.addSeries(totalReclamacoes);
		return model;
	}

	public void criarChartReclamacoes() {
		chartReclamacoes = initChartReclamacoes();
		chartReclamacoes.setAspectRatio(AspectRatio.OCTAVE);
		chartReclamacoes.setShowTooltip(true);
		chartReclamacoes.setSeriesBarDistance(15);
		chartReclamacoes.setAnimateAdvanced(true);

		Axis xAxis = chartReclamacoes.getAxis(AxisType.X);
		xAxis.setShowGrid(false);
	}
	
	private BarChartModel initChartReclamacoes() {
		BarChartModel model = new BarChartModel();

		BarChartSeries analiseGravidade = new BarChartSeries();
		analiseGravidade.setName(StatusReclamacaoEnum.ANALISE_GRAVIDADE.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++){
			model.addLabel(DataUtil.MESES_DO_ANO[mes-1].substring(0, 3));
		}
			
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ANALISE_GRAVIDADE.getId());
			analiseGravidade.set(valor);
		}
		
		BarChartSeries analiseAcao = new BarChartSeries();
		analiseAcao.setName(StatusReclamacaoEnum.ACAO_TOMADA.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ACAO_TOMADA.getId());
			analiseAcao.set(valor);
		}
		
		BarChartSeries analiseAceiteCliente = new BarChartSeries();
		analiseAceiteCliente.setName(StatusReclamacaoEnum.ACEITE_CLIENTE.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ACEITE_CLIENTE.getId());
			analiseAceiteCliente.set(valor);
		}
		
		BarChartSeries concluidas = new BarChartSeries();
		concluidas.setName(StatusReclamacaoEnum.CONCLUIDA.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.CONCLUIDA.getId());
			concluidas.set(valor);
		}
		
		model.addSeries(analiseGravidade);
		model.addSeries(analiseAcao);
		model.addSeries(analiseAceiteCliente);
		model.addSeries(concluidas);
		
		return model;
	}

	public void criarPieChartReclamacoes() {
		pieChartReclamacoes = new PieChartModel();
		pieChartReclamacoes.setAspectRatio(AspectRatio.MAJOR_SEVENTH);
		List<TipoReclamacao> listTiposReclamacao = tipoReclamacaoService.list();
		
		for(TipoReclamacao tipoReclamacao : listTiposReclamacao) {
			pieChartReclamacoes.addLabel(tipoReclamacao.getDescricao());		
		}
		
		for(TipoReclamacao tipoReclamacao : listTiposReclamacao) {
			pieChartReclamacoes.set(reclamacaoService.calcularReclamacoesPorTipoNoAno(anoSelecionado, tipoReclamacao));
		}
        
		pieChartReclamacoes.setShowTooltip(true);
	}
	
	//Gets e Sets =======================================================================================================================
	public BarChartModel getChartReclamacoes() {
		return chartReclamacoes;
	}

	public void setChartReclamacoes(BarChartModel chartReclamacoes) {
		this.chartReclamacoes = chartReclamacoes;
	}

	public List<Integer> getListaAnos() {
		return listaAnos;
	}

	public void setListaAnos(List<Integer> listaAnos) {
		this.listaAnos = listaAnos;
	}

	public Integer getAnoSelecionado() {
		return anoSelecionado;
	}

	public void setAnoSelecionado(Integer anoSelecionado) {
		this.anoSelecionado = anoSelecionado;
	}

	public PieChartModel getPieChartReclamacoes() {
		return pieChartReclamacoes;
	}

	public void setPieChartReclamacoes(PieChartModel pieChartReclamacoes) {
		this.pieChartReclamacoes = pieChartReclamacoes;
	}

	public LineChartModel getLineChartReclamacoes() {
		return lineChartReclamacoes;
	}

	public void setLineChartReclamacoes(LineChartModel lineChartReclamacoes) {
		this.lineChartReclamacoes = lineChartReclamacoes;
	}

	public Integer getTotalDefinir() {
		return totalDefinir;
	}

	public void setTotalDefinir(Integer totalDefinir) {
		this.totalDefinir = totalDefinir;
	}

	public Integer getTotalBaixa() {
		return totalBaixa;
	}

	public void setTotalBaixa(Integer totalBaixa) {
		this.totalBaixa = totalBaixa;
	}

	public Integer getTotalMedia() {
		return totalMedia;
	}

	public void setTotalMedia(Integer totalMedia) {
		this.totalMedia = totalMedia;
	}

	public Integer getTotalAlta() {
		return totalAlta;
	}

	public void setTotalAlta(Integer totalAlta) {
		this.totalAlta = totalAlta;
	}

}
