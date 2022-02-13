package br.ufrn.imd.app3.modelo.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

import br.ufrn.imd.app3.modelo.ConteudoCursado;

public class ConteudoCursadoDTO extends DisciplinaCursadaDTO {
	private @NotNull LocalDate localDate;

	public ConteudoCursadoDTO(){}

	public ConteudoCursadoDTO(ConteudoCursado conteudoCursado) {
		super(conteudoCursado);
		this.localDate = conteudoCursado.getLocalDate();
	}

	public @NotNull LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(@NotNull LocalDate localDate) {
		this.localDate = localDate;
	}
}
