package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Categoria;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // --- 1. MÉTODO NOVO PARA BUSCA POR NOME (MÁGICA DO JPA) ---
    /**
     * O Spring Data JPA lê o nome e cria a consulta:
     * "SELECT * FROM categorias WHERE nome LIKE '%nome%' (ignorando maiúsculas)"
     *
     * @param nome O nome (ou parte) da categoria a buscar.
     * @return Uma lista de categorias que contêm o nome.
     */
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    
}