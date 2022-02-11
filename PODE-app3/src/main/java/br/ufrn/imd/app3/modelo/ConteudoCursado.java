package br.ufrn.imd.app3.modelo;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "conteudocursado")
public class ConteudoCursado extends DisciplinaCursada implements DisciplinaInterface {

	@NotNull
	private LocalDateTime localDateTime;

	public ConteudoCursado() {}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
}
