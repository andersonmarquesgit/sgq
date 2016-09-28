package br.com.sgq.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;
import br.com.sgq.utils.enums.Role;

@Scope("view")
@Controller
public class UsuarioController {
	
	private List<Usuario> usuarios;
	
	private Usuario usuario;
	
	@Autowired
	private UsuarioService userService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetos();
	}

	private void inicializarObjetos() {
		usuario = new Usuario();
		usuarios = userService.list();
	}
	
	public void adicionarUsuario() {
		if(validarCampoObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUsuario').show();");
		}
	}
	
	public void confirmSalvarUsuario() {
		RequestContext.getCurrentInstance().update("formUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalUsuario').hide();");
		usuario.setRole(Role.ROLE_USER);
		usuario.setLogin(usuario.getEmail());
		usuario.setEmpresa(usuario.getUnidade().getEmpresa());
		userService.salvar(usuario);
		this.inicializarObjetos();
		if(this.usuario.getId() == null) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetos();
		RequestContext.getCurrentInstance().update("formUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalUsuario').hide();");
	}
	
	public boolean validarCampoObrigatorios(){
		if(this.usuario.getNome().isEmpty() ||
				this.usuario.getEmail().isEmpty() ||
				this.usuario.getPassword().isEmpty()) {
			return true;
		}
		return false;
	}
	
	//Gets e Sets ==============================================================================================
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
