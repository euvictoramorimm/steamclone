package br.com.plataforma.steamclone.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.model.Usuario;

@Service
public class N8nService {

    // 👇 IMPORTANTE: Coloque o URL do seu Webhook do n8n aqui!
    private static final String N8N_WEBHOOK_URL = "https://n8nprojeto.app.n8n.cloud/webhook/steam-clone";

    private final RestTemplate restTemplate = new RestTemplate();

    // Avisa quando cria conta
    public void notificarCadastro(Usuario usuario) {
        enviarParaN8n("cadastro", usuario, null);
    }

    // Avisa quando compra jogo
    public void notificarCompra(Usuario usuario, Jogo jogo) {
        enviarParaN8n("compra", usuario, jogo);
    }

    private void enviarParaN8n(String tipo, Usuario usuario, Jogo jogo) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("tipo", tipo); // "cadastro" ou "compra"
            payload.put("nome", usuario.getNomeCompleto());
            payload.put("email", usuario.getEmail());
            
            if (jogo != null) {
                payload.put("jogoTitulo", jogo.getTitulo());
                payload.put("jogoPreco", jogo.getPreco());
            }

            restTemplate.postForEntity(N8N_WEBHOOK_URL, payload, String.class);
            System.out.println("✅ N8n notificado: " + tipo);

        } catch (Exception e) {
            System.err.println("⚠️ Erro ao chamar n8n: " + e.getMessage());
        }
    }
}