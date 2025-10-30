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

    // --- CRUD BÁSICO (Já tínhamos, mas melhorado) ---
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
        return jogoRepository.findById(id)
                .map(jogo -> ResponseEntity.ok(jogo))
                .orElse(ResponseEntity.notFound().build());
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
    
    // --- RELACIONAMENTO (Já tínhamos) ---
    @PostMapping("/{jogoId}/adicionarCategoria/{categoriaId}")
    public Jogo adicionarCategoriaAoJogo(@PathVariable Long jogoId, @PathVariable Long categoriaId) {
        Jogo jogo = jogoRepository.findById(jogoId)
            .orElseThrow(() -> new RuntimeException("Jogo não encontrado!"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
        
        jogo.getCategorias().add(categoria);
        return jogoRepository.save(jogo);
    }

    // --- 3 NOVOS MÉTODOS ---

    /**
     * MÉTODO 1 NOVO: Buscar Jogo por Título
     * @GetMapping("/buscar"): GET http://localhost:8080/jogos/buscar?titulo=Witcher
     * @param titulo O nome (ou parte) do jogo a buscar.
     */
    @GetMapping("/buscar")
    public List<Jogo> buscarJogosPorTitulo(@RequestParam String titulo) {
        // Usa o novo método mágico que criámos no Repository
        return jogoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * MÉTODO 2 NOVO: Listar Jogos por Categoria
     * @GetMapping("/por-categoria/{categoriaId}"): GET http://localhost:8080/jogos/por-categoria/1
     * @param categoriaId O ID da categoria.
     */
    @GetMapping("/por-categoria/{categoriaId}")
    public List<Jogo> buscarJogosPorCategoria(@PathVariable Long categoriaId) {
        // Usa o novo método mágico do Repository
        return jogoRepository.findByCategorias_Id(categoriaId);
    }

    /**
     * MÉTODO 3 NOVO: Atualizar Preço (Promoção)
     * @PatchMapping: Usamos PATCH para atualizações parciais (só o preço).
     * @PatchMapping("/{id}/atualizar-preco"): PATCH http://localhost:8080/jogos/1/atualizar-preco?preco=99.90
     * @param id O ID do jogo a atualizar.
     * @param preco O novo preço.
     */
    @PatchMapping("/{id}/atualizar-preco")
    public ResponseEntity<Jogo> atualizarPrecoDoJogo(
            @PathVariable Long id,
            @RequestParam double preco
    ) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Jogo jogo = jogoOpt.get();
        jogo.setPreco(preco); // Atualiza SÓ o preço
        jogoRepository.save(jogo); // Salva a alteração
        
        return ResponseEntity.ok(jogo);
    }
}