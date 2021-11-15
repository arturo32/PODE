package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.service.EnfaseService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "enfase")
public class EnfaseController extends GenericController<Enfase, Long> {

    private EnfaseService service;

    @Autowired
    public void setService(EnfaseService service) {
        this.service = service;
    }

    @Override
    protected GenericService<Enfase, Long> service() {
        return this.service;
    }
}
