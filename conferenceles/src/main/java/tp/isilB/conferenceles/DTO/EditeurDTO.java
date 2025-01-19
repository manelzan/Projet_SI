package tp.isilB.conferenceles.DTO;

import java.util.List;

import java.util.Date;

import lombok.*;

@Data
@NoArgsConstructor // Constructeur sans arguments
@AllArgsConstructor // Constructeur avec tous les champs
@Getter
@Setter

public class EditeurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private List<ConferenceDTO> conferences;

   }