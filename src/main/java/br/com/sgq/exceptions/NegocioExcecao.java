package br.com.sgq.exceptions;

import java.util.List;


public class NegocioExcecao extends RuntimeException {

	private static final long serialVersionUID = -8972609392275726092L;
	private final List<String> mensagemList;
	private final String menssagem;
	
	public NegocioExcecao(String mensagem){
		super(mensagem);
		this.menssagem = mensagem;
		this.mensagemList = null;
	}
	
	public NegocioExcecao(List<String> mensagemList){
		super();
		StringBuilder sb = new StringBuilder();
		for (String msg : mensagemList) {
			sb.append(msg + "; ");
		}
		this.menssagem = sb.toString();
		this.mensagemList = mensagemList;
	}
	
	@Override
	public String getMessage() {
		return this.menssagem;
	}

	public List<String> getMensagemList() {
		return mensagemList;
	}
}
