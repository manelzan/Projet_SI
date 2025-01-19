package tp.isilB.conferenceles.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"evaluateurs", "auteurs"})
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
    @JoinColumn(name = "conference_id", nullable = false)
    @NotNull(message = "La conférence est obligatoire")
    private Conference conference;

    @ManyToOne
    private Auteur auteur;

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    @ManyToMany
    @JoinTable(
            name = "soumission_evaluateurs",
            joinColumns = @JoinColumn(name = "soumission_id"),
            inverseJoinColumns = @JoinColumn(name = "evaluateur_id")
    )
    private List<Evaluateur> evaluateurs = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "soumission_auteurs",
            joinColumns = @JoinColumn(name = "soumission_id"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private List<Auteur> auteurs = new ArrayList<>();

    @OneToMany(mappedBy = "soumission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();


    public void addEvaluateur(Evaluateur evaluateur) {
        if (auteurs.contains(evaluateur)) {
            throw new IllegalArgumentException("Un évaluateur ne peut pas évaluer une soumission dont il est auteur.");
        }
        evaluateurs.add(evaluateur);
    }

    public Soumission(Long id, String titre, String resume, String documentPdf, Conference conference, List<Auteur> auteurs) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.documentPdf = documentPdf;
        this.conference = conference;
        this.auteurs = (auteurs != null) ? new ArrayList<>(auteurs) : new ArrayList<>();
    }
}
