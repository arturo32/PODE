package br.ufrn.imd.app3.servico;

import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.modelo.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.VinculoPlataforma;

@Service
public class RecomendacaoTemaMaisCursado implements RecomendacaoServico {

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
        return "tema_mais_cursado";
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
        for (DisciplinaCursada dc : vinculo.getPlanoCurso().getDisciplinasCursadas()) {
            Conteudo conteudo = (Conteudo) dc.getDisciplina();
            Tema tema = conteudo.getTema();
            temas_counter.put(tema, temas_counter.getOrDefault(tema, 0L) + 1L);
        }
        if (temas_counter.isEmpty()) {
            throw new NegocioException("Nenhum conteudo cursado ainda, impossivel recomendar!");
        }
        Tema key = Collections.max(temas_counter.entrySet(), Map.Entry.comparingByValue()).getKey();
        recomendacaoDTO.setNome(key.getNome());
        Collection<Conteudo> conteudos = conteudoServico.buscarPorTema(key);
        Set<Disciplina> cursadas = vinculo.getPlanoCurso().getDisciplinasCursadas().stream().
                map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet());
        conteudos.removeIf(cursadas::contains);
        Set<Long> recomendadas = conteudos.stream().map(Conteudo::getId).collect(Collectors.toSet());
        recomendacaoDTO.setDisciplinasObrigatorias(recomendadas);
        return recomendacaoDTO;
    }
}
