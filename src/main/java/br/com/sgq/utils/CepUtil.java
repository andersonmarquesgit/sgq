package br.com.sgq.utils;

import br.com.sgq.model.Endereco;
import br.com.sgq.webservices.WebServiceCep;

public class CepUtil {
	
    public static Endereco buscarEndereco(String cep) {
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
        Endereco endereco = new Endereco();
        
        if(webServiceCep.wasSuccessful()) {
        	endereco.setLogradouro(webServiceCep.getLogradouroFull());
        	endereco.setBairro(webServiceCep.getBairro());
        	endereco.setCidade(webServiceCep.getCidade());
        	endereco.setUf(webServiceCep.getUf());
        	endereco.setCep(webServiceCep.getCep());
        }
        return endereco;
    }
}
