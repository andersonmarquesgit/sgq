package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.ItemAnaliseCritica;

@Repository
public interface ItemAnaliseCriticaRepository extends
		JpaRepository<ItemAnaliseCritica, Long> {

}
