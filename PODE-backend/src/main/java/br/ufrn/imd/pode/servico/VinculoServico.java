package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public abstract class VinculoServico<T extends Vinculo, E extends VinculoDTO> extends GenericoServico<T, E, Long> {

    public abstract VinculoRepositorio<T> getRepositorio();

    public T buscarPorId(Long idVinculo) {
        Optional<T> vinculo = this.getRepositorio().findById(idVinculo);
        if (vinculo.isPresent()) {
            return vinculo.get();
        }
        throw new EntidadeNaoEncontradaException("vinculo de id=" + idVinculo + "inexistente");
    }

    public void validar(Long idVinculo) {
        this.buscarPorId(idVinculo);
    }

    protected abstract Double gerarPercentualConclusao(Long idVinculo);

    public Double obterPercentualConclusao(Long idVinculo) {
        this.validar(idVinculo);
        return this.gerarPercentualConclusao(idVinculo);
    }

}
