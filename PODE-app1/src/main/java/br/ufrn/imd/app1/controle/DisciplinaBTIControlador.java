package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.DisciplinaControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.servico.DisciplinaBTIServico;

import java.util.Collection;

@RestController
@RequestMapping("/disciplinas-bti")
public class DisciplinaBTIControlador extends DisciplinaControlador<DisciplinaBTI, DisciplinaBTIDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	protected GenericoServico<DisciplinaBTI, DisciplinaBTIDTO, Long> servico() {
		return this.disciplinaBTIServico;
	}

	@GetMapping({"/codigos/{codigo}"})
	public ResponseEntity<Collection<DisciplinaBTIDTO>> buscarDisciplinaCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok(disciplinaBTIServico.converterParaListaDTO(disciplinaBTIServico.buscarDisciplinasPorCodigo(codigo)));
	}
}
