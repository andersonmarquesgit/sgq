package br.com.sgq.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.sgq.model.Endereco;

public class CepUtilTest {

	@Test
	public void test() {
		Endereco endereco = CepUtil.buscarEndereco("58063460");
		assertNotNull(endereco);
		System.out.println("Logradouro: " + endereco.getLogradouro());
		System.out.println("Bairro: " + endereco.getBairro());
		System.out.println("Cidade: " + endereco.getCidade());
		System.out.println("Estado: " + endereco.getCidade().getEstado().getSigla());
	}
}
