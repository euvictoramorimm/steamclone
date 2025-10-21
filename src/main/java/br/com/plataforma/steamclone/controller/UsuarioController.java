package br.com.plataforma.steamclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.plataforma.steamclone.model.Usuario;
import br.com.plataforma.steamclone.repository.UsuarioRepository;

/**
 * @RestController: Define que esta classe é um Controlador REST.
 * Ela vai receber pedidos HTTP (da web).
 * @RequestMapping("/usuario"): Define que todos os pedidos
 * para esta classe devem começar com "/usuario".
 * Ex: http://localhost:8080/usuario
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    /**
     * @Autowired: Injeção de Dependência.
     * Pede ao Spring: "Por favor, me dê uma instância
     * do UsuarioRepository que você criou."
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * @GetMapping: Mapeia pedidos HTTP GET para este método.
     * Se alguém aceder http://localhost:8080/usuario (via GET),
     * este método é executado.
     * @return Uma lista de todos os usuarios no banco.
     */
    @GetMapping
    public List<Usuario> listarTodosUsuarios() {
        // Usa a "ponte" (Repository) para buscar tudo no banco.
        return usuarioRepository.findAll();
    }

    /**
     * @PostMapping: Mapeia pedidos HTTP POST para este método.
     * Usado para CRIAR um novo recurso.
     * @RequestBody: Diz ao Spring: "Pegue no JSON do corpo
     * do pedido e transforme-o num objeto 'Usuario'."
     * @return O usuario que acabou de ser salvo (com o ID preenchido).
     */
    @PostMapping
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        // Usa a "ponte" (Repository) para salvar o usuario no banco.
        return usuarioRepository.save(usuario);
    }
}