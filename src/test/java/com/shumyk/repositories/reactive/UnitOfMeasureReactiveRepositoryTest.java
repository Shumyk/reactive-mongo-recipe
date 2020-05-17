package com.shumyk.repositories.reactive;

import com.shumyk.domain.UnitOfMeasure;
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
public class UnitOfMeasureReactiveRepositoryTest {

    public static final String EACH = "Each";

    @Autowired private UnitOfMeasureReactiveRepository measureReactiveRepository;

    @Before public void setUp() {
        measureReactiveRepository.deleteAll().block();
    }

    @Test public void testSaveUom() {
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        measureReactiveRepository.save(uom).block();

        long count = measureReactiveRepository.count().block();
        assertEquals(1L, count);
    }

    @Test public void testFindByDescription() {
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        measureReactiveRepository.save(uom).block();

        final UnitOfMeasure fetchedUom = measureReactiveRepository.findByDescription(EACH).block();
        assertNotNull(fetchedUom);
        assertNotNull(fetchedUom.getId());
        assertEquals(EACH, fetchedUom.getDescription());
    }
}