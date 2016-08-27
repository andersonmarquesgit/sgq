package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Elemento;

@Repository
public interface ElementoRepository extends JpaRepository<Elemento, Long> {

}
