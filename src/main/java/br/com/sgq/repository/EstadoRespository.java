package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Estado;

@Repository
public interface EstadoRespository extends JpaRepository<Estado, Long> {

}
