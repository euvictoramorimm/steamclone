package br.com.plataforma.steamclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.plataforma.steamclone.model.*;
import br.com.plataforma.steamclone.repository.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompraService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JogoRepository jogoRepository;
    
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private N8nService n8nService; // <-- O mensageiro

    public void realizarCompra(Usuario usuario, Long jogoId) {
        Jogo jogo = jogoRepository.findById(jogoId)
            .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        // 1. Verifica se já tem o jogo
        if (usuario.getBiblioteca().contains(jogo)) {
            return; // Já tem, não faz nada
        }

        // 2. Adiciona na Biblioteca
        usuario.getBiblioteca().add(jogo);
        usuarioRepository.save(usuario);

        // 3. Cria o Recibo (Compra)
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setJogos(List.of(jogo));
        compra.setDataCompra(LocalDateTime.now());
        compra.setValorTotal(jogo.getPreco());
        compra.setStatus("CONCLUÍDA");
        compra.setCodigoConfirmacao(UUID.randomUUID().toString());
        
        compraRepository.save(compra);

        // 4. Avisa o n8n!
        n8nService.notificarCompra(usuario, jogo);
    }
}