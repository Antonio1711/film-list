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

import com.pi.models.Filme;
import com.pi.repository.FilmeRepository;


@Controller
public class FilmeController {
    
    @Autowired
    private FilmeRepository fr;
    
    //Chama o form que cadastra o filme
    
    @RequestMapping("/cadastrarFilme")
    public String form() {
        return "filme/form-filme";
    }
    
    //Post que cadastra o filme
    @RequestMapping(value = "/cadastrarFilme", method = RequestMethod.POST)
    public String form(@Valid Filme filme, BindingResult result, RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarFilme";
        }
        
        fr.save(filme);
        attributes.addFlashAttribute("mensagem", "Filme cadastrado com sucesso!");
        return "redirect:/cadastrarFilme";
    }
    
    // GET que lista os filmes
    @RequestMapping("/filmes")
    public ModelAndView listaFilmes() {
        ModelAndView mv = new ModelAndView("filme/lista-filme");
        Iterable<Filme> filmes = fr.findAll();
        mv.addObject("filmes", filmes);
        return mv;
    }
    
    // GET que mostra os detalhes do filme
    @RequestMapping("/filme/{codigo}")
    public ModelAndView detalhesFilme(@PathVariable("codigo") long codigo) {
        Filme filme = fr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("filme/detalhes-filme");
        mv.addObject("filme", filme);

        return mv;
    }
    
    // GET que deleta o filme
    @RequestMapping("/deletarFilme")
    public String deletarFilme(long codigo) {
        Filme filme = fr.findByCodigo(codigo);
        fr.delete(filme);
        return "redirect:/filmes";
    }
    
	// Métodos que atualiza filme
	// GET que chama o formulário de edição do filme
	@RequestMapping("/editar-filme")
	public ModelAndView editarFilme(long codigo) {
		Filme filme = fr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("filme/update-filme");
		mv.addObject("filme", filme);
		return mv;
	}

	// POST do FORM que atualiza o filme
	@RequestMapping(value = "/editar-filme", method = RequestMethod.POST)
	public String updateFilme(@Valid Filme filme, BindingResult result, RedirectAttributes attributes) {
		fr.save(filme);
		attributes.addFlashAttribute("success", "Filme alterado com sucesso!");
		return "redirect:/filmes";
	}
}
