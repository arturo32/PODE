package br.ufrn.imd.app3.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ufrn.imd.pode.modelo.Vinculo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vinculobti")
public class VinculoBTI extends Vinculo {
	private Integer periodoInicialAno;

	private Integer periodoAtualPeriodo;

	private Integer periodoAtualAno;

	private Integer periodoInicialPeriodo;

	public Integer getPeriodoInicialAno() {
		return periodoInicialAno;
	}

	public void setPeriodoInicialAno(Integer periodoInicialAno) {
		this.periodoInicialAno = periodoInicialAno;
	}

	public Integer getPeriodoAtualPeriodo() {
		return periodoAtualPeriodo;
	}

	public void setPeriodoAtualPeriodo(Integer periodoAtualPeriodo) {
		this.periodoAtualPeriodo = periodoAtualPeriodo;
	}

	public Integer getPeriodoAtualAno() {
		return periodoAtualAno;
	}

	public void setPeriodoAtualAno(Integer periodoAtualAno) {
		this.periodoAtualAno = periodoAtualAno;
	}

	public Integer getPeriodoInicialPeriodo() {
		return periodoInicialPeriodo;
	}

	public void setPeriodoInicialPeriodo(Integer periodoInicialPeriodo) {
		this.periodoInicialPeriodo = periodoInicialPeriodo;
	}

	@JsonIgnore
	public Integer getPeriodoAtual() {
		int ano_diff = getPeriodoAtualAno() - getPeriodoInicialAno();
		int periodos = ano_diff * 2;
		if (getPeriodoAtualPeriodo().equals(getPeriodoInicialPeriodo())) {
			periodos += 1;
		} else {
			if (getPeriodoAtualPeriodo() > getPeriodoInicialPeriodo()) {
				periodos += 2;
			}
		}
		return periodos;
	}
}
