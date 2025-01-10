package tp.isilB.conferenceles.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluateur_id", nullable = false)
    private Evaluateur evaluateur;

    @ManyToOne
    @JoinColumn(name = "soumission_id", nullable = false)
    private Soumission soumission;

    @NotNull
    @Min(1)
    @Max(10)
    private int note; // Note sur une échelle de 1 à 10

    @Column(length = 1000) // Permet des commentaires détaillés
    private String commentaires;

    @Enumerated(EnumType.STRING)
    private EtatEvaluation etat; // ACCEPTÉE, REJETÉE, EN_REVISION
}

