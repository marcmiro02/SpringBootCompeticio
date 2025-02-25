package org.example.springbootstripe.controller;

import org.example.springbootstripe.services.PagamentCrearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagamentCrearController {

    @Autowired
    private PagamentCrearService pagamentCrearService;

    @GetMapping("/pagamentsCrear/crear")
    public String mostrarPaginaPagament(@RequestParam("id_competicio") Integer idCompeticio,Model model) {
        model.addAttribute("idCompeticio", idCompeticio);
        model.addAttribute("preu", 10);
        return "pagament_crear"; // Nombre de la vista HTML
    }
}

