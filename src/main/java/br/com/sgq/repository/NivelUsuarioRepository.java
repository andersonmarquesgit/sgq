package br.com.sgq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgq.model.NivelUsuario;

@Repository
public interface NivelUsuarioRepository extends JpaRepository<NivelUsuario, Long> {

}
