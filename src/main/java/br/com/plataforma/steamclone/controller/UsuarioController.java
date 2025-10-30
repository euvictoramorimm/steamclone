package br.com.plataforma.steamclone.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Import geral

import br.com.plataforma.steamclone.model.Usuario;
import br.com.plataforma.steamclone.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JogoRepository jogoRepository;

    // --- CRUD BÁSICO (Já tínhamos) ---
    @PostMapping
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        // Inicializa o saldo da carteira como 0.0 ao criar um novo usuário
        if(usuario.getSaldoCarteira() == null) {
            usuario.setSaldoCarteira(0.0);
        }
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioAtualizado.setId(id);
        Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- RELACIONAMENTO (Já tínhamos) ---
    @PostMapping("/{usuarioId}/adicionarJogo/{jogoId}")
    public Usuario adicionarJogoNaBiblioteca(@PathVariable Long usuarioId, @PathVariable Long jogoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        Jogo jogo = jogoRepository.findById(jogoId)
            .orElseThrow(() -> new RuntimeException("Jogo não encontrado!"));
        
        usuario.getBiblioteca().add(jogo);
        return usuarioRepository.save(usuario);
    }

    // --- 3 NOVOS MÉTODOS ---

    /**
     * MÉTODO 1 NOVO: Buscar Usuário por Email
     * @GetMapping("/buscar"): GET http://localhost:8080/usuarios/buscar?email=victor@email.com
     */
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email) {
        return usuarioRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * MÉTODO 2 NOVO: Ver a Biblioteca de um Usuário
     * @GetMapping("/{id}/biblioteca"): GET http://localhost:8080/usuarios/1/biblioteca
     */
    @GetMapping("/{id}/biblioteca")
    public ResponseEntity<List<Jogo>> verBibliotecaDoUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Retorna a lista de jogos daquele usuário
        return ResponseEntity.ok(usuarioOpt.get().getBiblioteca());
    }

    /**
     * MÉTODO 3 NOVO: Adicionar Saldo à Carteira
     * @PatchMapping("/{id}/adicionar-saldo"): PATCH http://localhost:8080/usuarios/1/adicionar-saldo?valor=50.0
     */
    @PatchMapping("/{id}/adicionar-saldo")
    public ResponseEntity<Usuario> adicionarSaldo(
            @PathVariable Long id,
            @RequestParam double valor
    ) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if (valor <= 0) {
            // Não podemos adicionar valor negativo ou zero
            return ResponseEntity.badRequest().build(); // Retorna erro 400
        }
        
        Usuario usuario = usuarioOpt.get();
        double saldoAtual = usuario.getSaldoCarteira();
        usuario.setSaldoCarteira(saldoAtual + valor); // Adiciona o valor
        usuarioRepository.save(usuario); // Salva
        
        return ResponseEntity.ok(usuario);
    }
}