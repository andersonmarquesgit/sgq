package br.com.sgq.utils;

import br.com.sgq.model.Usuario;

public abstract class AcessoUtil {

	/**
	 * Método estático para obter o usuário logado no sistema.
	 *  
	 * @return retorna um objeto do tipo Usuario.
	 */
	public static Usuario obterUsuarioLogado(){
		return (Usuario) FacesUtil.getSessionAttribute(Constantes.PROPRIEDADE_USUARIO_LOGADO);		
	}
	
}
