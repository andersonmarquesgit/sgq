package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.SecaoAnaliseCritica;

@Repository
public interface SecaoAnaliseCriticaRepository extends
		JpaRepository<SecaoAnaliseCritica, Long> {

}
