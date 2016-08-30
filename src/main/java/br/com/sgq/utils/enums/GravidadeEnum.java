package br.com.sgq.utils.enums;

public enum GravidadeEnum {
	BAIXA(1L), 
	MEDIA(2L), 
	ALTA(3L);
	
	private final Long id;

	GravidadeEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
