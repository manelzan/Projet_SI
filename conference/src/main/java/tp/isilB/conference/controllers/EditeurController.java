package tp.isilB.conference.controllers;


import tp.isilB.conference.entities.Editeur;
import tp.isilB.conference.repositories.EditeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editeurs")
public class EditeurController {
    @Autowired
    private EditeurRepository editeurRepository;

    @GetMapping
    public List<Editeur> getAllEditeurs() {
        return editeurRepository.findAll();
    }

    @PostMapping
    public Editeur createEditeur(@RequestBody Editeur editeur) {
        return editeurRepository.save(editeur);
    }
}
