package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ConteudoCursadoDTO extends DisciplinaCursadaDTO {

	private @NotNull LocalDate localDate;

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
