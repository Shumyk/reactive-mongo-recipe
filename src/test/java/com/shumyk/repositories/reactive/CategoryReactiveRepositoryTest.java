package com.shumyk.repositories.reactive;

import com.shumyk.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    public static final String UKRAINIAN = "Ukrainian";
    @Autowired private CategoryReactiveRepository categoryReactiveRepository;

    @Before public void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test public void testSaveCategory() {
        final Category category = new Category();
        category.setDescription(UKRAINIAN);

        categoryReactiveRepository.save(category).block();

        final long count = categoryReactiveRepository.count().block();
        assertEquals(1L, count);
    }

    @Test public void findByDescription() {
        final Category category = new Category();
        category.setDescription(UKRAINIAN);

        categoryReactiveRepository.save(category).block();

        final Category fetchedCategory = categoryReactiveRepository.findByDescription(UKRAINIAN).block();
        assertNotNull(fetchedCategory);
        assertNotNull(fetchedCategory.getId());
        assertEquals(UKRAINIAN, fetchedCategory.getDescription());
    }
}