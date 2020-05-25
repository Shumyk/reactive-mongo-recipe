package com.shumyk.services;

import com.shumyk.commands.RecipeCommand;
import com.shumyk.converters.RecipeCommandToRecipe;
import com.shumyk.converters.RecipeToRecipeCommand;
import com.shumyk.domain.Recipe;
import com.shumyk.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    final RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));
                    return recipeCommand;
                });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete).block();
    }
}
