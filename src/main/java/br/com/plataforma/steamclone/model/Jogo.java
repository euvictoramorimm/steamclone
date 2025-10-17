// Define que esta classe pertence ao nosso novo pacote 'model'
package br.com.plataforma.steamclone.model;

// --- Importações das Ferramentas ---
// Precisamos de importar as anotações do JPA e do Lombok

// JPA (Java Persistence API) - O "Tradutor"
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Lombok - O "Ajudante"
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * --- Documentação da Classe ---
 * * @Entity: Anotação mais importante!
 * Diz ao Spring Data JPA: "Isto não é uma classe qualquer.
 * Isto é uma ENTIDADE, um 'molde' que corresponde a uma tabela
 * no banco de dados."
 * * @Table(name = "jogos"): Opcional, mas boa prática.
 * Diz ao JPA: "Quando criar a tabela no banco de dados,
 * chame-a de 'jogos' (no plural)." Se não usarmos isto,
 * o JPA usaria o nome da classe, "jogo".
 * * @Getter / @Setter / @NoArgsConstructor / @AllArgsConstructor:
 * Anotações do LOMBOK. Elas escrevem o código por nós!
 * @Getter: Cria todos os métodos 'get' (ex: getTitulo(), getPreco()).
 * @Setter: Cria todos os métodos 'set' (ex: setTitulo(...), setPreco(...)).
 * @NoArgsConstructor: Cria um construtor vazio (ex: public Jogo() {}).
 * O JPA PRECISA disto para funcionar.
 * @AllArgsConstructor: Cria um construtor com todos os atributos
 * (ex: public Jogo(Long id, String titulo, ...)).
 */
@Entity
@Table(name = "jogos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    // --- Atributos (As Colunas da Tabela) ---

    /**
     * @Id: Anotação crucial.
     * Diz ao JPA: "Este atributo é a Chave Primária (Primary Key)
     * da tabela." É o identificador único de cada jogo.
     * * @GeneratedValue(strategy = GenerationType.IDENTITY):
     * Diz ao JPA: "Eu não vou definir este ID manualmente.
     * Deixe o banco de dados (H2, PostgreSQL, etc.) gerir isto
     * para mim, de forma auto-incremental (1, 2, 3...)."
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se não colocarmos nenhuma anotação, o JPA assume
    // que o nome da coluna é igual ao nome do atributo (ex: "titulo").
    private String titulo;

    private String genero;

    private double preco;

    // Não precisamos de mais nada!
    // O Lombok vai criar os Getters, Setters e Construtores em "runtime".
    // O JPA vai ler as anotações e preparar-se para criar a tabela.
}