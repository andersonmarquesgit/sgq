package br.com.sgq.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
	
	@Column(name="cep") 
	String cep;
	
	@Column(name="logradouro") 
	String logradouro;
	
	@Column(name="numero_endereco") 
	String numero;
	
	@Column(name="complemento") 
	String complemento;
	
	@Column(name="bairro") 
	String bairro;
	
	@Column(name="cidade") 
	String cidade;
	
	@Column(name="uf") 
	String uf;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
}
