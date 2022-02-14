package com.pi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pi.models.Filme;

public interface FilmeRepository extends CrudRepository<Filme, Long> {

	Filme findByCodigo(long codigo);

	List<Filme> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Filme u where u.nome like %?1%")
	List<Filme> findByNomesFilme(String nome);
}
