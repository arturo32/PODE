package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.servico.CursoBTIServico;
import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos-bti")
public class CursoBTIControlador extends GenericoControlador<CursoBTI, CursoBTIDTO, Long> {

	private CursoBTIServico servico;

	@Autowired
	public void setServico(CursoBTIServico servico) {
		this.servico = servico;
	}

	@Override
	protected GenericoServico<CursoBTI, CursoBTIDTO, Long> servico() {
		return servico;
	}
}
