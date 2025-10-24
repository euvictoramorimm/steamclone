package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import java.util.List;

/**
 * @RestController: Define que esta classe é um Controlador REST.
 * @RequestMapping("/categorias"): Define o caminho base da URL.
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    // Injeta o Repository
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * @GetMapping: Mapeia pedidos GET para /categorias
     * @return Lista de todas as categorias.
     */
    @GetMapping
    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    /**
     * @PostMapping: Mapeia pedidos POST para /categorias
     * @param categoria O JSON da nova categoria a ser criada.
     * @return A categoria salva (com o ID).
     */
    @PostMapping
    public Categoria adicionarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}