package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.PlanoCursoPes;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.servico.PlanoCursoPesServico;
import br.ufrn.imd.pode.controle.PlanoCursoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoPesControlador extends PlanoCursoControlador<PlanoCursoPes, PlanoCursoPesDTO> {

	private PlanoCursoPesServico servico;

	@Autowired
	public void setServico(PlanoCursoPesServico servico) {
		this.servico = servico;
	}

	@Override
	public PlanoCursoServico<PlanoCursoPes, PlanoCursoPesDTO> getPlanoCursoServico() {
		return servico;
	}

	@Override
	protected GenericoServico<PlanoCursoPes, PlanoCursoPesDTO, Long> servico() {
		return servico;
	}
}
