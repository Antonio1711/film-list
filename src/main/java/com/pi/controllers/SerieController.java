package com.pi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pi.models.Serie;
import com.pi.repository.SerieRepository;


@Controller
public class SerieController {
    
    @Autowired
    private SerieRepository sr;
    
    //Chama o form que cadastra o serie
    
    @RequestMapping("/cadastrarSerie")
    public String form() {
        return "serie/form-serie";
    }
    
    //Post que cadastra o serie
    @RequestMapping(value = "/cadastrarSerie", method = RequestMethod.POST)
    public String form(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarSerie";
        }
        
        sr.save(serie);
        attributes.addFlashAttribute("mensagem", "Serie cadastrado com sucesso!");
        return "redirect:/cadastrarSerie";
    }
    
    // GET que lista os series
    @RequestMapping("/series")
    public ModelAndView listaSeries() {
        ModelAndView mv = new ModelAndView("serie/lista-serie");
        Iterable<Serie> series = sr.findAll();
        mv.addObject("series", series);
        return mv;
    }
    
    // GET que mostra os detalhes do serie
    @RequestMapping("/serie/{codigo}")
    public ModelAndView detalhesSerie(@PathVariable("codigo") long codigo) {
        Serie serie = sr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("serie/detalhes-serie");
        mv.addObject("serie", serie);

        return mv;
    }
    
    // GET que deleta o serie
    @RequestMapping("/deletarSerie")
    public String deletarSerie(long codigo) {
        Serie serie = sr.findByCodigo(codigo);
        sr.delete(serie);
        return "redirect:/series";
    }
    
	// Métodos que atualiza serie
	// GET que chama o formulário de edição do serie
	@RequestMapping("/editar-serie")
	public ModelAndView editarSerie(long codigo) {
		Serie serie = sr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("serie/update-serie");
		mv.addObject("serie", serie);
		return mv;
	}

	// POST do FORM que atualiza o serie
	@RequestMapping(value = "/editar-serie", method = RequestMethod.POST)
	public String updateSerie(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
		sr.save(serie);
		attributes.addFlashAttribute("success", "Serie alterado com sucesso!");
		return "redirect:/series";
	}
}
