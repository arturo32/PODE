package br.ufrn.imd.pode.interfaces;

import br.ufrn.imd.pode.model.Disciplina;

import java.util.Set;

public interface IGradeCurricularSecundaria {
	Integer getChm();

	Integer getCho();

	Set<Disciplina> getDisciplinasObrigatorias();

	Set<Disciplina> getDisciplinasOptativas();

	Boolean concluida(Set<Disciplina> disciplinas);
}
