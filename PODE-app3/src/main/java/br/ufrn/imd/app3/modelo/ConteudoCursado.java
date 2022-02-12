package br.ufrn.imd.app3.modelo;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "conteudocursado")
public class ConteudoCursado extends DisciplinaCursada implements DisciplinaInterface {

	private @NotNull LocalDate localDate;

	public ConteudoCursado() {}

	public ConteudoCursado(Conteudo conteudo, LocalDate localDate) {
		this.setDisciplina(conteudo);
		this.setLocalDate(localDate);
	}

	public @NotNull LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(@NotNull LocalDate localDateTime) {
		this.localDate = localDateTime;
	}
}
