package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Usuario;
import java.util.Optional; // Usamos Optional para buscas que podem não retornar nada

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // --- 1. MÉTODO NOVO PARA BUSCA POR EMAIL (MÁGICA DO JPA) ---
    /**
     * O Spring Data JPA lê o nome e cria a consulta:
     * "SELECT * FROM usuarios WHERE email = ? (passando o email)"
     *
     * @param email O email exato a buscar.
     * @return Um Optional contendo o Usuário (se encontrado).
     */
    Optional<Usuario> findByEmail(String email);

}