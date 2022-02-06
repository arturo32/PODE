package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;

@Service
@Transactional
public abstract class GradeCurricularServico<T extends GradeCurricular, D extends GradeCurricularDTO> extends GenericoServico<T, D, Long> {

    @Override
    protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
        ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
    }

}
