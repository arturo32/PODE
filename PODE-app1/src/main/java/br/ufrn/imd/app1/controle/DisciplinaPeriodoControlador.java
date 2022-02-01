package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app1.servico.DisciplinaPeriodoServico;

@RestController
@RequestMapping("/disciplinas-periodo")
public class DisciplinaPeriodoControlador extends GenericoControlador<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

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
