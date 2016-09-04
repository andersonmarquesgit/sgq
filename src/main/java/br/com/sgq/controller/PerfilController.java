package br.com.sgq.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.autenticacao.UserSession;
import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;

@ManagedBean
@ViewScoped
@Controller
public class PerfilController {
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private Usuario usuarioLogado;
	private StreamedContent streamedContent;
    
//	public String getUrlRelativoDaFoto() {
//		String url;// = controlarAcessoFoto.obterUrlFotoSemContexto();
//		if(url == null) {
//			return Constantes.LOCAL_IMAGEM_SEM_FOTO;
//		}
//		return url;
//	}
	
	public void uploadFile(FileUploadEvent event)throws IOException{
		InputStream inputStream = event.getFile().getInputstream();
		byte[] file = new byte[inputStream.available()];
		inputStream.read(file);
		inputStream.close();
		this.usuarioLogado = userSession.obterUsuarioLogado();
		this.usuarioLogado.setFoto(file);
		this.carregarFoto();
	}
	
	public void carregarFoto() {
		InputStream stream = new ByteArrayInputStream(this.usuarioLogado.getFoto());
		streamedContent = new DefaultStreamedContent(stream, "image/jpg");
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
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
