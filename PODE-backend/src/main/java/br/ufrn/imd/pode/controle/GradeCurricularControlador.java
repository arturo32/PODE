package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GradeCurricularControlador {

	private GradeCurricularServico servico;

	@Autowired
	public void setServico(GradeCurricularServico servico) {
		this.servico = servico;
	}
}
