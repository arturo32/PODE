package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

import java.time.LocalDateTime;

public class ConteudoCursadoDTO extends DisciplinaCursadaDTO {

	private LocalDateTime localDateTime;

	public ConteudoCursadoDTO(ConteudoCursado conteudoCursado) {
		super(conteudoCursado);
		this.localDateTime = conteudoCursado.getLocalDateTime();
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
}
