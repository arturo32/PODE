package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.service.DisciplinaService;
import br.ufrn.imd.pode.service.GenericService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController extends GenericController<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaService service;

	public DisciplinaService getDisciplinaService() {
		return this.service;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.service = disciplinaService;
	}

	@Override
	protected GenericService<Disciplina, DisciplinaDTO, Long> service() {
		return this.service;
	}


}
