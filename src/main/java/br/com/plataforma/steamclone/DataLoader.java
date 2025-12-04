package br.com.plataforma.steamclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Importante!

import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.model.Usuario; // <-- Importante!
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import br.com.plataforma.steamclone.repository.JogoRepository;
import br.com.plataforma.steamclone.repository.UsuarioRepository; // <-- Importante!

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 👇 NOVAS FERRAMENTAS INJETADAS 👇
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // --- 1. CARREGAR JOGOS (Só se não tiver nenhum) ---
        if (jogoRepository.count() == 0) {
            System.out.println("--- CARREGANDO JOGOS COM CAPAS REAIS ---");

            Categoria rpg = new Categoria();
            rpg.setNome("RPG");
            rpg.setAtiva(true);
            rpg.setDataCriacao(LocalDate.now());
            
            Categoria acao = new Categoria();
            acao.setNome("Ação");
            acao.setAtiva(true);
            acao.setDataCriacao(LocalDate.now());

            categoriaRepository.saveAll(List.of(rpg, acao));
            
            Jogo jogo1 = new Jogo();
            jogo1.setTitulo("Dragon's Dogma 2");
            jogo1.setDescricao("Uma grande aventura de fantasia.");
            jogo1.setPreco(299.90);
            jogo1.setDataLancamento(LocalDate.of(2024, 3, 22));
            jogo1.setDesenvolvedor("Capcom");
            jogo1.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2054970/header.jpg");
            jogo1.setCategorias(List.of(rpg, acao));

            Jogo jogo2 = new Jogo();
            jogo2.setTitulo("Elden Ring");
            jogo2.setDescricao("Levante-se, Maculado...");
            jogo2.setPreco(229.90);
            jogo2.setDataLancamento(LocalDate.of(2022, 2, 25));
            jogo2.setDesenvolvedor("FromSoftware");
            jogo2.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/header.jpg");
            jogo2.setCategorias(List.of(rpg, acao));

            Jogo jogo3 = new Jogo();
            jogo3.setTitulo("Cyberpunk 2077");
            jogo3.setDescricao("RPG de ação e aventura...");
            jogo3.setPreco(199.90);
            jogo3.setDataLancamento(LocalDate.of(2020, 12, 10));
            jogo3.setDesenvolvedor("CD Projekt Red");
            jogo3.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/header.jpg");
            jogo3.setCategorias(List.of(rpg, acao));
            
            Jogo jogo4 = new Jogo();
            jogo4.setTitulo("Stardew Valley");
            jogo4.setDescricao("Sua nova fazenda...");
            jogo4.setPreco(24.99);
            jogo4.setDataLancamento(LocalDate.of(2016, 2, 26));
            jogo4.setDesenvolvedor("ConcernedApe");
            jogo4.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/header.jpg");
            jogo4.setCategorias(List.of(rpg));

            jogoRepository.saveAll(List.of(jogo1, jogo2, jogo3, jogo4));
        }

        // --- 2. CRIAR O ADMIN (Isso roda sempre para garantir que o Admin existe) ---
        
        // Verifica se o email do admin já existe no banco
        if (usuarioRepository.findByEmail("admin@steam.com").isEmpty()) {
            
            Usuario admin = new Usuario();
            admin.setNomeCompleto("Victor de Amorim (Admin)");
            admin.setEmail("admin@steam.com");
            // AQUI ESTÁ O TRUQUE: Usamos o encoder injetado lá em cima
            admin.setSenha(passwordEncoder.encode("admin123")); 
            admin.setPapel("ADMIN"); // Define que ele manda na loja
            admin.setSaldoCarteira(99999.0); // Admin rico!
            
            usuarioRepository.save(admin);
            
            System.out.println("👑 ADMIN CRIADO COM SUCESSO: admin@steam.com / admin123");
        } else {
            System.out.println("✅ Admin já existe no banco.");
        }
    }
}