package br.com.sgq.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tb_reclamacao")
public class Reclamacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="numero_reclamacao")
    private String numero;
	
	@Column(name="descricao")
    private String descricao;
	
	@Column(name="remediacao_sugerida")
	private String remediacaoSugerida;
	
	@Column(name="dt_prazo_resposta")
	private Date dataPrazoResposta;
	
	@Column(name="dt_inclusao")
	private Date dataInclusao;
	
	@Column(name="dt_alteracao")
	private Date dataAlteracao;
	
	@ManyToOne
	@JoinColumn(name="fk_tipo_reclamacao")
	private TipoReclamacao tipoReclamacao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="fk_status_reclamacao")
	private StatusReclamacao statusReclamacao;
	
	@ManyToOne
	@JoinColumn(name="fk_usuario")
	@NotFound(action=NotFoundAction.IGNORE)
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@JoinColumn(name="fk_aceite_cliente")
	private AceiteCliente aceiteCliente;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@JoinColumn(name="fk_acao_tomada")
	private AcaoTomada acaoTomada;
	
	@ManyToOne
	@JoinColumn(name="fk_gravidade")
	private Gravidade gravidade;
	
	@ManyToOne
	@JoinColumn(name="fk_complexidade")
	private Gravidade complexidade;

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

	public String getRemediacaoSugerida() {
		return remediacaoSugerida;
	}

	public void setRemediacaoSugerida(String remediacaoSugerida) {
		this.remediacaoSugerida = remediacaoSugerida;
	}

	public Date getDataPrazoResposta() {
		return dataPrazoResposta;
	}

	public void setDataPrazoResposta(Date dataPrazoResposta) {
		this.dataPrazoResposta = dataPrazoResposta;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataCriacao) {
		this.dataInclusao = dataCriacao;
	}

	public TipoReclamacao getTipoReclamacao() {
		return tipoReclamacao;
	}

	public void setTipoReclamacao(TipoReclamacao tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusReclamacao getStatusReclamacao() {
		return statusReclamacao;
	}

	public void setStatusReclamacao(StatusReclamacao statusReclamacao) {
		this.statusReclamacao = statusReclamacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AceiteCliente getAceiteCliente() {
		return aceiteCliente;
	}

	public void setAceiteCliente(AceiteCliente aceiteCliente) {
		this.aceiteCliente = aceiteCliente;
	}

	public AcaoTomada getAcaoTomada() {
		return acaoTomada;
	}

	public void setAcaoTomada(AcaoTomada acaoTomada) {
		this.acaoTomada = acaoTomada;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Gravidade getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravidade gravidade) {
		this.gravidade = gravidade;
	}

	public Gravidade getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(Gravidade complexidade) {
		this.complexidade = complexidade;
	}
}
