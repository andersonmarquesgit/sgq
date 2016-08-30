package br.com.sgq.utils.enums;

public enum StatusReclamacaoEnum {
	ANALISE_GRAVIDADE(1L, "Análise da gravidade"),
	ACAO_TOMADA(2L, "Análise de ação"),
	ACEITE_CLIENTE(3L, "Aceite do cliente"), 
	CONCLUIDA(4L, "Concluída");
	
	private final Long id;
	private final String value;

	StatusReclamacaoEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
}
