package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;

public class VinculoBTIDTO extends VinculoDTO {
	private Integer periodoInicialAno;

	private Integer periodoAtualPeriodo;

	private Integer periodoAtualAno;

	private Integer periodoInicialPeriodo;
	public VinculoBTIDTO(VinculoBTI vinculo) {
		super(vinculo);
		periodoInicialAno = vinculo.getPeriodoInicialAno();
		periodoAtualPeriodo = vinculo.getPeriodoAtualPeriodo();
		periodoAtualAno = vinculo.getPeriodoAtualAno();
		periodoInicialPeriodo = vinculo.getPeriodoInicialPeriodo();
	}

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
