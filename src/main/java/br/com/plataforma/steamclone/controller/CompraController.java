package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Compra;
import br.com.plataforma.steamclone.repository.CompraRepository;
import br.com.plataforma.steamclone.service.NotaFiscalFactory; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.http.HttpStatus; 

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID; 

@RestController
@RequestMapping("/compras")
public class CompraController {

   
    private static final Logger logger = LoggerFactory.getLogger(CompraController.class); 

    @Autowired
    private CompraRepository compraRepository;

    
    @Autowired
    private NotaFiscalFactory notaFiscalFactory; 

    // --- CRUD BÁSICO (adicionarCompra MODIFICADO) ---
    @PostMapping
    // Mudamos o retorno para ResponseEntity<?> para poder retornar o erro no catch
    public ResponseEntity<?> adicionarCompra(@RequestBody Compra compra) { 


        logger.debug("Tentativa de adicionar nova compra. Valor: {}", compra.getValorTotal());
        
        System.out.println(">>> [CompraController] Iniciando transação de compra.");

        try { 
            
            // Lógica de negócio ao criar a compra (igual ao original, mais a NF)
            compra.setDataCompra(LocalDateTime.now());
            compra.setStatus("CONCLUÍDA"); 
            compra.setCodigoConfirmacao(UUID.randomUUID().toString()); 
            
            
            String notaFiscal = notaFiscalFactory.gerarNotaFiscal("DIGITAL");
            compra.setNotaFiscal(notaFiscal); // Define o novo campo NotaFiscal no Model Compra
            
            // (Lógica de valores - igual ao original)
            if (compra.getValorTotal() == null) {
                compra.setValorTotal(0.0); 
            }
            if (compra.getDescontoAplicado() == null) {
                compra.setDescontoAplicado(0.0);
            }
            
            Compra compraSalva = compraRepository.save(compra);
            logger.info("Compra concluída com sucesso. ID: {}", compraSalva.getId());
            System.out.println("<<< [CompraController] Compra finalizada com sucesso. ID: " + compraSalva.getId());

            // Retorna status 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);

        } catch (Exception e) { // ⭐️
            
           
            logger.error("ERRO FATAL ao salvar compra: {}", e.getMessage(), e);
            
            System.err.println("!!! ERRO NO PROCESSAMENTO: " + e.getMessage());

            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a compra: " + e.getMessage());
        }
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

    // --- MÉTODOS ADICIONAIS ---

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