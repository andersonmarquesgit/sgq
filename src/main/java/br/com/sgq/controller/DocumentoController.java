package br.com.sgq.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.filtros.DocumentoLazyList;
import br.com.sgq.model.Documento;
import br.com.sgq.model.Elemento;
import br.com.sgq.model.TipoDocumento;
import br.com.sgq.service.DocumentoService;
import br.com.sgq.service.ElementoService;
import br.com.sgq.service.TipoDocumentoService;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;
import br.com.sgq.utils.enums.TipoDocumentoEnum;

@Scope("session")
@Controller
public class DocumentoController {

	private Documento documento;
	private Documento documentoSelecionado;
	private LazyDataModel<Documento> documentosProcedimentos;
	private LazyDataModel<Documento> documentosPoliticas;
	private LazyDataModel<Documento> documentosTreinamentos;
	private LazyDataModel<Documento> documentosDesignacoes;
	private LazyDataModel<Documento> documentosExternos;
	private List<TipoDocumento> tiposDeDocumentos;
	private List<TipoDocumento> tiposDeDocumentosExternos;
	private List<Elemento> elementos;
	private StreamedContent streamedContent;
    private InputStream stream;
    
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
		inicializarDocumentosInternos();
		inicializarDocumentosExternos();
		inicializarElementosENovoDoc();
	}

	private void inicializarElementosENovoDoc() {
		elementos = elementoService.list();
		documento = new Documento();
	}

	private void inicializarDocumentosExternos() {
		documentosExternos = new DocumentoLazyList(TipoDocumentoEnum.DOCUMENTOS_COMPLEMENTARES.getId(), documentoService);
		tiposDeDocumentosExternos = tipoDocumentoService.findById(TipoDocumentoEnum.DOCUMENTOS_COMPLEMENTARES.getId());
	}

	private void inicializarDocumentosInternos() {
		tiposDeDocumentos = tipoDocumentoService.list();
		documentosProcedimentos = new DocumentoLazyList(TipoDocumentoEnum.PROCEDIMENTO.getId(), documentoService);
		documentosPoliticas = new DocumentoLazyList(TipoDocumentoEnum.POLITICA_TRATAMENTO_RECLAMACAO.getId(), documentoService);
		documentosTreinamentos = new DocumentoLazyList(TipoDocumentoEnum.TREINAMENTO.getId(), documentoService);
		documentosDesignacoes = new DocumentoLazyList(TipoDocumentoEnum.DESIGNACAO.getId(), documentoService);
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
	
	public void adicionarDocExterno() {
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').show();");
	}
	
	public void cancelarEnvioDocExterno() {
		this.inicializarDocumentosExternos();
		this.inicializarElementosENovoDoc();
		RequestContext.getCurrentInstance().update("modalAddDocumento");
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').hide();");
	}
	
	public void adicionarDocInterno() {
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').show();");
	}
	
	public void cancelarEnvioDocInterno() {
		this.inicializarDocumentosInternos();
		this.inicializarElementosENovoDoc();
		RequestContext.getCurrentInstance().update("formAddDocumentoInterno");
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').hide();");
	}
	
	public void visualizarDocumento(Documento documentoSelecionado) {
		documentoService.visualizarDocumento(documentoSelecionado);
	}
	
	public void uploadFile(FileUploadEvent event)throws IOException{
		InputStream inputStream = event.getFile().getInputstream();
		byte[] conteudo = new byte[inputStream.available()];
		inputStream.read(conteudo);
		inputStream.close();
		documento.setConteudo(conteudo);
		documento.setNomeArquivo(event.getFile().getFileName());
	}
	
	public void confirmarInclusaoDocExterno(){
		if(camposObrigatoriosPreenchidos()) {
			documentoService.salvar(documento);
			this.inicializarDocumentosExternos();
			this.inicializarElementosENovoDoc();
			RequestContext.getCurrentInstance().update("modalAddDocumento");
			RequestContext.getCurrentInstance().update("formDocumentosExternos");
			RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').hide();");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_DOCUMENTO_EXTERNO);
		}else {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}
	}
	
	public void confirmarInclusaoDocInterno(){
		if(camposObrigatoriosPreenchidos()) {
			documentoService.salvar(documento);
			this.inicializarDocumentosInternos();
			this.inicializarElementosENovoDoc();
			RequestContext.getCurrentInstance().update("modalAddDocumentoInterno");
			RequestContext.getCurrentInstance().update("formDocumentosInternos");
			RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').hide();");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_DOCUMENTO_INTERNO);
		}else {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}
	}
	
	public boolean camposObrigatoriosPreenchidos() {
		if(documento.getConteudo() != null
				&& !documento.getDescricao().isEmpty() && documento.getDescricao() != null
				&& !documento.getTitulo().isEmpty() && documento.getTitulo() != null
				&& documento.getElemento() != null && documento.getTipoDocumento() != null){
			return true;
		}
		
		return false;
	}
	
	public void visualizarDocumentoPDF(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
		RequestContext.getCurrentInstance().update("modalDocumentoPDF");
        stream = new ByteArrayInputStream(documentoSelecionado.getConteudo());
        stream.mark(0); //remember to this position!
        streamedContent = new DefaultStreamedContent(stream, "application/pdf");
        RequestContext.getCurrentInstance().execute("PF('modalDocumentoPDF').show();");
	}
	
	public void visualizarDocumentoDOC(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
		RequestContext.getCurrentInstance().update("modalDocumentoPDF");
        stream = new ByteArrayInputStream(documentoSelecionado.getConteudo());
        stream.mark(0); //remember to this position!
        streamedContent = new DefaultStreamedContent(stream, "application/doc");
        RequestContext.getCurrentInstance().execute("PF('modalDocumentoPDF').show();");
	}
	
	// Gets e Sets
	// ==============================================================================================
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

	public List<TipoDocumento> getTiposDeDocumentosExternos() {
		return tiposDeDocumentosExternos;
	}

	public void setTiposDeDocumentosExternos(
			List<TipoDocumento> tiposDeDocumentosExternos) {
		this.tiposDeDocumentosExternos = tiposDeDocumentosExternos;
	}

	public StreamedContent getStreamedContent() throws IOException {
		if (streamedContent != null)
            streamedContent.getStream().reset();
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public Documento getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}
}
