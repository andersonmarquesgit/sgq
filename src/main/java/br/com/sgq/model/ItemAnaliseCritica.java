package br.com.sgq.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_item_analise_critica")
public class ItemAnaliseCritica {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="fk_item")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="fk_resultado")
	private Resultado resultado;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "fk_secao_analise_critica")
	private SecaoAnaliseCritica secaoAnaliseCritica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public SecaoAnaliseCritica getSecaoAnaliseCritica() {
		return secaoAnaliseCritica;
	}

	public void setSecaoAnaliseCritica(SecaoAnaliseCritica secaoAnaliseCritica) {
		this.secaoAnaliseCritica = secaoAnaliseCritica;
	}

}
