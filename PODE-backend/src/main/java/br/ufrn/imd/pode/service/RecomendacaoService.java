package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.*;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.model.others.PesChobCumpridaResult;
import br.ufrn.imd.pode.model.others.PesChopCumpridaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecomendacaoService {

	private PesService pesService;

	private DisciplinaService disciplinaService;

	private PlanoCursoService planoCursoService;

	private VinculoService vinculoService;

	private DisciplinaPeriodoService disciplinaPeriodoService;

	@Autowired
	public void setVinculoService(VinculoService vinculoService) {
		this.vinculoService = vinculoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoService disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	@Autowired
	public void setPlanoCursoService(PlanoCursoService planoCursoService) {
		this.planoCursoService = planoCursoService;
	}

	@Autowired
	public void setPesService(PesService pesService) {
		this.pesService = pesService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public long validate(long vinculoId) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/* verifica vinculoId */
		if (vinculoId < 0) {
			exceptionHelper.add("vinculo inconsistente");
		} else {
			try {
				this.vinculoService.findById(vinculoId);
				// TODO verificar se o vinculo de fato percente ao usuário
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("vinculo(id=" + vinculoId + ") inexistente");
			}
		}
		/* verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return vinculoId;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

	public RecomendacaoDTO recomendarDisciplinasPorProximidadeConclusaoPesOld(long vinculoId) {
		/* Etapa 1 - Buscar os pes com carga horária obrigatória cumprida */
		List<PesChobCumpridaResult> pesComChobCumprida = this.pesService.getRepository()
				.findPesComChobCumpridaByVinculo(vinculoId);
		/* Etapa 2 - Buscar os pes com carga horária optativa cumprida */
		List<PesChopCumpridaResult> pesComChopCumprida = this.pesService.getRepository()
				.findPesComChopCumpridaByVinculo(vinculoId);
		/* Etapa 3 - Escolher um pes */
		int size = pesComChobCumprida.size();
		if (size > 0) {
			Integer indexPesEscolhido = null;
			Integer chp = null;
			Integer chpCache = null;
			int index = 0;
			for (; index < size; index++) {
				chp = pesComChobCumprida.get(index).getChp() + pesComChopCumprida.get(index).getChp();
				if (index == 0) {
					chpCache = chp;
				} else {
					if (chp < chpCache) {
						chpCache = chp;
						indexPesEscolhido = index;
					}
				}
			}
			PesChobCumpridaResult pesEscolhido = pesComChobCumprida.get(indexPesEscolhido);
			/* Etapa 4 - Obter disciplinas obrigatórias não cursadas */
			RecomendacaoDTO recomendacoes = new RecomendacaoDTO();
			Set<Disciplina> disciplinasObrigatoriasPendentes = this.disciplinaService.getRepository()
					.findDisciplinasObrigatoriasPendentesByVinculoAndPes(vinculoId, pesEscolhido.getId());
			for (Disciplina disciplina : disciplinasObrigatoriasPendentes) {
				recomendacoes.getDisciplinasObrigatorias().add(this.disciplinaService.convertToDto(disciplina));
			}
			/* Etapa 5 - Obter disciplinas optativas não cursadas */
			Set<Disciplina> disciplinasOptativasPendentes = this.disciplinaService.getRepository()
					.findDisciplinasOptativasPendentesByVinculoAndPes(vinculoId, pesEscolhido.getId());
			for (Disciplina disciplina : disciplinasOptativasPendentes) {
				recomendacoes.getDisciplinasOptativas().add(this.disciplinaService.convertToDto(disciplina));
			}
			return recomendacoes;
		} else {
			throw new EntityNotFoundException("nenhum pes encontrado");
		}
	}

	public List<DisciplinaPeriodoDTO> recomendarDisciplinasPorPlanoDeCurso(Long idVinculo) {
		Vinculo vinculo = vinculoService.findById(idVinculo);
		PlanoCurso planoCurso = planoCursoService.findPlanoCursoByVinculoId(idVinculo);
		return planoCurso.getDisciplinasPendentes().stream().
				filter(disciplinaPeriodo -> disciplinaPeriodo.getPeriodo() <= vinculo.getPeriodoAtual()).
				collect(Collectors.toSet()).stream().map(disciplinaPeriodoService::convertToDto).
				sorted(Comparator.comparing(DisciplinaPeriodoDTO::getPeriodo)).
				collect(Collectors.toList());
	}

	public List<DisciplinaDTO> recomendarDisciplinasPorProximidadeConclusaoPes(Long idVinculo) {
		List<Disciplina> recomendadas = new ArrayList<>();
		PlanoCurso planoCurso = planoCursoService.findPlanoCursoByVinculoId(idVinculo);
		Set<Disciplina> cursadas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		List<Pes> pesList = pesService.findAll(10,0);
		int bestCompletion = 0;
		for (Pes pes: pesList) {
			int pesConclusion = 0;
			List<Disciplina> disciplinasRestantes = new ArrayList<>();
			Set<Disciplina> obrigatorias = pes.getDisciplinasObrigatorias();
			for (Disciplina d: obrigatorias) {
				if (cursadas.contains(d) || disciplinaService.checarEquivalencia(cursadas, d)){
					pesConclusion += 1;
				} else {
					disciplinasRestantes.add(d);
				}
			}
			Set<Disciplina> optativas = pes.getDisciplinasOptativas();
			for (Disciplina d: optativas) {
				if (cursadas.contains(d) || disciplinaService.checarEquivalencia(cursadas, d)){
					pesConclusion += 1;
				} else {
					disciplinasRestantes.add(d);
				}
			}
			if (pesConclusion <= pes.getChm() && pesConclusion >= pes.getChm() / 2) {
				if (pesConclusion > bestCompletion) {
					bestCompletion = pesConclusion;
					recomendadas.clear();
					recomendadas.addAll(disciplinasRestantes);
				}
			}
		}

		return new ArrayList<>(disciplinaService.convertToDTOList(recomendadas));
	}

}
