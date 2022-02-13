package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.persistence.EntityNotFoundException;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;

import br.ufrn.imd.app3.modelo.VinculoPlataforma;

@Service
public class RecomendacaoSemNome implements RecomendacaoServico {

    private VinculoPlataformaServico vinculoPlataformaServico;

    @Autowired
    public void setVinculoBTIServico(VinculoPlataformaServico vinculoPlataformaServico) {
        this.vinculoPlataformaServico = vinculoPlataformaServico;
    }

    @Override
    public String getNomeServico() {
        return "???";
    }

    @Override
    public void validar(Long vinculoId) {
        ExceptionHelper exceptionHelper = new ExceptionHelper();
        /* verifica vinculoId */
        if (vinculoId < 0) {
            exceptionHelper.add("vinculo inconsistente");
        } else {
            try {
                this.vinculoPlataformaServico.buscarPorId(vinculoId);
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
        VinculoPlataforma vinculo = vinculoPlataformaServico.buscarPorId(vinculoId);
//        for (DisciplinaCursada dc : vinculo.getPlanoCurso().getDisciplinasPendentes()) {
//            DisciplinaPeriodo dp = (DisciplinaPeriodo) dc;
//            if (dp.getPeriodo() <= vinculo.getPeriodoAtual()) {
//                obrigatorias.add(dp.getDisciplina().getId());
//            }
//        }
        recomendacaoDTO.setDisciplinasObrigatorias(obrigatorias);
        return recomendacaoDTO;
    }
}
