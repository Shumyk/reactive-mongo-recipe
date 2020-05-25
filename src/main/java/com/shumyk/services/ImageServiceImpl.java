package com.shumyk.services;

import com.shumyk.domain.Recipe;
import com.shumyk.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeRepository;

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        final Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    final Byte[] byteObjects;
                    try {
                        byteObjects = new Byte[file.getBytes().length];
                        int i = 0;
                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }
                        recipe.setImage(byteObjects);
                        return recipe;
                    } catch (IOException exception) {
                        log.error("Error during saving image file: {}", exception.getMessage(), exception);
                        throw new RuntimeException(exception);
                    }
                });
        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
