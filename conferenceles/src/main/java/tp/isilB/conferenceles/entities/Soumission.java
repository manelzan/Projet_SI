package tp.isilB.conferenceles.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Soumission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le titre est obligatoire")
    private String titre;

    @NotEmpty(message = "Le résumé est obligatoire")
    @Size(max = 500, message = "Le résumé ne doit pas dépasser 500 caractères")
    private String resume;

    @NotEmpty(message = "Le document PDF est obligatoire")
    private String documentPdf;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    @NotNull(message = "L'auteur est obligatoire")
    private Auteur auteur;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    @NotNull(message = "La conférence est obligatoire")
    private Conference conference;

    @ManyToMany
    @JoinTable(
            name = "soumission_evaluateurs",
            joinColumns = @JoinColumn(name = "soumission_id"),
            inverseJoinColumns = @JoinColumn(name = "evaluateur_id")
    )
    private List<Evaluateur> evaluateurs = new ArrayList<>();

    @OneToMany(mappedBy = "soumission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();


    public Soumission(Long id, String titre, String resume, String documentPdf, Auteur auteur, Conference conference, List<Evaluateur> evaluateurs) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.documentPdf = documentPdf;
        this.auteur = auteur;
        this.conference = conference;
        this.evaluateurs = (evaluateurs != null) ? evaluateurs : new ArrayList<>();
    }
}