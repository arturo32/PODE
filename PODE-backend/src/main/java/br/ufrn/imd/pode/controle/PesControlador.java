package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.Pes;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.PesDTO;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.PesServico;

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
@RequestMapping("/pes")
public class PesControlador extends GenericoControlador<Pes, PesDTO, Long> {

	private PesServico service;

	@Autowired
	public void setService(PesServico service) {
		this.service = service;
	}

	@Override
	protected GenericoServico<Pes, PesDTO, Long> servico() {
		return this.service;
	}

	@GetMapping("/{id}/disciplinas-obrigatorias")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasObrigatoriasPes(@PathVariable Long id) {
		Set<Disciplina> disciplinasObrigatorias = service.buscarPorId(id).getDisciplinasObrigatorias();
		return ResponseEntity
				.ok(disciplinasObrigatorias.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas-optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasPes(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.buscarPorId(id).getDisciplinasOptativas();
		return ResponseEntity
				.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
