package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app1.servico.DisciplinaPeriodoServico;
import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/disciplinas-periodo")
public class DisciplinaPeriodoControlador extends GenericoControlador<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoServico disciplinaBTIServico;

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaPeriodoServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	protected GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> servico() {
		return disciplinaBTIServico;
	}
}
