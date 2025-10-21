package br.com.plataforma.steamclone.model;

// Imports do Java (para Lista e Data/Hora)
import java.time.LocalDateTime;
import java.util.List;

// Imports do JPA (Jakarta) - Note os novos!
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

// Imports do Lombok
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

    // Guarda a data e hora exata da compra
    private LocalDateTime dataCompra;
    
    // --- RELACIONAMENTO COM USUÁRIO ---
    /**
     * @ManyToOne: Muitas compras para UM usuário.
     * @JoinColumn(name = "usuario_id"):
     * Isto cria uma coluna na tabela "compras" chamada "usuario_id",
     * que é a Chave Estrangeira (FK) para a tabela "usuarios".
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // --- RELACIONAMENTO COM JOGO ---
    /**
     * @ManyToMany: Muitas compras podem ter muitos jogos.
     * @JoinTable: Como é ManyToMany, o JPA precisa criar uma
     * TERCEIRA tabela (uma tabela de junção) para ligar os dois.
     * * name = "compra_jogos": Nome da nova tabela de junção.
     * joinColumns = @JoinColumn(name = "compra_id"): Coluna que referencia esta classe (Compra).
     * inverseJoinColumns = @JoinColumn(name = "jogo_id"): Coluna que referencia a outra classe (Jogo).
     */
    @ManyToMany
    @JoinTable(
        name = "compra_jogos", 
        joinColumns = @JoinColumn(name = "compra_id"), 
        inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private List<Jogo> jogos;

    // Poderíamos ter um 'private double valorTotal' também,
    // que seria calculado no momento da compra.
}