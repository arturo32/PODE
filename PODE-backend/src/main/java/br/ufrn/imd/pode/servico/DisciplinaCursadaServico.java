package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

public abstract class DisciplinaCursadaServico <T extends DisciplinaCursada, D extends DisciplinaCursadaDTO> extends GenericoServico<T, D, Long> {

    @Override
    protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
        ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
    }

}
