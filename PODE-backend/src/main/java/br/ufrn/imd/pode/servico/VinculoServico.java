package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public abstract class VinculoServico<T extends Vinculo, D extends VinculoDTO> extends GenericoServico<T, D, Long> {

    public abstract VinculoRepositorio<T> getRepositorio();

    @Override
    protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
        ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
    }

    protected void validar(Long idVinculo) {
        this.buscarPorId(idVinculo);
    }

    protected abstract Double gerarPercentualConclusao(Long idVinculo);

    public Double obterPercentualConclusao(Long idVinculo) {
        this.validar(idVinculo);
        return this.gerarPercentualConclusao(idVinculo);
    }

    public T buscarPorPlanoCursoId(Long planoCursoId) {
        Optional<T> r = getRepositorio().findByPlanoCurso_Id(planoCursoId);
        try {
            return r.orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException("Não foi encontrado um Vinculo associado ao plano de curso indicado");
        }
    }

}
