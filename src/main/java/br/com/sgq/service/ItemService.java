package br.com.sgq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgq.model.Item;
import br.com.sgq.model.Secao;
import br.com.sgq.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> listar() {
		return itemRepository.findAll();
	}

	public List<Item> listar(Secao secao) {
		return itemRepository.listarBySecao(secao);
	}

}
