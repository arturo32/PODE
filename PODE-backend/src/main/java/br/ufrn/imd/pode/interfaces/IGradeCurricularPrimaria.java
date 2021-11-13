package br.ufrn.imd.pode.interfaces;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaSemestre;

import java.util.Set;

public interface IGradeCurricularPrimaria {
    Integer getChm();

    Integer getCho();

    Integer getChom();

    Integer getChcm();

    Integer getChem();

    Integer getChmp();

    Integer getPrazoMinimo();

    Integer getPrazoMaximo();

    Integer getPrazoEsperado();

    Set<DisciplinaSemestre> getDisciplinasObrigatorias();

    Set<Disciplina> getDisciplinasOptativas();

    Boolean concluida(Set<Disciplina> disciplinas);
}
