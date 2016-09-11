package br.com.sgq.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.autenticacao.UserSession;
import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;
import br.com.sgq.utils.AcessoUtil;
import br.com.sgq.utils.Constantes;

@Scope("view")
@Controller
public class PerfilController {
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private Usuario usuarioLogado;
	
	private StreamedContent streamedContent;
	
	@PostConstruct
	public void init(){
		this.usuarioLogado = AcessoUtil.obterUsuarioLogado();
		if(this.usuarioLogado != null
				&& this.usuarioLogado.getFoto() != null) {
			this.carregarImagemComFoto();
		}else{
			this.carregarImagemSemFoto();
		}
	}

	public void uploadFile(FileUploadEvent event) {
		InputStream inputStream;
		byte[] file = null;
		try {
			inputStream = event.getFile().getInputstream();
			file = new byte[inputStream.available()];
			inputStream.read(file);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.usuarioLogado = AcessoUtil.obterUsuarioLogado();
		this.usuarioLogado.setFoto(file);
		this.carregarImagemComFoto();
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
	}
	
	public void carregarImagemComFoto() {
		InputStream inputStream = new ByteArrayInputStream(this.usuarioLogado.getFoto());
		this.streamedContent = new DefaultStreamedContent(inputStream, "image/jpg");
	}

	private void carregarImagemSemFoto() {
		InputStream inputStream = FacesContext
				.getCurrentInstance().getExternalContext()
				.getResourceAsStream(Constantes.LOCAL_IMAGEM_SEM_FOTO);
		this.streamedContent = new DefaultStreamedContent(inputStream, "image/jpg");
	}
	
	public void salvar() {
		usuarioService.salvar(this.usuarioLogado);
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
}
