package tp.isilB.conference.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evaluateur extends utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @OneToMany(mappedBy = "evaluateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations;

    public Long getId() {
        return id;
    }
}
