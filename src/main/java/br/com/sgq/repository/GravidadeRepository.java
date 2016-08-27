package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Gravidade;

@Repository
public interface GravidadeRepository extends JpaRepository<Gravidade, Long> {

}
