package es.upm.miw.betca_tpv_spring.api_rest_controllers;

import es.upm.miw.betca_tpv_spring.business_controllers.SendingsController;
import es.upm.miw.betca_tpv_spring.documents.Sendings;
import es.upm.miw.betca_tpv_spring.dtos.SendingsCreationDto;
import es.upm.miw.betca_tpv_spring.dtos.SendingsDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(SendingsResource.SENDINGS)
public class SendingsResource {

    public static final String SENDINGS = "/sendings";
    public static final String TICKET_ID = "/{id}";

    private SendingsController sendingsController;

    @Autowired
    public SendingsResource(SendingsController sendingsController) {
        this.sendingsController = sendingsController;
    }

    @GetMapping
    public Flux<Sendings> readAll() {
        return this.sendingsController.readAll()
                .doOnEach(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = TICKET_ID)
    public Mono<Sendings> read(@PathVariable String id) {
        return this.sendingsController.readSendings(id)
                .doOnSuccess(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Sendings> createSendings(@Valid @RequestBody SendingsCreationDto sendingsCreationDto) {
        return this.sendingsController.createSendings(sendingsCreationDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PutMapping(value = TICKET_ID)
    public Mono<SendingsDto> update(@PathVariable String id, @Valid @RequestBody SendingsDto sendingsDto) {
        return this.sendingsController.update(id, sendingsDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @DeleteMapping(value = TICKET_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.sendingsController.deleteSendings(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

}
