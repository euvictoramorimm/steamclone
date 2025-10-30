package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Jogo;
import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    // --- 1. MÉTODO NOVO PARA BUSCA (MÁGICA DO JPA) ---
    /**
     * O Spring Data JPA lê o nome deste método e "magicamente" cria a consulta SQL:
     * "SELECT * FROM jogos WHERE titulo LIKE '%titulo%' (ignorando maiúsculas)"
     *
     * @param titulo O pedaço do título que queremos buscar.
     * @return Uma lista de jogos que contêm o título.
     */
    List<Jogo> findByTituloContainingIgnoreCase(String titulo);

    // --- 2. MÉTODO NOVO PARA LISTAR POR CATEGORIA (MÁGICA DO JPA) ---
    /**
     * "SELECT * FROM jogos WHERE categorias (lista) contêm o ID da categoria"
     *
     * @param categoriaId O ID da Categoria que estamos a procurar.
     * @return Uma lista de jogos dessa categoria.
     */
    List<Jogo> findByCategorias_Id(Long categoriaId);

}