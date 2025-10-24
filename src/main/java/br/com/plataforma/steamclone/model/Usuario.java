// Define que esta classe pertence ao pacote 'model'
package br.com.plataforma.steamclone.model;

// Importações do JPA (o "Tradutor" - Jakarta)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Entity: Diz ao JPA que esta classe é uma tabela no banco.
 * @Table(name = "usuarios"): Nome da tabela no banco (boa prática).
 * @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor: Lombok!
 * Ele cria os getters, setters e construtores por nós.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    /**
     * @Id: Define que este é o campo de Chave Primária.
     * @GeneratedValue(strategy = GenerationType.IDENTITY):
     * Define que o banco de dados vai gerar o ID automaticamente (1, 2, 3...).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Usamos Long para o ID (como discutimos).
    
    private String nomeDeUsuario;
    private String email;
    private String senha;

}