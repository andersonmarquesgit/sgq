package br.com.sgq.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.filtros.DocumentoLazyList;
import br.com.sgq.model.Documento;
import br.com.sgq.model.Elemento;
import br.com.sgq.model.TipoDocumento;
import br.com.sgq.service.DocumentoService;
import br.com.sgq.service.ElementoService;
import br.com.sgq.service.TipoDocumentoService;
import br.com.sgq.utils.enums.TipoDocumentoEnum;

@ManagedBean
@ViewScoped
@Controller
public class DocumentoController {

	private Part arquivo;
	private Documento documento;
	private LazyDataModel<Documento> documentosProcedimentos;
	private LazyDataModel<Documento> documentosPoliticas;
	private LazyDataModel<Documento> documentosTreinamentos;
	private LazyDataModel<Documento> documentosDesignacoes;
	private LazyDataModel<Documento> documentosExternos;
	private List<TipoDocumento> tiposDeDocumentos;
	private List<Elemento> elementos;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@Autowired
	private ElementoService elementoService;

	@Autowired
	private DocumentoService documentoService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetos();
	}

	public void inicializarObjetos() {
		documentosProcedimentos = new DocumentoLazyList(TipoDocumentoEnum.PROCEDIMENTO.getId(), documentoService);
		documentosPoliticas = new DocumentoLazyList(TipoDocumentoEnum.POLITICA_TRATAMENTO_RECLAMACAO.getId(), documentoService);
		documentosTreinamentos = new DocumentoLazyList(TipoDocumentoEnum.TREINAMENTO.getId(), documentoService);
		documentosDesignacoes = new DocumentoLazyList(TipoDocumentoEnum.DESIGNACAO.getId(), documentoService);
		documentosExternos = new DocumentoLazyList(TipoDocumentoEnum.DOCUMENTOS_COMPLEMENTARES.getId(), documentoService);
		elementos = elementoService.list();
		tiposDeDocumentos = tipoDocumentoService.list();
		documento = new Documento();
	}
	
	public LazyDataModel<Documento> listDocumentosProcedimentos(){
		return new DocumentoLazyList(TipoDocumentoEnum.PROCEDIMENTO.getId(), documentoService);
	}
	
	public LazyDataModel<Documento> listDocumentosPoliticas(){
		return new DocumentoLazyList(TipoDocumentoEnum.POLITICA_TRATAMENTO_RECLAMACAO.getId(), documentoService);
	}
	
	public LazyDataModel<Documento> listDocumentosTreinamentos(){
		return new DocumentoLazyList(TipoDocumentoEnum.TREINAMENTO.getId(), documentoService);
	}
	
	public LazyDataModel<Documento> listDocumentosDesignacoes(){
		return new DocumentoLazyList(TipoDocumentoEnum.DESIGNACAO.getId(), documentoService);
	}
	
	public LazyDataModel<Documento> listDocumentosExternos(){
		return new DocumentoLazyList(TipoDocumentoEnum.DOCUMENTOS_COMPLEMENTARES.getId(), documentoService);
	}
	
	public String uploadDocumento() throws IOException {
		InputStream inputStream = arquivo.getInputStream();
		byte[] conteudo = new byte[inputStream.available()];
		inputStream.read(conteudo);
		inputStream.close();
		documento.setConteudo(conteudo);
		documento.setNomeArquivo(getFileName(arquivo));
		documentoService.salvar(documento);
		this.inicializarObjetos();

		return "success";
	}

	public void cancelarInclusao() {
		this.inicializarObjetos();
		RequestContext.getCurrentInstance().update("formDocumentosInternos");
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').hide();");
	}
	
	public void visualizarDocumento(Documento documentoSelecionado) {
		documentoService.visualizarDocumento(documentoSelecionado);
	}

	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.indexOf('/') + 1).substring(
						filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}


	public List<TipoDocumento> getTiposDeDocumentos() {
		return tiposDeDocumentos;
	}

	public void setTiposDeDocumentos(List<TipoDocumento> tiposDeDocumentos) {
		this.tiposDeDocumentos = tiposDeDocumentos;
	}

	public List<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public LazyDataModel<Documento> getDocumentosProcedimentos() {
		return documentosProcedimentos;
	}

	public void setDocumentosProcedimentos(
			LazyDataModel<Documento> documentosProcedimentos) {
		this.documentosProcedimentos = documentosProcedimentos;
	}

	public LazyDataModel<Documento> getDocumentosPoliticas() {
		return documentosPoliticas;
	}

	public void setDocumentosPoliticas(LazyDataModel<Documento> documentosPoliticas) {
		this.documentosPoliticas = documentosPoliticas;
	}

	public LazyDataModel<Documento> getDocumentosTreinamentos() {
		return documentosTreinamentos;
	}

	public void setDocumentosTreinamentos(
			LazyDataModel<Documento> documentosTreinamentos) {
		this.documentosTreinamentos = documentosTreinamentos;
	}

	public LazyDataModel<Documento> getDocumentosDesignacoes() {
		return documentosDesignacoes;
	}

	public void setDocumentosDesignacoes(
			LazyDataModel<Documento> documentosDesignacoes) {
		this.documentosDesignacoes = documentosDesignacoes;
	}

	public LazyDataModel<Documento> getDocumentosExternos() {
		return documentosExternos;
	}

	public void setDocumentosExternos(LazyDataModel<Documento> documentosExternos) {
		this.documentosExternos = documentosExternos;
	}


}
