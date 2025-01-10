package tp.isilB.conference.entities;


import jakarta.validation.Valid;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le titre ne peut pas être vide")
    private String titre;

    @Column(nullable = false)
// TODO: Réactiver la validation pour la production
    @FutureOrPresent(message = "La date de début doit être dans le futur ou aujourd'hui")
    private LocalDate dateDebut;


    @Column(nullable = false)
    @FutureOrPresent(message = "La date de début doit être dans le futur ou aujourd'hui")
    private LocalDate dateFin;

    @NotEmpty(message = "La thématique ne peut pas être vide")
    private String thematique;

    @NotEmpty(message = "L'état ne peut pas être vide")
    private String etat;

    @ManyToOne
    @JoinColumn(name = "editeur_id", nullable = false)
    @NotNull(message = "L'éditeur est obligatoire")
    private Editeur editeur;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soumission> soumissions = new ArrayList<>();
}
