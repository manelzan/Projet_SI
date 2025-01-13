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

    @OneToMany(mappedBy = "evaluateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();


}
