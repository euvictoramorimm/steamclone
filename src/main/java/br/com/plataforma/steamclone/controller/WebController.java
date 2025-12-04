package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.model.Usuario;
import br.com.plataforma.steamclone.repository.JogoRepository;
import br.com.plataforma.steamclone.repository.UsuarioRepository;
import br.com.plataforma.steamclone.service.UsuarioService;


import java.security.Principal; // <-- Import fundamental para saber quem logou
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository; // <-- Precisamos dele para buscar os dados

    // --- PÁGINA INICIAL (ATUALIZADA) ---
    @GetMapping("/")
    public String mostrarPaginaInicial(Model model, Principal principal) {
        // 1. Busca os jogos (como antes)
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        model.addAttribute("jogos", listaDeJogos);

        // 2. Verifica se tem alguém logado
        if (principal != null) {
            // O 'principal.getName()' retorna o email usado no login
            String email = principal.getName();
            Optional<Usuario> usuarioLogado = usuarioRepository.findByEmail(email);
            
            // Se achou o usuário, manda ele para o HTML com o nome "currentUser"
            if (usuarioLogado.isPresent()) {
                model.addAttribute("currentUser", usuarioLogado.get());
            }
        }

        

        return "index";

        
    }

    // --- MÉTODOS DE EDIÇÃO E EXCLUSÃO ---

    // 1. EDITAR: Busca o jogo pelo ID e mostra o formulário preenchido
    @GetMapping("/jogos/editar/{id}")
    public String mostrarFormularioEditarJogo(@PathVariable Long id, Model model) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isPresent()) {
            model.addAttribute("jogo", jogoOpt.get());
            return "form-jogo"; // Reusa o mesmo formulário!
        }
        return "redirect:/";
    }

    // 2. DELETAR: Apaga o jogo pelo ID
    @GetMapping("/jogos/deletar/{id}")
    public String deletarJogo(@PathVariable Long id) {
        jogoRepository.deleteById(id);
        return "redirect:/";
    }

    // ... (Mantenha o resto dos métodos /jogos/novo, /registrar, /login iguais) ...
    // Vou ocultar aqui para economizar espaço, mas NÃO APAGUE ELES!
    @GetMapping("/jogos/novo")
    public String mostrarFormularioNovoJogo(Model model) {
        model.addAttribute("jogo", new Jogo());
        return "form-jogo";
    }

    @PostMapping("/jogos/salvar")
    public String salvarNovoJogo(@ModelAttribute Jogo jogo) {
        jogoRepository.save(jogo);
        return "redirect:/";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.criarNovoUsuario(usuario);
        return "redirect:/login"; 
    }
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}