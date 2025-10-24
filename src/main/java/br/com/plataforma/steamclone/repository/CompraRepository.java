package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Compra;

/**
 * Interface JpaRepository para a entidade Compra.
 * A Chave Primária (Id) é do tipo Long.
 */
public interface CompraRepository extends JpaRepository<Compra, Long> {
}