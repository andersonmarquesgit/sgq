package br.com.sgq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.sgq.utils.enums.Role;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
 
	@Column(name="email", nullable=false)
    private String email;
	
	@Column(name="nome", nullable=false)
    private String nome;
	
	@Column(name="login", nullable=false)
	private String login;
	
	@Column(name="senha", nullable=false)
    private String password;
 
	@ManyToOne
	@JoinColumn(name="fk_empresa", nullable=false)
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name="fk_unidade", nullable=false)
	private Unidade unidade;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role", nullable=false)
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="fk_nivel_usuario")
	private NivelUsuario nivel;
	
	@Column(name="ativo")
	private Boolean ativo;
	
	//Este atributo corresponde ao campo BLOB/CLOB que armazena 
	// o conteudo do documento PDF (Note a anotação @Lob)
	@Lob
	@Column
	private byte[] foto;
		
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public NivelUsuario getNivel() {
		return nivel;
	}

	public void setNivel(NivelUsuario nivel) {
		this.nivel = nivel;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
