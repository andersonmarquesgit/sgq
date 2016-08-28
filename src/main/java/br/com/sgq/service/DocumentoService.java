package br.com.sgq.service;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgq.model.Documento;
import br.com.sgq.model.TipoDocumento;
import br.com.sgq.repository.DocumentoRepository;
import br.com.sgq.repository.TipoDocumentoRepository;
import br.com.sgq.utils.FacesUtil;

@Service
public class DocumentoService {

	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	public Documento salvar(Documento documento) {
		return documentoRepository.saveAndFlush(documento);
	}

	public Documento findOn(Long id) {
		return documentoRepository.findOne(id);
	}
	
	public void visualizarDocumento(Documento documento) {
    	try {
            byte[] arquivo = documento.getConteudo();
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            //Código abaixo gerar o relatório e disponibiliza diretamente na página 
            res.setHeader("Content-disposition", "inline;filename="+ documento.getNomeArquivo());
            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
            //res.setHeader("Content-disposition", "attachment;filename=reclamacao.pdf");
            res.getOutputStream().write(arquivo);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
        	FacesUtil.adicionarErro(e.getMessage());
        }
    }
	
//	public void visualizarDocumento(Documento documento) {
//		//TODO: alterar para o documento selecionado
////    	Documento documentoVisualizar = this.findOn(19L);
//    	
//        FacesContext fc = FacesContext.getCurrentInstance();
//
//        // Obtem o HttpServletResponse, objeto responsável pela resposta do
//        // servidor ao browser
//        HttpServletResponse response = (HttpServletResponse) fc
//              .getExternalContext().getResponse();
//
//        // Limpa o buffer do response
//        response.reset();
//
//        // Seta o tipo de conteudo no cabecalho da resposta. No caso, indica que o
//        // conteudo sera um documento pdf.
//        response.setContentType("application/pdf");
//
//        // Seta o tamanho do conteudo no cabecalho da resposta. No caso, o tamanho
//        // em bytes do pdf
//        response.setContentLength(documento.getConteudo().length);
//
//        // Seta o nome do arquivo e a disposição: "inline" abre no próprio navegador
//        // Mude para "attachment" para indicar que deve ser feito um download
//        response.setHeader("Content-disposition",
//              "inline; filename=arquivo.pdf");
//        try {
//
//           // Envia o conteudo do arquivo PDF para o response
//           response.getOutputStream().write(documento.getConteudo());
//
//           // Descarrega o conteudo do stream, forçando a escrita de qualquer byte
//           // ainda em buffer
//           response.getOutputStream().flush();
//
//           // Fecha o stream, liberando seus recursos
//           response.getOutputStream().close();
//
//           // Sinaliza ao JSF que a resposta HTTP para este pedido já foi gerada
//           fc.responseComplete();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//		
//	}

	public List<Documento> list() {
		return documentoRepository.findAll();
	}

	/**
	 * A anotação @Transasional foi necessária para evitar a exceção @see PSQLException
	 * Caused by: org.postgresql.util.PSQLException: 
	 * Objetos Grandes não podem ser usados no modo de efetivação automática (auto-commit).
	 */
	@Transactional
	public List<Documento> listByTipoDocumento(Long id, Pageable pageable) {
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findOne(id);
		return documentoRepository.findByTipoDocumento(tipoDocumento, pageable);
	}
	
	@Transactional
	public Long countByTipoDocumento(Long id) {
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findOne(id);
		return documentoRepository.countByTipoDocumento(tipoDocumento);
	}

	@Transactional
	public List<Documento> listByTipoDocumento(Long id) {
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findOne(id);
		return documentoRepository.listByTipoDocumento(tipoDocumento);
	}
	
}
