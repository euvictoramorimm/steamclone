package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Compra;
import br.com.plataforma.steamclone.repository.CompraRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // Para gerar o código de confirmação

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    // --- CRUD BÁSICO (Melhorado) ---
    @PostMapping
    public Compra adicionarCompra(@RequestBody Compra compra) {
        // Lógica de negócio ao criar a compra
        compra.setDataCompra(LocalDateTime.now());
        compra.setStatus("CONCLUÍDA"); // Ou "PENDENTE" se precisar de confirmação
        compra.setCodigoConfirmacao(UUID.randomUUID().toString()); // Gera um código único
        
        // (Numa aplicação real, aqui calcularíamos o valorTotal
        // e o descontoAplicado com base na lista de jogos)
        if (compra.getValorTotal() == null) {
            compra.setValorTotal(0.0); // Valor padrão
        }
        if (compra.getDescontoAplicado() == null) {
            compra.setDescontoAplicado(0.0); // Valor padrão
        }
        
        return compraRepository.save(compra);
    }

    @GetMapping
    public List<Compra> listarTodasCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarCompraPorId(@PathVariable Long id) {
        return compraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizarCompra(@PathVariable Long id, @RequestBody Compra compraAtualizada) {
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compraAtualizada.setId(id);
        Compra compraSalva = compraRepository.save(compraAtualizada);
        return ResponseEntity.ok(compraSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCompra(@PathVariable Long id) {
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- 3 NOVOS MÉTODOS ---

    /**
     * MÉTODO 1 NOVO: Listar Compras de um Usuário
     * @GetMapping("/por-usuario/{usuarioId}"): GET http://localhost:8080/compras/por-usuario/1
     */
    @GetMapping("/por-usuario/{usuarioId}")
    public List<Compra> listarComprasDoUsuario(@PathVariable Long usuarioId) {
        return compraRepository.findByUsuario_Id(usuarioId);
    }

    /**
     * MÉTODO 2 NOVO: Cancelar uma Compra
     * @PatchMapping("/{id}/cancelar"): PATCH http://localhost:8080/compras/1/cancelar
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Compra> cancelarCompra(@PathVariable Long id) {
        Optional<Compra> compraOpt = compraRepository.findById(id);
        if (compraOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Compra compra = compraOpt.get();
        compra.setStatus("CANCELADA");
        compraRepository.save(compra);
        return ResponseEntity.ok(compra);
    }

    /**
     * MÉTODO 3 NOVO: Buscar Compras por Status
     * @GetMapping("/buscar"): GET http://localhost:8080/compras/buscar?status=PENDENTE
     */
    @GetMapping("/buscar")
    public List<Compra> buscarComprasPorStatus(@RequestParam String status) {
        return compraRepository.findByStatus(status);
    }
}