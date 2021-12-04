package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PlanoCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "plano_curso")
public class PlanoCursoController extends GenericController<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoService service;

	@Autowired
	public void setService(PlanoCursoService service) {
		this.service = service;
	}

	@Override
	protected GenericService<PlanoCurso, PlanoCursoDTO, Long> service() {
		return this.service;
	}
	
}
