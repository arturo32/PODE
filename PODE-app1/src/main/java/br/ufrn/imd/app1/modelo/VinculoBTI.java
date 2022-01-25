package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.Vinculo;

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
}
