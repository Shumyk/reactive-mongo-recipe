package com.shumyk.services;

import com.shumyk.commands.UnitOfMeasureCommand;
import com.shumyk.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.shumyk.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override public Flux<UnitOfMeasureCommand> listAllUoms() {
        return unitOfMeasureRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
