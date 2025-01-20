package tp.isilB.conferenceles.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tp.isilB.conferenceles.entities.Auteur;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Evaluateur extends utilisateur {
    @ManyToMany(mappedBy = "evaluateurs")
    private List<Soumission> soumissions;


    public Evaluateur(String nom, String prenom, String email) {
        super(null, nom, prenom, email, new HashSet<>(Set.of("Evaluateur")));
    }

    @ManyToOne // Assumons qu'un évaluateur est associé à un auteur.
    @JoinColumn(name = "auteur_id")
    private Auteur auteur; // L'évaluateur a un auteur associé.

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }
    @OneToMany(mappedBy = "evaluateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();




}
