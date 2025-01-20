package tp.isilB.conferenceles.DTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EvaluationCreationDTO {
    @NotNull(message = "L'ID de l'évaluateur est obligatoire")
    private Long evaluateurId;

    @NotNull(message = "L'ID de la soumission est obligatoire")
    private Long soumissionId;

    @NotNull
    @Min(1)
    @Max(10)
    private int note;

    @NotEmpty(message = "Les commentaires sont obligatoires")
    private String commentaires;

    @NotEmpty(message = "L'état est obligatoire")
    private String etat;

}
