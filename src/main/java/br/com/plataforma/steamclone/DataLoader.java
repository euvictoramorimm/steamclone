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
        
        // Só executa se o banco estiver vazio
        if (jogoRepository.count() > 0) {
            System.out.println("Banco de dados já populado. Ignorando DataLoader.");
            return; 
        }

        System.out.println("--- INICIANDO CARGA DE DADOS DE TESTE (com imagens) ---");

        // --- Criar Categorias ---
        Categoria rpg = new Categoria();
        rpg.setNome("RPG");
        rpg.setDescricao("Jogos de Role-Playing");
        rpg.setAtiva(true);
        rpg.setDataCriacao(LocalDate.now());
        
        Categoria acao = new Categoria();
        acao.setNome("Ação");
        acao.setDescricao("Jogos de combate e reflexos rápidos");
        acao.setAtiva(true);
        acao.setDataCriacao(LocalDate.now());

        // Salva as categorias PRIMEIRO
        categoriaRepository.saveAll(List.of(rpg, acao));
        System.out.println("Categorias 'RPG' e 'Ação' salvas.");
        
        // --- Criar Jogos (Agora com imagens) ---
        
        // Jogo 1 (RPG)
        Jogo jogo1 = new Jogo();
        jogo1.setTitulo("Dragon's Dogma 2");
        jogo1.setDescricao("Uma grande aventura de fantasia.");
        jogo1.setPreco(299.90);
        jogo1.setDataLancamento(LocalDate.of(2024, 3, 22));
        jogo1.setDesenvolvedor("Capcom");
        jogo1.setPublicadora("Capcom");
        // Imagem de placeholder
        jogo1.setUrlCapa("https://placehold.co/600x340/1b1a21/a29bfe?text=Dragon's+Dogma+2");
        // Liga o jogo à categoria
        jogo1.setCategorias(List.of(rpg));

        // Jogo 2 (Ação/RPG)
        Jogo jogo2 = new Jogo();
        jogo2.setTitulo("Elden Ring");
        jogo2.setDescricao("Levante-se, Maculado...");
        jogo2.setPreco(249.90);
        jogo2.setDataLancamento(LocalDate.of(2022, 2, 25));
        jogo2.setDesenvolvedor("FromSoftware");
        jogo2.setPublicadora("Bandai Namco");
        // Imagem de placeholder
        jogo2.setUrlCapa("https://placehold.co/600x340/1b1a21/a29bfe?text=Elden+Ring");
        // Liga o jogo às duas categorias
        jogo2.setCategorias(List.of(rpg, acao));

        // Jogo 3 (Ação)
        Jogo jogo3 = new Jogo();
        jogo3.setTitulo("Cyberpunk 2077");
        jogo3.setDescricao("Uma história de ação e aventura...");
        jogo3.setPreco(199.90);
        jogo3.setDataLancamento(LocalDate.of(2020, 12, 10));
        jogo3.setDesenvolvedor("CD Projekt Red");
        jogo3.setPublicadora("CD Projekt");
        // Imagem de placeholder
        jogo3.setUrlCapa("https://placehold.co/600x340/1b1a21/a29bfe?text=Cyberpunk+2077");
        jogo3.setCategorias(List.of(rpg, acao));
        
        // Jogo 4 (Simulação/RPG)
        Jogo jogo4 = new Jogo();
        jogo4.setTitulo("Stardew Valley");
        jogo4.setDescricao("Você herdou a velha quinta do seu avô...");
        jogo4.setPreco(24.99);
        jogo4.setDataLancamento(LocalDate.of(2016, 2, 26));
        jogo4.setDesenvolvedor("ConcernedApe");
        jogo4.setPublicadora("ConcernedApe");
        // Imagem de placeholder
        jogo4.setUrlCapa("https://placehold.co/600x340/1b1a21/a29bfe?text=Stardew+Valley");
        jogo4.setCategorias(List.of(rpg));

        // Salva todos os jogos
        jogoRepository.saveAll(List.of(jogo1, jogo2, jogo3, jogo4));
        System.out.println("4 jogos de teste salvos.");
        
        System.out.println("--- CARGA DE DADOS CONCLUÍDA ---");
    }
}