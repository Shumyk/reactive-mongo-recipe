package com.shumyk.controllers;

import com.shumyk.commands.RecipeCommand;
import com.shumyk.services.RecipeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    @NonNull private final RecipeService recipeService;
    private WebDataBinder webDataBinder;

    @InitBinder public void initBinder(final WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand command){
        webDataBinder.validate();
        final BindingResult bindingResult = webDataBinder.getBindingResult();
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPEFORM_URL;
        }

        recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + command.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(id);
        return "redirect:/";
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception exception){
//
//        log.error("Handling not found exception");
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("404error");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }

}
