package br.com.plataforma.steamclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.plataforma.steamclone.model.Compra;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    // --- 1. MÉTODO NOVO: Buscar por ID do Usuário ---
    /**
     * O Spring Data JPA lê o nome e cria a consulta:
     * "SELECT * FROM compras WHERE usuario_id = ?"
     *
     * @param usuarioId O ID do usuário.
     * @return Uma lista de compras daquele usuário.
     */
    List<Compra> findByUsuario_Id(Long usuarioId);

    // --- 2. MÉTODO NOVO: Buscar por Status ---
    /**
     * "SELECT * FROM compras WHERE status = ?"
     *
     * @param status O status (ex: "PENDENTE").
     * @return Uma lista de compras com aquele status.
     */
    List<Compra> findByStatus(String status);
    
}