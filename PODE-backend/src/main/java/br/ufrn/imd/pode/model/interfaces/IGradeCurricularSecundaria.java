package br.ufrn.imd.pode.model.interfaces;

import br.ufrn.imd.pode.model.Disciplina;

import java.util.Set;

public interface IGradeCurricularSecundaria {
	Integer getChm();

	Integer getCho();

	Set<Disciplina> getDisciplinasObrigatorias();

	Set<Disciplina> getDisciplinasOptativas();
}
