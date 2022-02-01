package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public abstract class VinculoServico<T extends Vinculo, E extends VinculoDTO> extends GenericoServico<T, E, Long> {

    public abstract VinculoRepositorio<T> getRepositorio();

    public void validar(Long idVinculo) {
        this.buscarPorId(idVinculo);
    }

    protected abstract Double gerarPercentualConclusao(Long idVinculo);

    public Double obterPercentualConclusao(Long idVinculo) {
        this.validar(idVinculo);
        return this.gerarPercentualConclusao(idVinculo);
    }

}
