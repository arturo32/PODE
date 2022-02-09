package br.ufrn.imd.app2.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.DisciplinaCursadaControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app2.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app2.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app2.servico.DisciplinaPeriodoServico;

@RestController
@RequestMapping("/disciplinas-periodo")
public class DisciplinaPeriodoControlador extends DisciplinaCursadaControlador<DisciplinaPeriodo, DisciplinaPeriodoDTO> {

	private DisciplinaPeriodoServico disciplinaBTIServico;

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaPeriodoServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	protected GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> servico() {
		return this.disciplinaBTIServico;
	}

}
