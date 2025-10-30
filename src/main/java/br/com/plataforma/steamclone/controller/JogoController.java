package br.com.plataforma.steamclone.controller;

// --- IMPORTANTE: Mudança aqui ---
import org.springframework.stereotype.Controller; // Sai @RestController, entra @Controller
import org.springframework.ui.Model; // Import para "carregar" dados para o HTML

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.JogoRepository;
import java.util.List;

// (Imports de Categoria, CategoriaRepository, PathVariable, Optional)
import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

/**
 * --- MUDANÇA IMPORTANTE ---
 * @Controller: Define que esta classe é um Controlador Web normal.
 * Ela agora vai procurar ficheiros HTML na pasta /templates
 * em vez de devolver JSON.
 */
@Controller
// @RequestMapping("/jogos") // Vamos remover o /jogos por agora para facilitar
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * --- MÉTODO 1 (A NOSSA NOVA PÁGINA INICIAL) ---
     * @GetMapping("/"): Mapeia pedidos GET para a raiz do site (http://localhost:8080/)
     *
     * @param model O "caixote" que o Spring nos dá para levar dados para o HTML.
     * @return String "index": O nome do ficheiro HTML que queremos mostrar
     * (Spring vai procurar /resources/templates/index.html).
     */
    @GetMapping("/")
    public String listarTodosJogos(Model model) {
        
        // 1. Busca todos os jogos no banco de dados
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        
        // 2. "Carrega" a lista de jogos no "caixote" (model)
        // O nome "jogos" TEM que ser o mesmo que usámos no HTML (th:each="jogo : ${jogos}")
        model.addAttribute("jogos", listaDeJogos);
        
        // 3. Retorna o nome do ficheiro HTML
        return "index";
    }

    
    /* ==================================================================
    NOTA: Os métodos abaixo (@PostMapping, @PutMapping, etc.)
    ainda são APIs REST (porque têm @ResponseBody ou estão em
    Controllers diferentes).
    Para este nosso teste simples, vamos focar-nos apenas
    no @GetMapping("/") acima.
    
    Se quiséssemos que o JogoController MISTURASSE HTML e API,
    manteríamos o @Controller e adicionaríamos @ResponseBody
    em cada método que devolve JSON.
    
    Mas por agora, o seu JogoController só precisa do método
    listarTodosJogos() acima.
    ==================================================================
    */
    
    // --- Métodos de API (Vamos mantê-los para o futuro) ---
    // Para que eles funcionem num @Controller, adicionamos @ResponseBody
    
    @PostMapping("/api/jogos")
    @ResponseBody
    public Jogo adicionarJogo(@RequestBody Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    @GetMapping("/api/jogos/{id}")
    @ResponseBody
    public ResponseEntity<Jogo> buscarJogoPorId(@PathVariable Long id) {
        return jogoRepository.findById(id)
                .map(jogo -> ResponseEntity.ok(jogo))
                .orElse(ResponseEntity.notFound().build());
    }

    // ... (Todos os outros métodos CRUD e de relacionamento
    // deveriam ter @ResponseBody também e estar num
    // @RequestMapping("/api/jogos") separado, mas vamos manter simples por agora) ...
}