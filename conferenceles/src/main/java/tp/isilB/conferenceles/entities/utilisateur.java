package tp.isilB.conferenceles.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true) // Visible=true pour inclure le champ "type" dans la sérialisation
@JsonSubTypes({
        @JsonSubTypes.Type(value = Auteur.class, name = "Auteur"),
        @JsonSubTypes.Type(value = Editeur.class, name = "Editeur"),
        @JsonSubTypes.Type(value = Evaluateur.class, name = "Evaluateur")
})
public abstract class utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "utilisateur_roles", joinColumns = @JoinColumn(name = "utilisateur_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();



}
