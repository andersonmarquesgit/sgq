package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.AnaliseCritica;

@Repository
public interface AnaliseCriticaRepository extends JpaRepository<AnaliseCritica, Long> {

}
