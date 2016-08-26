package br.com.sgq.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_analise_critica")
public class AnaliseCritica {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="numero_analise_critica", nullable=false)
    private String numero;
	
	@OneToMany(mappedBy = "analiseCritica", cascade=CascadeType.ALL)
	private List<SecaoAnaliseCritica> secoesAnaliseCritica;
	
	@Column(name="conclusao_geral", nullable=false)
	private String conclusaoGeral;
	
	@Column(name="dt_inclusao", nullable=false)
	private Date dataInclusao;
	
	@Column(name="participantes")
	private String participantes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<SecaoAnaliseCritica> getSecoesAnaliseCritica() {
		return secoesAnaliseCritica;
	}

	public void setSecoesAnaliseCritica(
			List<SecaoAnaliseCritica> secoesAnaliseCritica) {
		this.secoesAnaliseCritica = secoesAnaliseCritica;
	}

	public String getConclusaoGeral() {
		return conclusaoGeral;
	}

	public void setConclusaoGeral(String conclusaoGeral) {
		this.conclusaoGeral = conclusaoGeral;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getParticipantes() {
		return participantes;
	}

	public void setParticipantes(String participantes) {
		this.participantes = participantes;
	}
}
