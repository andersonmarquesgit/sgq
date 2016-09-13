package br.com.sgq.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;
import br.com.sgq.utils.Constantes;
import br.com.sgq.utils.FacesUtil;

@Scope("session")
@Controller
public class UserSession {
	
	@Autowired
	private UsuarioService userService;
	
	public Usuario obterUsuarioLogado() {
		SecurityContext context = SecurityContextHolder.getContext();
		Usuario usuarioLogado = null;
		
		if(context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if(authentication instanceof Authentication) {
				usuarioLogado = userService.findByLogin(((User)authentication.getPrincipal()).getUsername());
				FacesUtil.setSessionAttribute(Constantes.PROPRIEDADE_USUARIO_LOGADO, usuarioLogado);
			}
		}
		return usuarioLogado;
	}

}
