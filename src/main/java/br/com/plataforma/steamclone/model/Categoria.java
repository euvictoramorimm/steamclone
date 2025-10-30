package br.com.plataforma.steamclone.model;

import java.util.List;
import java.time.LocalDate; // Import para data
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // --- 4 NOVOS ATRIBUTOS ---
    private String urlIcone;
    private String urlBanner;
    private Boolean ativa; // Usamos Boolean (Objeto) para poder ser nulo
    private LocalDate dataCriacao;

    // --- RELACIONAMENTO ---
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    private List<Jogo> jogos;
}