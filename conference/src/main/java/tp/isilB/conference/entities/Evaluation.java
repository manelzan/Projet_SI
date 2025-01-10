package tp.isilB.conference.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="soumission_id",nullable=false)
    @NotNull(message="la soumission es obligatoire")
    private Soumission soumission;
    @ManyToOne
    @JoinColumn(name="evaluateur-id",nullable=false)
    @NotNull(message = "l'evaluateur est obligatoire")
    private Evaluateur evaluateur;
    @NotNull(message="La note est obligatoire")
    @Min(1)
    @Max(10)
    private Integer note;// note de l'evaluation (de 1 a 10)
    @NotEmpty(message="les commentaires sont obligatoires")
    private String commentaires;
    @NotEmpty(message="l'etat de l'evaluation est obligatoire ")
    private String etat;
}
