package br.com.sgq.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.service.ReclamacaoService;
import br.com.sgq.service.TipoReclamacaoService;
import br.com.sgq.utils.DataUtil;
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
	private Long maxValueChart = 0L;
	private Long maxValueLineChart = 0L;
	
	@Inject
	private ReclamacaoService reclamacaoService;
	
	@Inject
	private TipoReclamacaoService tipoReclamacaoService;

	@PostConstruct
	public void init() {
		anoSelecionado = DataUtil.getAnoAtual();
		listaAnos = DataUtil.getListaAnos(anoSelecionado-4);
		criarChartReclamacoes();
		criarPieChartReclamacoes();
		criarLineChartReclamacoes();
	}

	public void selecionarAno() {
		criarChartReclamacoes();
		criarPieChartReclamacoes();
	}
	private void criarLineChartReclamacoes() {
		lineChartReclamacoes = initLineChartReclamacoes();
		
		lineChartReclamacoes.setTitle("Total de reclamações por ano");
		lineChartReclamacoes.setAnimate(true);
		lineChartReclamacoes.setShadow(true);
		lineChartReclamacoes.setShowDatatip(true);
		lineChartReclamacoes.setShowPointLabels(true);

		Axis xAxis = new CategoryAxis();
		lineChartReclamacoes.getAxes().put(AxisType.X, xAxis);

		Axis yAxis = lineChartReclamacoes.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(this.maxValueLineChart>10?this.maxValueLineChart+2:10);
		yAxis.setTickInterval("1");
	}
	
	private LineChartModel initLineChartReclamacoes() {
		LineChartModel model = new LineChartModel();
		
		ChartSeries totalReclamacoes = new ChartSeries();
		for (Integer ano : listaAnos) {
			Long valor = reclamacaoService.calculaReclamacoesPorAno(ano);
			setMaxValueLineChart(valor);
			totalReclamacoes.set(ano, valor);
		}
		
		model.addSeries(totalReclamacoes);
		return model;
	}

	public void criarChartReclamacoes() {
		chartReclamacoes = initChartReclamacoes();
		chartReclamacoes.setTitle("Reclamações de " + anoSelecionado);
		chartReclamacoes.setAnimate(true);
		chartReclamacoes.setLegendPosition("ne");
		chartReclamacoes.setShadow(true);
		chartReclamacoes.setShowDatatip(true);
		chartReclamacoes.setShowPointLabels(true);

		Axis xAxis = new CategoryAxis();
		chartReclamacoes.getAxes().put(AxisType.X, xAxis);

		Axis yAxis = chartReclamacoes.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade de Reclamações");
		yAxis.setMin(0);
		yAxis.setMax(this.maxValueChart>10?this.maxValueChart+2:10);
		yAxis.setTickInterval("1");
	}
	
	private BarChartModel initChartReclamacoes() {
		BarChartModel model = new BarChartModel();

		ChartSeries analiseGravidade = new ChartSeries(StatusReclamacaoEnum.ANALISE_GRAVIDADE.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ANALISE_GRAVIDADE.getId());
			setMaxValueChart(valor);
			analiseGravidade.set(DataUtil.MESES_DO_ANO[mes-1], valor);
		}
		
		ChartSeries analiseAcao = new ChartSeries(StatusReclamacaoEnum.ACAO_TOMADA.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ACAO_TOMADA.getId());
			setMaxValueChart(valor);
			analiseAcao.set(DataUtil.MESES_DO_ANO[mes-1], valor);
		}
		
		ChartSeries analiseAceiteCliente = new ChartSeries(StatusReclamacaoEnum.ACEITE_CLIENTE.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.ACEITE_CLIENTE.getId());
			setMaxValueChart(valor);
			analiseAceiteCliente.set(DataUtil.MESES_DO_ANO[mes-1], valor);
		}
		
		ChartSeries concluidas = new ChartSeries(StatusReclamacaoEnum.CONCLUIDA.getValue());
		
		for (Integer mes = 1; mes <= DataUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					StatusReclamacaoEnum.CONCLUIDA.getId());
			setMaxValueChart(valor);
			concluidas.set(DataUtil.MESES_DO_ANO[mes-1], valor);
		}
		
		model.addSeries(analiseGravidade);
		model.addSeries(analiseAcao);
		model.addSeries(analiseAceiteCliente);
		model.addSeries(concluidas);
		
		return model;
	}

	public void criarPieChartReclamacoes() {
		pieChartReclamacoes = new PieChartModel();
		
		for(TipoReclamacao tipoReclamacao : tipoReclamacaoService.list()) {
			pieChartReclamacoes.set(tipoReclamacao.getDescricao(), reclamacaoService.calcularReclamacoesPorTipoNoAno(anoSelecionado, tipoReclamacao));
		}
        
		pieChartReclamacoes.setTitle("Principais motivos");
		pieChartReclamacoes.setLegendPosition("w");
		pieChartReclamacoes.setShowDataLabels(true);
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

	public Long getMaxValueChart() {
		return maxValueChart;
	}

	public void setMaxValueChart(Long valor) {
		if(valor > getMaxValueChart()){
			this.maxValueChart = valor;
		}
	}

	public Long getMaxValueLineChart() {
		return maxValueLineChart;
	}

	public void setMaxValueLineChart(Long valor) {
		if(valor > getMaxValueLineChart()){
			this.maxValueLineChart = valor;
		}
	}

}
