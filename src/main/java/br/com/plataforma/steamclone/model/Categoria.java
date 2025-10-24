package br.com.plataforma.steamclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import jakarta.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore; 


/**
 * @Entity: Define que esta classe é uma tabela.
 * @Table(name = "categorias"): Nome da tabela no banco.
 * @Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor: Lombok.
 */
@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    /**
     * @ManyToMany(mappedBy = "categorias"):
     * 'mappedBy' diz ao JPA: "Esta é a outra ponta do relacionamento
     * que JÁ FOI DEFINIDO no atributo 'categorias' da classe Jogo."
     * Isto impede o JPA de criar uma *segunda* tabela de junção.
     * * @JsonIgnore: BÓNUS IMPORTANTE! Impede um "loop infinito"
     * (Jogo tem Categoria, que tem Jogo, que tem Categoria...)
     * quando o Spring tenta converter os dados para JSON.
     */
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore // Impede o loop infinito ao listar
    private List<Jogo> jogos;
}


