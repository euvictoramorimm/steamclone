package br.com.plataforma.steamclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.plataforma.steamclone.model.Categoria;
import br.com.plataforma.steamclone.model.Jogo;
import br.com.plataforma.steamclone.repository.CategoriaRepository;
import br.com.plataforma.steamclone.repository.JogoRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Só carrega se o banco estiver vazio
        if (jogoRepository.count() > 0) {
            return; 
        }

        System.out.println("--- CARREGANDO JOGOS COM CAPAS REAIS DA STEAM ---");

        // --- Criar Categorias ---
        Categoria rpg = new Categoria();
        rpg.setNome("RPG");
        rpg.setAtiva(true);
        rpg.setDataCriacao(LocalDate.now());
        
        Categoria acao = new Categoria();
        acao.setNome("Ação");
        acao.setAtiva(true);
        acao.setDataCriacao(LocalDate.now());

        categoriaRepository.saveAll(List.of(rpg, acao));
        
        // --- Jogos com Imagens Reais ---
        
        Jogo jogo1 = new Jogo();
        jogo1.setTitulo("Dragon's Dogma 2");
        jogo1.setDescricao("Uma grande aventura de fantasia.");
        jogo1.setPreco(299.90);
        jogo1.setDataLancamento(LocalDate.of(2024, 3, 22));
        jogo1.setDesenvolvedor("Capcom");
        // Capa Oficial
        jogo1.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2054970/header.jpg");
        jogo1.setCategorias(List.of(rpg, acao));

        Jogo jogo2 = new Jogo();
        jogo2.setTitulo("Elden Ring");
        jogo2.setDescricao("Levante-se, Maculado...");
        jogo2.setPreco(229.90);
        jogo2.setDataLancamento(LocalDate.of(2022, 2, 25));
        jogo2.setDesenvolvedor("FromSoftware");
        // Capa Oficial
        jogo2.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/header.jpg");
        jogo2.setCategorias(List.of(rpg, acao));

        Jogo jogo3 = new Jogo();
        jogo3.setTitulo("Cyberpunk 2077");
        jogo3.setDescricao("Uma história de ação e aventura...");
        jogo3.setPreco(199.90);
        jogo3.setDataLancamento(LocalDate.of(2020, 12, 10));
        jogo3.setDesenvolvedor("CD Projekt Red");
        // Capa Oficial
        jogo3.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/header.jpg");
        jogo3.setCategorias(List.of(rpg, acao));
        
        Jogo jogo4 = new Jogo();
        jogo4.setTitulo("Stardew Valley");
        jogo4.setDescricao("Você herdou a velha quinta do seu avô...");
        jogo4.setPreco(24.99);
        jogo4.setDataLancamento(LocalDate.of(2016, 2, 26));
        jogo4.setDesenvolvedor("ConcernedApe");
        // Capa Oficial
        jogo4.setUrlCapa("https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/header.jpg");
        jogo4.setCategorias(List.of(rpg));

        jogoRepository.saveAll(List.of(jogo1, jogo2, jogo3, jogo4));
        System.out.println("--- JOGOS CARREGADOS ---");
    }
}