package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

}
