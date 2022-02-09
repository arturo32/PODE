package br.ufrn.imd.app2.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "enfase")
public class Enfase extends GradeCurricular {

	@NotNull
	@ManyToOne
	private CursoBTI cursoBTI;

	public Enfase() {
	}

	public Enfase(@NotNull String nome, @NotNull CursoBTI cursoBTI) {
		this.nome = nome;
		this.cursoBTI = cursoBTI;
		this.chobm = 0;
		this.chopm = 0;
	}

	@NotNull
	public CursoBTI getCursoBTI() {
		return cursoBTI;
	}

	public void setCursoBTI(@NotNull CursoBTI cursoBTI) {
		this.cursoBTI = cursoBTI;
	}

	public CursoBTI getCurso() {
		return cursoBTI;
	}

	public void setCurso(CursoBTI curso) {
		this.cursoBTI = curso;
	}

	public Integer getChm() {
		return this.cursoBTI.getChm();
	}

	public Integer getChEspecifica() {
		Integer cho_especifica = 0;
		for (DisciplinaCursada disciplina : this.getDisciplinasObrigatorias()) {
			cho_especifica += disciplina.getDisciplina().getCh();
		}
		return cho_especifica;
	}

	public Integer getChobm() {
		return this.cursoBTI.getChobm() + this.getChEspecifica();
	}

	public Integer getChopm() {
		return this.cursoBTI.getChopm() - this.getChEspecifica();
	}

	public Integer getChcm() {
		return this.cursoBTI.getChcm();
	}

	public Integer getChem() {
		return this.cursoBTI.getChem();
	}

	public Integer getChmaxp() {
		return this.cursoBTI.getChmaxp();
	}

	public Integer getChminp() {
		return this.cursoBTI.getChminp();
	}

	public Integer getPrazoMinimo() {
		return this.cursoBTI.getPrazoMinimo();
	}

	public Integer getPrazoMaximo() {
		return this.cursoBTI.getPrazoMaximo();
	}

	public Integer getPrazoEsperado() {
		return this.cursoBTI.getPrazoEsperado();
	}

	public Set<DisciplinaCursada> getDisciplinasObrigatorias() {
		Set<DisciplinaCursada> resultado = this.cursoBTI.getDisciplinasObrigatorias();
		resultado.addAll(this.disciplinasObrigatorias);
		return resultado;
	}

	public Set<Disciplina> getDisciplinasOptativas() {
		Set<Disciplina> resultado = this.cursoBTI.getDisciplinasOptativas();
		resultado.removeAll(this.disciplinasObrigatorias.stream()
				.map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet()));
		return resultado;
	}

}
