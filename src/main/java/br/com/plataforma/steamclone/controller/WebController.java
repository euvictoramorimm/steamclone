package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.JogoRepository;
import java.util.List;

@Controller // Este é o controlador para as PÁGINAS WEB
public class WebController {

    @Autowired
    private JogoRepository jogoRepository;

    /**
     * MÉTODO 1: Mostrar a Página Inicial (Listar Jogos)
     * URL: GET http://localhost:8081/
     */
    @GetMapping("/")
    public String mostrarPaginaInicial(Model model) {
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        model.addAttribute("jogos", listaDeJogos);
        return "index"; // Mostra o index.html
    }

    /**
     * MÉTODO 2: Mostrar o Formulário de Novo Jogo
     * URL: GET http://localhost:8081/jogos/novo
     */
    @GetMapping("/jogos/novo")
    public String mostrarFormularioNovoJogo(Model model) {
        // Cria um objeto Jogo "em branco" para o Thymeleaf
        model.addAttribute("jogo", new Jogo());
        return "form-jogo"; // Mostra o form-jogo.html
    }

    /**
     * MÉTODO 3: Salvar o Novo Jogo (Recebe os dados do formulário)
     * URL: POST http://localhost:8081/jogos/salvar
     */
    @PostMapping("/jogos/salvar")
    public String salvarNovoJogo(@ModelAttribute Jogo jogo) {
        // Salva o novo jogo no banco
        jogoRepository.save(jogo);
        
        // Redireciona o browser de volta para a página inicial
        return "redirect:/";
    }
}