package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Conclusao;

@Repository
public interface ConclusaoRespository extends JpaRepository<Conclusao, Long> {

}
