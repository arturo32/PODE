package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.GenericoServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaControlador extends GenericControlador<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaServico service;

	public DisciplinaServico getDisciplinaService() {
		return this.service;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaServico disciplinaService) {
		this.service = disciplinaService;
	}

	@Override
	protected GenericoServico<Disciplina, DisciplinaDTO, Long> service() {
		return this.service;
	}

	@GetMapping("/codigos/{codigo}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinaCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok(service.convertToDTOList(service.findDisciplinasByCodigo(codigo)));
	}

}
