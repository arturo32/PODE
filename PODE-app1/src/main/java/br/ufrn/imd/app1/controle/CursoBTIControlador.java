package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GradeCurricularControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.servico.CursoBTIServico;

@RestController
@RequestMapping("/cursos-bti")
public class CursoBTIControlador extends GradeCurricularControlador<CursoBTI, CursoBTIDTO> {

	private CursoBTIServico cursoBTIServico;

	@Autowired
	public void setCursoBTIServico(CursoBTIServico cursoBTIServico) {
		this.cursoBTIServico = cursoBTIServico;
	}

	@Override
	protected GenericoServico<CursoBTI, CursoBTIDTO, Long> servico() {
		return this.cursoBTIServico;
	}

}