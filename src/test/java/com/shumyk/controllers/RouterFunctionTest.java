package com.shumyk.controllers;

import com.shumyk.config.WebConfig;
import com.shumyk.domain.Recipe;
import com.shumyk.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.doReturn;

public class RouterFunctionTest {

    WebTestClient webTestClient;

    @Mock RecipeService recipeService;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        WebConfig webConfig = new WebConfig();
        RouterFunction<ServerResponse> routerFunction = webConfig.routes(recipeService);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test public void testGetRecipes() {
        doReturn(Flux.just()).when(recipeService).getRecipes();

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test public void testGetRecipesWithData() {
        doReturn(Flux.just(new Recipe(), new Recipe())).when(recipeService).getRecipes();

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }
}
