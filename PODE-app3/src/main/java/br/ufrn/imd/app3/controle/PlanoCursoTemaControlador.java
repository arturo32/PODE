package br.ufrn.imd.app3.controle;

import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufrn.imd.pode.controle.PlanoCursoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.PlanoCursoTema;
import br.ufrn.imd.app3.modelo.dto.PlanoCursoTemaDTO;
import br.ufrn.imd.app3.servico.PlanoCursoTemaServico;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoTemaControlador extends PlanoCursoControlador<PlanoCursoTema, PlanoCursoTemaDTO, ConteudoCursadoDTO> {

	private PlanoCursoTemaServico planoCursoPesServico;

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoTemaServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Override
	protected GenericoServico<PlanoCursoTema, PlanoCursoTemaDTO, Long> servico() {
		return this.planoCursoPesServico;
	}

}
