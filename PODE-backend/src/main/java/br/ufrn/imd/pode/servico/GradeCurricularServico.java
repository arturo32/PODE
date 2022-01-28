package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public abstract class GradeCurricularServico<T extends GradeCurricular, D extends GradeCurricularDTO> extends GenericoServico<T, D, Long> {
	protected DisciplinaServico disciplinaServico;

	@Autowired
	public DisciplinaServico getDisciplinaServico() {
		return this.disciplinaServico;
	}
}
