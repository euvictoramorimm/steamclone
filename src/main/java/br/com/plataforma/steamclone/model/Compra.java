package br.com.plataforma.steamclone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; // Import para gerar códigos únicos

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCompra;
    
    private Double valorTotal;

    // --- 5 NOVOS ATRIBUTOS ---
    private String metodoPagamento;
    private String status; // Ex: "PENDENTE", "CONCLUÍDA", "CANCELADA"
    private String codigoConfirmacao; // Um código único (ex: UUID)
    private Double descontoAplicado;
    private String notaFiscal; // Link para a NF-e

    // --- RELACIONAMENTOS ---
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "compra_jogos", 
        joinColumns = @JoinColumn(name = "compra_id"), 
        inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private List<Jogo> jogos;
}