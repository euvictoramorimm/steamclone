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

/**
 * @Component: Diz ao Spring para "criar" esta classe.
 * CommandLineRunner: É uma interface que diz ao Spring:
 * "Execute o método run() UMA VEZ, assim que a aplicação arrancar."
 *
 * Vamos usar isto para popular o nosso banco de dados H2.
 */
@Component
public class DataLoader implements CommandLineRunner {

    // Precisamos dos repositórios para salvar os dados
    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DADOS DE TESTE ---");

        // --- Criar Categorias ---
        // (Verifica se já não existem para não duplicar,
        // embora com H2 em memória, isto é desnecessário, mas é boa prática)
        if (categoriaRepository.count() == 0) {
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

            categoriaRepository.saveAll(List.of(rpg, acao));
            System.out.println("Categorias 'RPG' e 'Ação' salvas.");
        }
        
        // --- Criar Jogos ---
        if (jogoRepository.count() == 0) {
            Jogo jogo1 = new Jogo();
            jogo1.setTitulo("The Witcher 3: Wild Hunt");
            jogo1.setDescricao("Um jogo de RPG de mundo aberto...");
            jogo1.setPreco(129.90);
            jogo1.setDataLancamento(LocalDate.of(2015, 5, 19));
            jogo1.setDesenvolvedor("CD Projekt Red");
            jogo1.setPublicadora("CD Projekt");
            jogo1.setUrlCapa("https://.../witcher3.jpg");
            
            Jogo jogo2 = new Jogo();
            jogo2.setTitulo("Elden Ring");
            jogo2.setDescricao("Levante-se, Maculado...");
            jogo2.setPreco(249.90);
            jogo2.setDataLancamento(LocalDate.of(2022, 2, 25));
            jogo2.setDesenvolvedor("FromSoftware");
            jogo2.setPublicadora("Bandai Namco");
            jogo2.setUrlCapa("https://.../eldenring.jpg");

            jogoRepository.saveAll(List.of(jogo1, jogo2));
            System.out.println("Jogos 'The Witcher 3' e 'Elden Ring' salvos.");
        }
        
        System.out.println("--- CARGA DE DADOS CONCLUÍDA ---");
    }
}