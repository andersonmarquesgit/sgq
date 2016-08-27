package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

}
