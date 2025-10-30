package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.JogoRepository;
import java.util.List;
import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public Jogo adicionarJogo(@RequestBody Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    @GetMapping
    public List<Jogo> listarTodosJogos() {
        return jogoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarJogoPorId(@PathVariable Long id) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isPresent()) {
            return ResponseEntity.ok(jogoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizarJogo(@PathVariable Long id, @RequestBody Jogo jogoAtualizado) {
        if (!jogoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogoAtualizado.setId(id); 
        Jogo jogoSalvo = jogoRepository.save(jogoAtualizado);
        return ResponseEntity.ok(jogoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        if (!jogoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{jogoId}/adicionarCategoria/{categoriaId}")
    public Jogo adicionarCategoriaAoJogo(
        @PathVariable Long jogoId,
        @PathVariable Long categoriaId
    ) {
        Jogo jogo = jogoRepository.findById(jogoId)
            .orElseThrow(() -> new RuntimeException("Jogo não encontrado!"));
        
        Categoria categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
        
        jogo.getCategorias().add(categoria);
        return jogoRepository.save(jogo);
    }
}