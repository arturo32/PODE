package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.VinculoPlataforma;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;

import javax.persistence.EntityNotFoundException;

@Service
public class RecomendacaoSemNome implements RecomendacaoServico {

    private VinculoPlataformaServico vinculoPlataformaServico;
    private ConteudoServico conteudoServico;

    @Autowired
    public void setVinculoPlataformaServico(VinculoPlataformaServico vinculoPlataformaServico) {
        this.vinculoPlataformaServico = vinculoPlataformaServico;
    }

    @Autowired
    public void setConteudoServico(ConteudoServico conteudoServico) {
        this.conteudoServico = conteudoServico;
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
        VinculoPlataforma vinculo = vinculoPlataformaServico.buscarPorId(vinculoId);
        HashMap<Tema, Long> temas_counter = new HashMap<>();
        for (DisciplinaCursada dc : vinculo.getPlanoCurso().getDisciplinasPendentes()) {
            Conteudo conteudo = (Conteudo) dc.getDisciplina();
            Tema tema = conteudo.getTema();
            temas_counter.put(tema, temas_counter.getOrDefault(tema, 0L) + 1L);
        }
        Tema key = Collections.max(temas_counter.entrySet(), Map.Entry.comparingByValue()).getKey();
        Collection<Conteudo> conteudos = conteudoServico.buscarPorTema(key);
        Set<Long> recomendadas = conteudos.stream().map(Conteudo::getId).collect(Collectors.toSet());
        recomendacaoDTO.setDisciplinasObrigatorias(recomendadas);
        return recomendacaoDTO;
    }
}
