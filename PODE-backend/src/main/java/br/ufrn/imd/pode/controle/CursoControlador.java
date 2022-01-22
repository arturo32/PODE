package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Curso;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.dto.CursoDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.servico.CursoServico;
import br.ufrn.imd.pode.servico.GenericoServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
public class CursoControlador extends GenericoControlador<Curso, CursoDTO, Long> {

	private CursoServico service;

	@Autowired
	public void setService(CursoServico cursoService) {
		this.service = cursoService;
	}

	@Override
	protected GenericoServico<Curso, CursoDTO, Long> servico() {
		return this.service;
	}

	@GetMapping("/{id}/disciplinas-obrigatorias")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> buscarDisciplinasObrigatoriasCurso(@PathVariable Long id) {
		Set<DisciplinaPeriodo> disciplinasObrigatorias = service.buscarPorId(id).getDisciplinasObrigatorias();
		return ResponseEntity
				.ok(disciplinasObrigatorias.stream().map(DisciplinaPeriodoDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas-optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasCurso(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.buscarPorId(id).getDisciplinasOptativas();
		return ResponseEntity.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
