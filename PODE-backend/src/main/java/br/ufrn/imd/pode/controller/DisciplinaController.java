package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.service.DisciplinaService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "disciplina")
public class DisciplinaController extends GenericController<Disciplina, Long> {

	private DisciplinaService service;

	@Autowired
	public void setService(DisciplinaService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Disciplina, Long> service() {
		return this.service;
	}
}
