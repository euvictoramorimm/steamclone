package br.com.plataforma.steamclone.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "jogos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String genero;
    private double preco;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private LocalDate dataLancamento;
    private String urlCapa;

    // --- 5 NOVOS ATRIBUTOS ---
    private String desenvolvedor;
    private String publicadora;
    private Double avaliacaoMedia; // Usamos Double (Objeto) para poder ser nulo
    private Integer classificacaoIndicativa; // Usamos Integer (Objeto)
    private String urlTrailer;

    // --- RELACIONAMENTOS ---
    @ManyToMany
    @JoinTable(
        name = "jogo_categorias",
        joinColumns = @JoinColumn(name = "jogo_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
    
    @ManyToMany(mappedBy = "biblioteca")
    @JsonIgnore
    private List<Usuario> proprietarios;
}