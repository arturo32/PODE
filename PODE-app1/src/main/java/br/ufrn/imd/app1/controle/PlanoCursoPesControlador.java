package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.PlanoCursoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.PlanoCursoPes;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.servico.PlanoCursoPesServico;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoPesControlador extends PlanoCursoControlador<PlanoCursoPes, PlanoCursoPesDTO> {

	private PlanoCursoPesServico planoCursoPesServico;

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoPesServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Override
	protected GenericoServico<PlanoCursoPes, PlanoCursoPesDTO, Long> servico() {
		return this.planoCursoPesServico;
	}

}
