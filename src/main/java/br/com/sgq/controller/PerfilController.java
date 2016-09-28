package br.com.sgq.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.autenticacao.UserSession;
import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;
import br.com.sgq.utils.AcessoUtil;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

@Scope("view")
@Controller
public class PerfilController {
	
	private static final Logger logger = LogManager.getLogger(PerfilController.class);
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private Usuario usuarioLogado;
	
	@PostConstruct
	public void init(){
		this.usuarioLogado = AcessoUtil.obterUsuarioLogado();
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
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
	}
	
	public void salvar() {
		if (validarCamposObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmSalvarPerfil').show();");
		}
	}
	
	public String confirmSalvarPerfil() {
		RequestContext.getCurrentInstance().execute(
				"PF('confirmSalvarPerfil').hide();");
		usuarioService.salvar(this.usuarioLogado);
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		return redirectPerfil();
	}
	
	public String cancelarEdicaoPerfil() {
		return redirectDashboard();
	}
	
	public String redirectPerfil() {
		return FacesUtil.sendRedirect("/paginas/usuario/perfil");
	}
	
	public String redirectDashboard() {
		return FacesUtil.sendRedirect("/paginas/index");
	}

	private boolean validarCamposObrigatorios() {
		if(this.usuarioLogado.getEmail() == null
				&& this.usuarioLogado.getNome() == null){
			return true;
		}
		return false;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
