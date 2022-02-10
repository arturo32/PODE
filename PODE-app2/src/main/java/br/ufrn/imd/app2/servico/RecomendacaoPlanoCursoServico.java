package br.ufrn.imd.app2.servico;

import br.ufrn.imd.app2.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app2.modelo.VinculoBTI;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;

import javax.persistence.EntityNotFoundException;

@Service
public class RecomendacaoPlanoCursoServico implements RecomendacaoServico {

    private VinculoBTIServico vinculoBTIServico;

    @Autowired
    public void setVinculoBTIServico(VinculoBTIServico vinculoBTIServico) {
        this.vinculoBTIServico = vinculoBTIServico;
    }

    @Override
    public String getNomeServico() {
        return "plano_curso";
    }

    @Override
    public void validar(Long vinculoId) {
        ExceptionHelper exceptionHelper = new ExceptionHelper();
        /* verifica vinculoId */
        if (vinculoId < 0) {
            exceptionHelper.add("vinculo inconsistente");
        } else {
            try {
                this.vinculoBTIServico.buscarPorId(vinculoId);
            } catch (EntityNotFoundException entityNotFoundException) {
                exceptionHelper.add("vinculo(id=" + vinculoId + ") inexistente");
            }
        }
        /* verifica se existe exceçao */
        if (!exceptionHelper.getMessage().isEmpty()) {
            throw new ValidacaoException(exceptionHelper.getMessage());
        }
    }

    @Override
    public RecomendacaoDTO recomendar(Long vinculoId) {
        RecomendacaoDTO recomendacaoDTO = new RecomendacaoDTO();
        Set<Long> obrigatorias = new HashSet<>();
        VinculoBTI vinculo = vinculoBTIServico.buscarPorId(vinculoId);
        for (DisciplinaCursada dc : vinculo.getPlanoCurso().getDisciplinasPendentes()) {
            DisciplinaPeriodo dp = (DisciplinaPeriodo) dc;
            if (dp.getPeriodo() <= vinculo.getPeriodoAtual()) {
                obrigatorias.add(dp.getDisciplina().getId());
            }
        }
        recomendacaoDTO.setDisciplinasObrigatorias(obrigatorias);
        return recomendacaoDTO;
    }
}
