package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private br.com.plataforma.steamclone.service.CompraService compraService;

    // --- CARTEIRA: ADICIONAR SALDO ---
    @PostMapping("/adicionar-saldo")
    public String adicionarSaldo(@RequestParam("valor") Double valor, Principal principal) {
        if (principal != null && valor > 0) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
            usuario.setSaldoCarteira(usuario.getSaldoCarteira() + valor);
            usuarioRepository.save(usuario);
        }
        return "redirect:/";
    }

    // --- CARTEIRA: RETIRAR SALDO (SAQUE) ---
    @PostMapping("/retirar-saldo")
    public String retirarSaldo(@RequestParam("valor") Double valor, Principal principal) {
        if (principal != null && valor > 0) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
            
            // Só saca se tiver saldo suficiente
            if (usuario.getSaldoCarteira() >= valor) {
                usuario.setSaldoCarteira(usuario.getSaldoCarteira() - valor);
                usuarioRepository.save(usuario);
            }
        }
        return "redirect:/";
    }

    // --- COMPRAR JOGO ---
    @PostMapping("/comprar/{id}")
    public String comprarJogo(@PathVariable Long id, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        compraService.realizarCompra(usuario, id);

        return "redirect:/biblioteca";
    }

    // --- VER BIBLIOTECA ---
    @GetMapping("/biblioteca")
    public String mostrarBiblioteca(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        model.addAttribute("currentUser", usuario);
        model.addAttribute("jogos", usuario.getBiblioteca()); // Manda só os jogos dele
        
        return "library";
    }

   // Em WebController.java

    // --- PÁGINA INICIAL (COM BUSCA) ---
    @GetMapping("/")
    public String mostrarPaginaInicial(
            @RequestParam(value = "busca", required = false) String busca, // 1. Recebe o texto da busca (opcional)
            Model model, 
            Principal principal
    ) {
        List<Jogo> listaDeJogos;

        // 2. Lógica da Pesquisa
        if (busca != null && !busca.isEmpty()) {
            // Se o usuário digitou algo, busca no banco pelo título
            listaDeJogos = jogoRepository.findByTituloContainingIgnoreCase(busca);
        } else {
            // Se não digitou nada, traz tudo
            listaDeJogos = jogoRepository.findAll();
        }
        
        // Manda a lista (filtrada ou completa) para a tela
        model.addAttribute("jogos", listaDeJogos);

        // 3. Verifica usuário logado (código que já existia)
        if (principal != null) {
            String email = principal.getName();
            Optional<Usuario> usuarioLogado = usuarioRepository.findByEmail(email);
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
    
    // --- PÁGINA DE DETALHES DO JOGO ---
    @GetMapping("/jogo/{id}")
    public String mostrarDetalhesJogo(@PathVariable Long id, Model model, Principal principal) {
        // 1. Busca o jogo (se não achar, volta pra home)
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isEmpty()) {
            return "redirect:/";
        }
        
        Jogo jogo = jogoOpt.get();
        model.addAttribute("jogo", jogo);

        // 2. Verifica usuário logado (para mostrar botão Comprar ou Jogar)
        if (principal != null) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
            model.addAttribute("currentUser", usuario);
            
            // Verifica se o usuário JÁ TEM esse jogo
            boolean possuiJogo = usuario.getBiblioteca().contains(jogo);
            model.addAttribute("possuiJogo", possuiJogo);
        } else {
            model.addAttribute("possuiJogo", false);
        }

        return "detalhes"; // Vamos criar esse arquivo agora!
    }
}