package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;

@Service
@Transactional
public abstract class VinculoServico<T extends Vinculo, E extends VinculoDTO> extends GenericoServico<T, E, Long> {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public T buscarPorId(Long idVinculo) {
        Optional<T> vinculo = this.repositorio().findById(idVinculo);
        if (vinculo.isPresent()) {
            return vinculo.get();
        }
        throw new EntidadeNaoEncontradaException("vinculo de id=" + idVinculo + "inexistente");
    }

    public void validar(Long idVinculo) {
        this.buscarPorId(idVinculo);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    protected abstract Double gerarPercentualConclusao(Long idVinculo);

    public Double obterPercentualConclusao(Long idVinculo) {
        this.validar(idVinculo);
        return this.gerarPercentualConclusao(idVinculo);
    }

}
