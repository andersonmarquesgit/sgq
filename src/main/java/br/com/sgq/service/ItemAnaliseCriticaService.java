package br.com.sgq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Item;
import br.com.sgq.model.ItemAnaliseCritica;
import br.com.sgq.model.Resultado;
import br.com.sgq.model.Secao;
import br.com.sgq.model.SecaoAnaliseCritica;

@Service
public class ItemAnaliseCriticaService {

	@Autowired
	private ItemService itemService;
	
	public List<ItemAnaliseCritica> construirItensAnaliseCritica(
			SecaoAnaliseCritica secaoAnaliseCritica, Secao secao) {
		List<ItemAnaliseCritica> itens = new ArrayList<ItemAnaliseCritica>();
		for (Item item : itemService.listar(secao)) {
			ItemAnaliseCritica itemAnaliseCritica = new ItemAnaliseCritica();
			itemAnaliseCritica.setSecaoAnaliseCritica(secaoAnaliseCritica);
			itemAnaliseCritica.setItem(item);
			itemAnaliseCritica.setResultado(new Resultado());
			itens.add(itemAnaliseCritica);
		}
		return itens;
	}

}
