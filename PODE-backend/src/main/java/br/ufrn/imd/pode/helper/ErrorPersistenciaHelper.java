package br.ufrn.imd.pode.helper;

import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import br.ufrn.imd.pode.servico.TipoPersistencia;

public class ErrorPersistenciaHelper {

    public static void validate(TipoPersistencia tipoPersistencia, String nomeModelo, AbstratoDTO dto) {
        if (tipoPersistencia == TipoPersistencia.ADICIONAR) {
            if (dto.getId() != null) {
                throw new NegocioException("Entidade do tipo '" + nomeModelo
                        + "' com id: '" + dto.getId() + "'não pode ser modificada com esse método, caso queira modificá-la, use o método PUT");
            }
        } else if (tipoPersistencia == TipoPersistencia.ATUALIZAR) {
            if (dto.getId() == null) {
                throw new NegocioException("Entidade do tipo '" + nomeModelo
                        + "' com id: '" + dto.getId() + "'não existe, caso queira salvá-la, use o método POST");
            }
        }
    }

}
