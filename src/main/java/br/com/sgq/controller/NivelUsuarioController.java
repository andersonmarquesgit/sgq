package br.com.sgq.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgq.model.NivelUsuario;
import br.com.sgq.service.NivelUsuarioService;
import br.com.sgq.utils.FacesUtil;
import br.com.sgq.utils.MsgConstantes;

@ManagedBean
@ViewScoped
@Controller
public class NivelUsuarioController implements Serializable{
	private static final long serialVersionUID = -7580482456782445297L;
	
	private List<NivelUsuario> niveisDeUsuario;
	private NivelUsuario nivelUsuario;
	private NivelUsuario nivelUsuarioSelecionado;
	
	@Autowired
	private NivelUsuarioService nivelUsuarioService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarNivelUsuario();
		niveisDeUsuario = nivelUsuarioService.list();
	}
	
	private void inicializarNivelUsuario() {
		nivelUsuario = new NivelUsuario();
	}

	public void adicionarNivelUsuario() {
		if(nivelUsuario.getNome().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoNivelUsuario').show();");
		}
	}
	
	public void confirmSalvarNivelUsuario() {
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').hide();");
		nivelUsuario.setDataInclusao(new Date());
		Boolean ehEdicao = (this.nivelUsuario.getId() == null);
		nivelUsuarioService.salvar(nivelUsuario);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').hide();");
	}
	
	public void excluirNivelUsuario() {
		nivelUsuarioService.remover(this.nivelUsuarioSelecionado);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmarExclusaoNivelUsuario(NivelUsuario nivelUsuarioSelecionado) {
		this.nivelUsuarioSelecionado = nivelUsuarioSelecionado;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').hide();");
	}
	
	public void editarNivelUsuario(NivelUsuario nivelUsuarioSelecionado) {
		this.nivelUsuario = nivelUsuarioSelecionado;
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').show();");
	}
	
	//Gets e Sets ==============================================================================================
	public List<NivelUsuario> getNiveisDeUsuario() {
		return niveisDeUsuario;
	}

	public void setNiveisDeUsuario(List<NivelUsuario> niveisDeUsuario) {
		this.niveisDeUsuario = niveisDeUsuario;
	}

	public NivelUsuario getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(NivelUsuario nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	public NivelUsuario getNivelUsuarioSelecionado() {
		return nivelUsuarioSelecionado;
	}

	public void setNivelUsuarioSelecionado(NivelUsuario nivelUsuarioSelecionado) {
		this.nivelUsuarioSelecionado = nivelUsuarioSelecionado;
	}
}
