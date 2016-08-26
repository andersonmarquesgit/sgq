package br.com.sgq.controller;

import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Controller;
import br.com.sgq.model.Reclamacao;
import br.com.sgq.utils.Constantes;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.report.ReportUtil;

@Controller
public class RelatorioController {
    
    public StreamedContent gerarArquivoReclamacao(Reclamacao reclamacao) {
		StreamedContent arquivoReclamacao;
        FacesContext context = FacesContext.getCurrentInstance();
        HashMap parametrosRelatorio = new HashMap();
        parametrosRelatorio.put("ID_RECLAMACAO", reclamacao.getId());
        try {
            arquivoReclamacao = ReportUtil.gerarStreamedRelatorio(parametrosRelatorio, Constantes.CAMINHO_RELATORIO_RECLAMACAO);
        } catch (Exception e) {
            FacesUtil.adicionarErro(e.getMessage());
            return null;
        }         
        return arquivoReclamacao;
    }    
   
    public void imprimirReclamacao(Reclamacao reclamacao) {
    	try {
            HashMap parametros = new HashMap();
            parametros.put("ID_RECLAMACAO", reclamacao.getId());
            byte[] arquivo = ReportUtil.gerarBytesRelatorio(parametros, Constantes.CAMINHO_RELATORIO_RECLAMACAO);
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            //Código abaixo gerar o relatório e disponibiliza diretamente na página 
            res.setHeader("Content-disposition", "inline;filename=reclamacao"+ reclamacao.getNumero() +".pdf");
            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
            //res.setHeader("Content-disposition", "attachment;filename=reclamacao.pdf");
            res.getOutputStream().write(arquivo);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
        	FacesUtil.adicionarErro(e.getMessage());
        }
    }
}
