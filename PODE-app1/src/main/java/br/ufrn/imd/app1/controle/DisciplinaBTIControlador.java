package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.DisciplinaControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.servico.DisciplinaBTIServico;

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

}
