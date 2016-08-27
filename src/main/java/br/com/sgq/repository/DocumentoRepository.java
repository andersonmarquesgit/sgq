package br.com.sgq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Documento;
import br.com.sgq.model.TipoDocumento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
	@Query("SELECT d FROM Documento d where d.tipoDocumento = :tipoDocumento") 
	public List<Documento> findByTipoDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, Pageable pegeable);
	
	@Query("SELECT COUNT(d) FROM Documento d where d.tipoDocumento = :tipoDocumento") 
	public Long countByTipoDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento);
	
	@Query("SELECT d FROM Documento d where d.tipoDocumento = :tipoDocumento") 
	public List<Documento> listByTipoDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento);
}
