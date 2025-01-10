package tp.isilB.conference.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conference.entities.Auteur;
import tp.isilB.conference.entities.utilisateur;
import tp.isilB.conference.repositories.utilisateurRepository;
import tp.isilB.conference.entities.Editeur;
import tp.isilB.conference.entities.Evaluateur;
import tp.isilB.conference.repositories.AuteurRepository;
import tp.isilB.conference.repositories.EditeurRepository;
import tp.isilB.conference.repositories.EvaluateurRepository;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
public class utilisateurController {
    @Autowired
    private utilisateurRepository utilisateurRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private EditeurRepository editeurRepository;

    @Autowired
    private EvaluateurRepository evaluateurRepository;


    @PostMapping
    public ResponseEntity<utilisateur> createUtilisateur(@RequestBody utilisateur utilisateur) {
        utilisateur savedUser = utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(savedUser);
    }
    // Ajouter un auteur
    @PostMapping("/auteur")
    public ResponseEntity<Auteur> ajouterAuteur(@RequestBody Auteur auteur) {
        Auteur savedAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(savedAuteur);
    }

    // Ajouter un éditeur
    @PostMapping("/editeur")
    public ResponseEntity<Editeur> ajouterEditeur(@RequestBody Editeur editeur) {
        Editeur savedEditeur = editeurRepository.save(editeur);
        return ResponseEntity.ok(savedEditeur);
    }

    // Ajouter un évaluateur
    @PostMapping("/evaluateur")
    public ResponseEntity<Evaluateur> ajouterEvaluateur(@RequestBody Evaluateur evaluateur) {
        Evaluateur savedEvaluateur = evaluateurRepository.save(evaluateur);
        return ResponseEntity.ok(savedEvaluateur);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<utilisateur> addRoleToUtilisateur(@PathVariable Long id, @RequestBody String role) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.getRoles().add(role);
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.notFound().build();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        return utilisateurOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
