package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.reservationsspringboot.model.Artist;
import be.iccbxl.pid.reservationsspringboot.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ArtistController {

    @Autowired
    ArtistService service;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("title", "Liste des artistes");
        return "artist/index";
    }

    @GetMapping("/artists/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        model.addAttribute("title", "Fiche d'un artiste");
        return "artist/show";
    }

    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);

        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artists/" + artist.getId());
        }

        return "artist/edit";
    }

    @PutMapping("/artists/{id}/edit")
    public String update(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult,
            @PathVariable("id") long id, Model model, RedirectAttributes redirAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la modification de l'artiste !");
            return "artist/edit";
        }

        Artist existing = service.getArtist(id);
        if (existing == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Artiste introuvable !");
            return "redirect:/artists";
        }

        artist.setId(id);
        service.updateArtist(id, artist);
        redirAttrs.addFlashAttribute("successMessage", "Artiste modifié avec succès.");

        return "redirect:/artists/" + artist.getId();
    }

    @GetMapping("/artists/create")
    public String create(Model model) {
        if (!model.containsAttribute("artist")) {
            model.addAttribute("artist", new Artist());
        }
        return "artist/create";
    }

    @PostMapping("/artists/create")
    public String store(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult,
            Model model, RedirectAttributes redirAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création de l'artiste !");
            return "artist/create";
        }

        service.addArtist(artist);
        redirAttrs.addFlashAttribute("successMessage", "Artiste créé avec succès.");

        return "redirect:/artists/" + artist.getId();
    }

    @DeleteMapping("/artists/{id}")
    public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirAttrs) {
        Artist existing = service.getArtist(id);

        if (existing != null) {
            service.deleteArtist(id);
            redirAttrs.addFlashAttribute("successMessage", "Artiste supprimé avec succès.");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Échec de la suppression de l'artiste !");
        }

        return "redirect:/artists";
    }
}