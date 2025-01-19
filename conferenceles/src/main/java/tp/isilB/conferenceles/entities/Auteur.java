package tp.isilB.conferenceles.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"soumissions"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Auteur extends utilisateur{
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soumission> soumissions= new ArrayList<>();

    public Auteur(String nom, String prenom, String email) {
        super(null, nom, prenom, email, new HashSet<>(Set.of("Auteur")));
    }

}


