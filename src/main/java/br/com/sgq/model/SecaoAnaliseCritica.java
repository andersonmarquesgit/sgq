package br.com.sgq.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_secao_analise_critica")
public class SecaoAnaliseCritica {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@OneToMany(mappedBy = "secaoAnaliseCritica", cascade=CascadeType.ALL)
	private List<ItemAnaliseCritica> itensAnaliseCritica;
	
	@ManyToOne
	@JoinColumn(name = "fk_analise_critica")
	private AnaliseCritica analiseCritica;
	
	@ManyToOne
	@JoinColumn(name = "fk_conclusao")
	private Conclusao conclusao;
	
	@Column(name="referencias")
	private String referencias;
	
	@Column(name="comentarios")
	private String comentarios;
	
	@OneToOne
	private Secao secao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemAnaliseCritica> getItensAnaliseCritica() {
		return itensAnaliseCritica;
	}

	public void setItensAnaliseCritica(List<ItemAnaliseCritica> itensAnaliseCritica) {
		this.itensAnaliseCritica = itensAnaliseCritica;
	}

	public AnaliseCritica getAnaliseCritica() {
		return analiseCritica;
	}

	public void setAnaliseCritica(AnaliseCritica analiseCritica) {
		this.analiseCritica = analiseCritica;
	}

	public Conclusao getConclusao() {
		return conclusao;
	}

	public void setConclusao(Conclusao conclusao) {
		this.conclusao = conclusao;
	}

	public String getReferencias() {
		return referencias;
	}

	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}
}
