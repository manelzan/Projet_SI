package tp.isilB.conference.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Auteur extends utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Le nom ne peut pas être vide")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotEmpty(message = "Le prénom ne peut pas être vide")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @NotEmpty(message = "Les informations ne peuvent pas être vides")
    @Size(max = 256, message = "Les informations ne doivent pas dépasser 256 caractères")
    private String infos;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soumission> soumissions;

    public Auteur(String nom, String prenom, String infos) {
        this.nom = nom;
        this.prenom = prenom;
        this.infos = infos;
    };
}

