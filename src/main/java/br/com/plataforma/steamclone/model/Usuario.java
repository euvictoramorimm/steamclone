package br.com.plataforma.steamclone.model;

import java.util.List;
import java.time.LocalDate; // Import para data
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

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeDeUsuario;
    private String email;
    private String senha;

    // --- 5 NOVOS ATRIBUTOS ---
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String pais;
    private Double saldoCarteira;
    private String urlAvatar;
    
    // --- RELACIONAMENTO (Biblioteca) ---
    @ManyToMany
    @JoinTable(
        name = "usuario_biblioteca",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private List<Jogo> biblioteca;
}