package tp.isilB.conference.entities;

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

    @ManyToMany
    @JoinTable(
            name = "soumission_auteurs",
            joinColumns = @JoinColumn(name = "soumission_id"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private List<Auteur> auteurs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    @NotNull(message = "La conférence est obligatoire")
    private Conference conference;

    @OneToMany(mappedBy = "soumission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    // Constructeur personnalisé
    public Soumission(String titre, String resume, String documentPdf, List<Auteur> auteurs, Conference conference) {
        this.titre = titre;
        this.resume = resume;
        this.documentPdf = documentPdf;
        this.auteurs = auteurs;
        this.conference = conference;
    }

    // Méthodes utilitaires
    public void addAuteur(Auteur auteur) {
        this.auteurs.add(auteur);
    }

    public void removeAuteur(Auteur auteur) {
        this.auteurs.remove(auteur);
    }

    public void addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setSoumission(this);
    }

    public void removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setSoumission(null);
    }



    public Long getId() {
        return id;
    }
}
