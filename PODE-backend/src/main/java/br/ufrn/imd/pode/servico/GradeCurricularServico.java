package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;

@Service
@Transactional
public abstract class GradeCurricularServico<T extends GradeCurricular, D extends GradeCurricularDTO> extends GenericoServico<T, D, Long> {

}
