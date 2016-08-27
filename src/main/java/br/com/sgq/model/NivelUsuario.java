package br.com.sgq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_nivel_usuario")
public class NivelUsuario {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="nome")
    private String nome;

	@Column(name="permissao_documentos", columnDefinition = "boolean default false")
    private Boolean permissaoDocumentos;
	
	@Column(name="permissao_sac", columnDefinition = "boolean default false")
    private Boolean permissaoSac;
	
	@Column(name="permissao_analise_critica", columnDefinition = "boolean default false")
    private Boolean permissaoAnaliseCritica;
	
	@Column(name="permissao_configuracoes", columnDefinition = "boolean default false")
    private Boolean permissaoConfiguracoes;
	
	@Column(name="dt_inclusao")
	private Date dataInclusao;
	
	@Column(name="dt_alteracao")
	private Date dataAlteracao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getPermissaoDocumentos() {
		return permissaoDocumentos;
	}

	public void setPermissaoDocumentos(Boolean permissaoDocumentos) {
		this.permissaoDocumentos = permissaoDocumentos;
	}

	public Boolean getPermissaoSac() {
		return permissaoSac;
	}

	public void setPermissaoSac(Boolean permissaoSac) {
		this.permissaoSac = permissaoSac;
	}

	public Boolean getPermissaoAnaliseCritica() {
		return permissaoAnaliseCritica;
	}

	public void setPermissaoAnaliseCritica(Boolean permissaoAnaliseCritica) {
		this.permissaoAnaliseCritica = permissaoAnaliseCritica;
	}

	public Boolean getPermissaoConfiguracoes() {
		return permissaoConfiguracoes;
	}

	public void setPermissaoConfiguracoes(Boolean permissaoConfiguracoes) {
		this.permissaoConfiguracoes = permissaoConfiguracoes;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}
