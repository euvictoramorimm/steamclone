// Define que esta classe pertence ao pacote 'model'
package br.com.plataforma.steamclone.model;

// Importações do JPA (o "Tradutor" - Jakarta)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

/**
 * @Entity: Diz ao JPA que esta classe é uma tabela no banco.
 * @Table(name = "jogos"): Nome da tabela no banco (boa prática).
 * @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor: Lombok!
 * Ele cria os getters, setters e construtores por nós.
 */
@Entity
@Table(name = "jogos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    /**
     * @Id: Define que este é o campo de Chave Primária.
     * @GeneratedValue(strategy = GenerationType.IDENTITY):
     * Define que o banco de dados vai gerar o ID automaticamente (1, 2, 3...).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Usamos Long para o ID (como discutimos).

    private String titulo;
    private String genero;
    private double preco;

    /**
     * @ManyToMany: Muitos Jogos para Muitas Categorias.
     * @JoinTable: Cria a tabela de junção "jogo_categorias".
     * joinColumns = @JoinColumn(name = "jogo_id"): Coluna que referencia esta classe (Jogo).
     * inverseJoinColumns = @JoinColumn(name = "categoria_id"): Coluna que referencia a outra (Categoria).
     */
    @ManyToMany
    @JoinTable(
        name = "jogo_categorias",
        joinColumns = @JoinColumn(name = "jogo_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
}
