package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Categoria;

/**
 * Interface JpaRepository para a entidade Categoria.
 * A Chave Primária (Id) é do tipo Long.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}