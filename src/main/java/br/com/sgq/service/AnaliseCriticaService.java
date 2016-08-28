package br.com.sgq.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.model.AnaliseCritica;
import br.com.sgq.repository.AnaliseCriticaRepository;
import br.com.sgq.utils.Constantes;
import br.com.sgq.utils.DataUtil;

@Service
public class AnaliseCriticaService {

	@Autowired
	private AnaliseCriticaRepository analiseCriticaRepository;
	
	@Autowired
	private SecaoAnaliseCriticaService secaoAnaliseCriticaService;
	
	@Transactional
	public AnaliseCritica salvar(AnaliseCritica analiseCritica) {
		return analiseCriticaRepository.saveAndFlush(analiseCritica);
	}

	public List<AnaliseCritica> listar() {
		return analiseCriticaRepository.findAll(orderByDataInclusao());
	}
	
	private Sort orderByDataInclusao() {
	    return new Sort(Sort.Direction.DESC, "dataInclusao");
	}

	public AnaliseCritica inicializarNovaAnaliseCritica() {
		AnaliseCritica analiseCritica = new AnaliseCritica();
		analiseCritica.setSecoesAnaliseCritica(secaoAnaliseCriticaService.construirSecoesAnaliseCritica(analiseCritica));
		return analiseCritica;
	}
	
	public String construirNumeroDaAnaliseCritica(Date dataAtual){
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");  
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		String ano = yyyy.format(dataAtual);
		String mes = mm.format(dataAtual);
		String dia = dd.format(dataAtual);
		Long qtdAnaliseCriticaNoDia = this.buscarQtdAnaliseCriticaNoDia(dataAtual);
		String numeroAnaliseCritica = ano + mes + dia + this.formatarQtdAnaliseCriticaNoDia(qtdAnaliseCriticaNoDia);
		return numeroAnaliseCritica;
	}

	private String formatarQtdAnaliseCriticaNoDia(Long qtdAnaliseCriticaNoDia) {
		return StringUtils.rightPad(qtdAnaliseCriticaNoDia.toString(), Constantes.QTD_ZEROS_NUM_ANALISE_CRITICA, "0");
	}
	
	private Long buscarQtdAnaliseCriticaNoDia(Date dataAtual) {
		Long qtd = 1L;
		for (AnaliseCritica analiseCritica : analiseCriticaRepository.findAll()) {
			if(DataUtil.getDataFormatada(analiseCritica.getDataInclusao(), DataUtil.DIA_MES_ANO)
					.equals(DataUtil.getDataFormatada(dataAtual, DataUtil.DIA_MES_ANO))) {
				qtd++;
			}
		}
		return qtd;
	}
}
