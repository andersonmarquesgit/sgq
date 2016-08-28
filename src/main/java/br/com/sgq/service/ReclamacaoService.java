package br.com.sgq.service;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.model.Reclamacao;
import br.com.sgq.model.TipoReclamacao;
import br.com.sgq.repository.ReclamacaoRepository;
import br.com.sgq.utils.Constantes;
import br.com.sgq.utils.DataUtil;
import br.com.sgq.utils.EmailUtil;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

@Service
public class ReclamacaoService {
	
//	private static final Logger LOG = Logger.getLogger(ReclamacaoService.class);
	
	@Autowired
	private ReclamacaoRepository reclamacaoRepository;

	@Transactional
	public Reclamacao salvar(Reclamacao reclamacao) {
		return reclamacaoRepository.saveAndFlush(reclamacao);
	}

	public List<Reclamacao> listar() {
		return reclamacaoRepository.findAll(orderByDataInclusao());
	}
	
	private Sort orderByDataInclusao() {
	    return new Sort(Sort.Direction.DESC, "dataInclusao");
	}

	public String construirNumeroDaReclamacao(Date dataAtual){
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");  
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		String ano = yyyy.format(dataAtual);
		String mes = mm.format(dataAtual);
		String dia = dd.format(dataAtual);
		Long qtdReclamacaoNoDia = this.buscarQtdReclamacaoNoDia(dataAtual);
		String numeroReclamacao = ano + mes + dia + this.formatarQtdReclamacaoNoDia(qtdReclamacaoNoDia);
		return numeroReclamacao;
	}

	private String formatarQtdReclamacaoNoDia(Long qtdReclamacaoNoDia) {
		return StringUtils.rightPad(qtdReclamacaoNoDia.toString(), Constantes.QTD_ZEROS_NUM_RECLAMACAO, "0");
	}
	
	private Long buscarQtdReclamacaoNoDia(Date dataAtual) {
		Long qtd = 1L;
		for (Reclamacao reclamacao : reclamacaoRepository.findAll()) {
			if(DataUtil.getDataFormatada(reclamacao.getDataInclusao(), DataUtil.DIA_MES_ANO)
					.equals(DataUtil.getDataFormatada(dataAtual, DataUtil.DIA_MES_ANO))) {
				qtd++;
			}
		}
		return qtd;
	}

	public Long calculaReclamacoesNoPeriodo(Integer mes, Integer anoSelecionado, Long statusId) {
		return reclamacaoRepository.calculaReclamacoesNoMes(mes, anoSelecionado, statusId);
	}
	
	public Long calculaReclamacoesPorAno(Integer ano) {
		return reclamacaoRepository.calculaReclamacoesPorAno(ano);
	}

	public Number calcularReclamacoesPorTipoNoAno(Integer anoSelecionado,
			TipoReclamacao tipoReclamacao) {
		return reclamacaoRepository.calcularReclamacoesPorTipoNoAno(anoSelecionado, tipoReclamacao);
	}

	public void enviarEmail(Reclamacao reclamacao) {
		EmailUtil email = new EmailUtil("Teste de envio da reclamação", 
				FacesUtil.obterTexto(MsgConstantes.EMAIL_EMPRESA) + " - " +
				FacesUtil.obterTexto(MsgConstantes.EMAIL_REGISTRO_DE_RECLAMACAO) + " Nº " + reclamacao.getNumero(), 
				reclamacao.getCliente().getEmail(), reclamacao.getCliente().getNome());
		try {
			email.enviaEmailFormatoHtml(reclamacao);
		} catch (EmailException e) {
			System.out.println(e);
//			LOG.error("Erro durante o envio do email", e);
		} catch (MalformedURLException e) {
			System.out.println(e);
//			LOG.error("Erro de formatação do email", e);
		}
		
	}

	public List<Reclamacao> listarPorMesEAno(int mesAtual, int anoAtual) {
		return reclamacaoRepository.listarPorMesEAno(mesAtual, anoAtual);
	}
}
