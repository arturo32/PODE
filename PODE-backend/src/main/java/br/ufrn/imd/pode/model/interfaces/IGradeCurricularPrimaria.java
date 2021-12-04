package br.ufrn.imd.pode.model.interfaces;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;

import java.util.Set;

public interface IGradeCurricularPrimaria {
	Integer getChm();

	Integer getCho();

	Integer getChom();

	Integer getChcm();

	Integer getChem();

	Integer getChmaxp();

	Integer getChminp();

	Integer getPrazoMinimo();

	Integer getPrazoMaximo();

	Integer getPrazoEsperado();

	Set<DisciplinaPeriodo> getDisciplinasObrigatorias();

	Set<Disciplina> getDisciplinasOptativas();
}
