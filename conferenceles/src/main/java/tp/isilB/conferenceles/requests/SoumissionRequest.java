package tp.isilB.conferenceles.requests;

import lombok.Data;

import java.util.List;

@Data

public class SoumissionRequest {
    private String titre;
    private String resume;
    private byte[] documentPdf; // Contenu du fichier PDF (utilisez base64 ou autre selon votre configuration)
    private Long conferenceId;  // ID de la conférence liée
    private List<Long> auteurIds; // Liste des IDs des auteurs
}
