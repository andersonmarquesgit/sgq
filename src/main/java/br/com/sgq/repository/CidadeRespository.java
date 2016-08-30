package br.com.sgq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Cidade;

@Repository
public interface CidadeRespository extends JpaRepository<Cidade, Long> {

	@Query("SELECT c FROM Cidade c where c.estado.id = :idEstado")
	List<Cidade> listarCidadesPorEstado(@Param("idEstado") Long idEstado);

}
