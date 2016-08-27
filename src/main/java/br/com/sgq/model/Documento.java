package br.com.sgq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_documento")
public class Documento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="nome_arquivo")
	private String nomeArquivo;
	
	@ManyToOne
	@JoinColumn(name="fk_tipo_documento")
	private TipoDocumento tipoDocumento;
	
	@ManyToOne
	@JoinColumn(name="fk_elemento")
	private Elemento elemento;
	
	@Column(name="revisao")
	private Integer revisao;
	
	@ManyToOne
	@JoinColumn(name="fk_status_documento")
	private StatusDocumento statusDocumento;
	
	//Este atributo corresponde ao campo BLOB/CLOB que armazena 
	// o conteudo do documento PDF (Note a anotação @Lob)
	@Lob
	@Column
	private byte[] conteudo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getRevisao() {
		return revisao;
	}

	public void setRevisao(Integer revisao) {
		this.revisao = revisao;
	}

	public StatusDocumento getStatusDocumento() {
		return statusDocumento;
	}

	public void setStatusDocumento(StatusDocumento statusDocumento) {
		this.statusDocumento = statusDocumento;
	}
}
