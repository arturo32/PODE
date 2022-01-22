package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.Enfase;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.modelo.dto.EnfaseDTO;
import br.ufrn.imd.pode.servico.EnfaseServico;
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
@RequestMapping("/enfases")
public class EnfaseControlador extends GenericoControlador<Enfase, EnfaseDTO, Long> {

	private EnfaseServico service;

	@Autowired
	public void setService(EnfaseServico service) {
		this.service = service;
	}

	@Override
	protected GenericoServico<Enfase, EnfaseDTO, Long> servico() {
		return this.service;
	}

	@GetMapping("/cursos/{cursoId}")
	public ResponseEntity<Collection<EnfaseDTO>> enfasesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(service.converterParaListaDTO(service.findEnfasePorCurso(cursoId)));
	}

	@GetMapping("/{id}/disciplinas-obrigatorias")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> buscarDisciplinasObrigatoriasEnfase(@PathVariable Long id) {
		Set<DisciplinaPeriodo> disciplinasObrigatorias = service.buscarPorId(id).getDisciplinasObrigatorias();
		return ResponseEntity
				.ok(disciplinasObrigatorias.stream().map(DisciplinaPeriodoDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas-optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasEnfase(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.buscarPorId(id).getDisciplinasOptativas();
		return ResponseEntity
				.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
