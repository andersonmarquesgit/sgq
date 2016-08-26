package br.com.sgq.autenticacao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.Usuario;
import br.com.sgq.service.UsuarioService;

@ManagedBean
@SessionScoped
@Controller
public class UserSession {
	
	@Autowired
	private UsuarioService userService;
	
	public Usuario obterUsuarioLogado() {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if(authentication instanceof Authentication) {
				return userService.findByLogin(((User)authentication.getPrincipal()).getUsername());
			}
		}
		return null;
	}

}
