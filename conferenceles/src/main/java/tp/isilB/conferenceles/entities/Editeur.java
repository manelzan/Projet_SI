package tp.isilB.conferenceles.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Editeur extends utilisateur {
    @OneToMany(mappedBy = "editeur", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonIgnore
    @JsonManagedReference
    private List<Conference> conferences;

    public Editeur(String nom, String prenom, String email) {
        super(null, nom, prenom, email, Set.of("Editeur"));
    }
}
