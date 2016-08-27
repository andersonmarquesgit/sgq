package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.Reclamacao;
import br.com.sgq.model.TipoReclamacao;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long>{

	@Query("SELECT COUNT(r) FROM Reclamacao r where r.statusReclamacao.id = :statusReclamacao "
			+ "AND month(r.dataInclusao) = :mes "
			+ "AND year(r.dataInclusao) = :ano") 
	public Long calculaReclamacoesNoMes(@Param("mes") Integer mes, @Param("ano") Integer anoSelecionado, @Param("statusReclamacao") Long statusId);

	@Query("SELECT COUNT(r) FROM Reclamacao r where r.tipoReclamacao = :tipoReclamacao "
			+ "AND year(r.dataInclusao) = :ano") 
	public Number calcularReclamacoesPorTipoNoAno(@Param("ano") Integer anoSelecionado, @Param("tipoReclamacao") TipoReclamacao tipoReclamacao);

	@Query("SELECT COUNT(r) FROM Reclamacao r where year(r.dataInclusao) = :ano") 
	public Long calculaReclamacoesPorAno(@Param("ano") Integer ano);

}
