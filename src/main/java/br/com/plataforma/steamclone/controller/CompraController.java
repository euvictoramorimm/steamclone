package br.com.plataforma.steamclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.plataforma.steamclone.model.Compra;
import br.com.plataforma.steamclone.repository.CompraRepository;
import java.util.List;

/**
 * @RestController: Define que esta classe é um Controlador REST.
 * @RequestMapping("/compras"): Define o caminho base da URL.
 */
@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    /**
     * @GetMapping: Lista todas as compras feitas.
     * @return Lista de todas as compras.
     */
    @GetMapping
    public List<Compra> listarTodasCompras() {
        return compraRepository.findAll();
    }

    /**
     * @PostMapping: Cria uma nova compra.
     * @param compra O JSON da nova compra (deve incluir o
     * 'usuario_id' e a lista de 'jogo_id's).
     * @return A compra salva (com o ID).
     */
    @PostMapping
    public Compra adicionarCompra(@RequestBody Compra compra) {
        // Para uma aplicação real, aqui nós calcularíamos o valor total
        // e definiríamos a dataCompra, mas para "rodar",
        // um save simples é o suficiente.
        return compraRepository.save(compra);
    }
}