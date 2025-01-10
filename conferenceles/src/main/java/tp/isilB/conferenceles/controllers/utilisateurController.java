package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.entities.utilisateur;
import tp.isilB.conferenceles.repositries.utilisateurRepository;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.repositries.AuteurRepository;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
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