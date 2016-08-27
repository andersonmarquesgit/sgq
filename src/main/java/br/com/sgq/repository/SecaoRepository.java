package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Secao;

@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long> {

}
