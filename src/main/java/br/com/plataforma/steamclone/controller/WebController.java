package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.model.Usuario; // <-- Importante
import br.com.plataforma.steamclone.repository.JogoRepository;
import br.com.plataforma.steamclone.service.UsuarioService; // <-- O nosso Chef!

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private UsuarioService usuarioService; // <-- Injetando o Chef

    // --- PÁGINA INICIAL ---
    @GetMapping("/")
    public String mostrarPaginaInicial(Model model) {
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        model.addAttribute("jogos", listaDeJogos);
        return "index";
    }

    // --- JOGOS (ADMIN) ---
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

    // --- NOVO: CADASTRO DE USUÁRIO ---
    
    // 1. Mostrar a tela de cadastro
    @GetMapping("/registrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario()); // Manda um usuário em branco
        return "register"; // Mostra o register.html
    }

    // 2. Receber os dados e criar o usuário
    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        // Chama o Chef para criptografar a senha e salvar
        usuarioService.criarNovoUsuario(usuario);
        
        // Manda o usuário para a tela de login depois de cadastrar
        return "redirect:/login"; 
    }
    
    // --- NOVO: TELA DE LOGIN ---
    // (O Spring Security faz a mágica, mas precisamos da página)
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Mostra o login.html
    }
}