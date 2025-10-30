package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import java.time.LocalDate; // Import para data
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // --- CRUD BÁSICO (Melhorado) ---
    @PostMapping
    public Categoria adicionarCategoria(@RequestBody Categoria categoria) {
        // Define a data de criação e o status 'ativa' como padrão
        if (categoria.getDataCriacao() == null) {
            categoria.setDataCriacao(LocalDate.now());
        }
        if (categoria.getAtiva() == null) {
            categoria.setAtiva(true);
        }
        return categoriaRepository.save(categoria);
    }

    @GetMapping
    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaAtualizada.setId(id);
        Categoria categoriaSalva = categoriaRepository.save(categoriaAtualizada);
        return ResponseEntity.ok(categoriaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // --- MÉTODOS DE RELACIONAMENTO (Já tínhamos) ---
    @GetMapping("/{id}/jogos")
    public ResponseEntity<List<Jogo>> buscarJogosDaCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaOpt.get().getJogos());
    }

    // --- 3 NOVOS MÉTODOS ---

    /**
     * MÉTODO 1 NOVO: Buscar Categoria por Nome
     * @GetMapping("/buscar"): GET http://localhost:8080/categorias/buscar?nome=RPG
     */
    @GetMapping("/buscar")
    public List<Categoria> buscarCategoriasPorNome(@RequestParam String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * MÉTODO 2 NOVO: Ativar Categoria
     * @PatchMapping("/{id}/ativar"): PATCH http://localhost:8080/categorias/1/ativar
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Categoria> ativarCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Categoria categoria = categoriaOpt.get();
        categoria.setAtiva(true); // Muda o status
        categoriaRepository.save(categoria); // Salva
        return ResponseEntity.ok(categoria);
    }

    /**
     * MÉTODO 3 NOVO: Desativar Categoria
     * @PatchMapping("/{id}/desativar"): PATCH http://localhost:8080/categorias/1/desativar
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Categoria> desativarCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Categoria categoria = categoriaOpt.get();
        categoria.setAtiva(false); // Muda o status
        categoriaRepository.save(categoria); // Salva
        return ResponseEntity.ok(categoria);
    }
}