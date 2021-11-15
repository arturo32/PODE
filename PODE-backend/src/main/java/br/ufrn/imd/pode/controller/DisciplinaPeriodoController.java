package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.service.DisciplinaPeriodoService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "disciplina_periodo")
public class DisciplinaPeriodoController extends GenericController<DisciplinaPeriodo, Long> {

    private DisciplinaPeriodoService service;

    @Autowired
    public void setService(DisciplinaPeriodoService service) {
        this.service = service;
    }

    @Override
    protected GenericService<DisciplinaPeriodo, Long> service() {
        return this.service;
    }
}
