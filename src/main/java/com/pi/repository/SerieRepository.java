package com.pi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pi.models.Serie;

public interface SerieRepository extends CrudRepository<Serie, Long> {

	Serie findByCodigo(long codigo);

	List<Serie> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Serie u where u.nome like %?1%")
	List<Serie> findByNomesSerie(String nome);
}
