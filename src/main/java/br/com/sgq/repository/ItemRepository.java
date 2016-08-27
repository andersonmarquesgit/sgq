package br.com.sgq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Item;
import br.com.sgq.model.Secao;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query("SELECT i FROM Item i where i.secao = :secao ") 
	public List<Item> listarBySecao(@Param("secao") Secao secao);

}
