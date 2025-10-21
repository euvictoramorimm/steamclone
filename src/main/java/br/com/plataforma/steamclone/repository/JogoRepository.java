package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.plataforma.steamclone.model.Jogo;

/**
 * Isto é uma Interface, não uma Classe.
 * JpaRepository<Jogo, Long> significa:
 * "Spring, crie uma ponte para a Entidade 'Jogo',
 * onde a Chave Primária (Id) é do tipo 'Long'."
 *
 * É SÓ ISTO! O Spring automaticamente nos dá métodos como:
 * .save() - Salvar
 * .findAll() - Buscar todos
 * .findById() - Buscar por ID
 * .deleteById() - Deletar
 */
public interface JogoRepository extends JpaRepository<Jogo, Long> {
}