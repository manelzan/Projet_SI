package tp.isilB.conferenceles.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDTO {
    private Long id;
    private Long evaluateurId;   // ID de l'évaluateur
    private Long soumissionId;  // ID de la soumission
    private int note;           // Note donnée
    private String commentaires; // Commentaires
    private String etat;
}
