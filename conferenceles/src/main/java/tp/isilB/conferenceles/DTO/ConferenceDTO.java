package tp.isilB.conferenceles.DTO;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ConferenceDTO {
    private Long id;
    private String titre;
    private LocalDate dateDebut; // Remplacé par LocalDate
    private LocalDate dateFin;   // Remplacé par LocalDate
    private String thematique;
    private String etat;

    // Constructeur avec LocalDate
    public ConferenceDTO(Long id, String titre, LocalDate dateDebut, LocalDate dateFin, String thematique, String etat) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.thematique = thematique;
        this.etat = etat;
    }
}