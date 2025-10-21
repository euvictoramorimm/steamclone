package br.com.plataforma.steamclone.controller;

// Imports existentes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.JogoRepository;
import java.util.List;

// --- NOVAS IMPORTAÇÕES ADICIONADAS ---
import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.repository.CategoriaRepository; // Precisa do repositório
import org.springframework.web.bind.annotation.PathVariable; // Para ler IDs da URL
import java.util.Optional; // Para buscar por ID (findById)

/**
 * @RestController: Define que esta classe é um Controlador REST.
 * @RequestMapping("/jogos"): Define que todos os pedidos
 * para esta classe devem começar com "/jogos".
 * Ex: http://localhost:8080/jogos
 */
@RestController
@RequestMapping("/jogos")
public class JogoController {

    /**
     * @Autowired: Injeção de Dependência do JogoRepository.
     */
    @Autowired
    private JogoRepository jogoRepository;

    /**
     * --- NOVO REPOSITORY INJETADO ---
     * @Autowired: Pede ao Spring uma instância do CategoriaRepository
     * para que possamos buscar categorias.
     */
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * --- MÉTODO 1 (O antigo @GetMapping) ---
     * @GetMapping: Mapeia pedidos GET para http://localhost:8080/jogos
     * @return Uma lista de todos os jogos no banco.
     */
    @GetMapping
    public List<Jogo> listarTodosJogos() {
        return jogoRepository.findAll();
    }

    /**
     * --- MÉTODO 2 (O antigo @PostMapping) ---
     * @PostMapping: Mapeia pedidos POST para http://localhost:8080/jogos
     * @param jogo O JSON do novo jogo a ser criado.
     * @return O jogo que acabou de ser salvo (com o ID).
     */
    @PostMapping
    public Jogo adicionarJogo(@RequestBody Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    /**
     * --- MÉTODO 3 (O NOVO MÉTODO QUE VOCÊ PEDIU) ---
     * @PostMapping("/{jogoId}/adicionarCategoria/{categoriaId}")
     * Este método liga um Jogo (pelo ID) a uma Categoria (pelo ID).
     * Exemplo de URL: POST http://localhost:8080/jogos/1/adicionarCategoria/2
     *
     * @param jogoId O ID do Jogo (vindo da URL).
     * @param categoriaId O ID da Categoria (vindo da URL).
     * @return O Jogo atualizado com a nova categoria na sua lista.
     */
    @PostMapping("/{jogoId}/adicionarCategoria/{categoriaId}")
    public Jogo adicionarCategoriaAoJogo(
        @PathVariable Long jogoId,
        @PathVariable Long categoriaId
    ) {
        // 1. Busca o Jogo no banco de dados pelo ID
        Optional<Jogo> jogoOpt = jogoRepository.findById(jogoId);
        if (jogoOpt.isEmpty()) {
            throw new RuntimeException("Jogo não encontrado!");
        }

        // 2. Busca a Categoria no banco de dados pelo ID
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        if (categoriaOpt.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada!");
        }

        // 3. Pega os objetos reais de dentro do "Optional"
        Jogo jogo = jogoOpt.get();
        Categoria categoria = categoriaOpt.get();

        // 4. Adiciona a Categoria encontrada à lista de categorias do Jogo
        jogo.getCategorias().add(categoria);

        // 5. Salva o Jogo atualizado. O JPA atualizará a tabela de junção.
        return jogoRepository.save(jogo);
    }
}