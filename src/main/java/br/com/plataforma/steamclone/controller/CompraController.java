package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Compra;
import br.com.plataforma.steamclone.repository.CompraRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    @PostMapping
    public Compra adicionarCompra(@RequestBody Compra compra) {
        compra.setDataCompra(LocalDateTime.now());
        // (Aqui faltaria a lógica de calcular o valorTotal)
        return compraRepository.save(compra);
    }

    @GetMapping
    public List<Compra> listarTodasCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarCompraPorId(@PathVariable Long id) {
        Optional<Compra> compraOpt = compraRepository.findById(id);
        if (compraOpt.isPresent()) {
            return ResponseEntity.ok(compraOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizarCompra(@PathVariable Long id, @RequestBody Compra compraAtualizada) {
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compraAtualizada.setId(id); 
        
        if(compraAtualizada.getDataCompra() == null) {
            compraRepository.findById(id).ifPresent(c -> 
                compraAtualizada.setDataCompra(c.getDataCompra()));
        }
        
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
}