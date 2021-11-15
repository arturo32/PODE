package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.service.EstudanteService;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "pes")
public class PesController extends GenericController<Pes, Long> {

    private PesService service;

    @Autowired
    public void setService(PesService service) {
        this.service = service;
    }

    @Override
    protected GenericService<Pes, Long> service() {
        return this.service;
    }
}
