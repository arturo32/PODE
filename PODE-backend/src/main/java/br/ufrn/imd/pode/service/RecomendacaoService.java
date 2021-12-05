package br.ufrn.imd.pode.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.model.others.PesChobCumpridaResult;
import br.ufrn.imd.pode.model.others.PesChopCumpridaResult;

@Service
@Transactional
public class RecomendacaoService {

	private VinculoService vinculoService;
	private PesService pesService;
	private DisciplinaService disciplinaService;

	public VinculoService getVinculoService() {
		return vinculoService;
	}

	@Autowired
	public void setVinculoService(VinculoService vinculoService) {
		this.vinculoService = vinculoService;
	}

	public PesService getPesService() {
		return pesService;
	}

	@Autowired
	public void setPesService(PesService pesService) {
		this.pesService = pesService;
	}

	public DisciplinaService getDisciplinaService() {
		return disciplinaService;
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
				this.getVinculoService().findById(vinculoId);
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

	public RecomendacaoDTO recomendarDisciplinasPorProximidadeConclusaoPes(long vinculoId) {
		/** Etapa 1 - Buscar os pes com carga horária obrigatória cumprida */
		List<PesChobCumpridaResult> pesComChobCumprida = this.getPesService().getRepository()
				.findPesComChobCumpridaByVinculo(vinculoId);
		/** Etapa 2 - Buscar os pes com carga horária optativa cumprida */
		List<PesChopCumpridaResult> pesComChopCumprida = this.getPesService().getRepository()
				.findPesComChopCumpridaByVinculo(vinculoId);
		/** Etapa 3 - Escolher um pes */
		Integer size = pesComChobCumprida.size();
		if (size > 0) {
			Integer indexPesEscolhido = null;
			Integer chp = null;
			Integer chpCache = null;
			Integer index = 0;
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
			/** Etapa 4 - Obter disciplinas obrigatórias não cursadas */
			RecomendacaoDTO recomendacoes = new RecomendacaoDTO();
			Set<Disciplina> disciplinasObrigatoriasPendentes = this.disciplinaService.getRepository()
					.findDisciplinasObrigatoriasPendentesByVinculoAndPes(vinculoId, pesEscolhido.getId());
			for (Disciplina disciplina : disciplinasObrigatoriasPendentes) {
				recomendacoes.getDisciplinasObrigatorias().add(this.getDisciplinaService().convertToDto(disciplina));
			}
			/** Etapa 5 - Obter disciplinas optativas não cursadas */
			Set<Disciplina> disciplinasOptativasPendentes = this.disciplinaService.getRepository()
					.findDisciplinasOptativasPendentesByVinculoAndPes(vinculoId, pesEscolhido.getId());
			for (Disciplina disciplina : disciplinasOptativasPendentes) {
				recomendacoes.getDisciplinasOptativas().add(this.getDisciplinaService().convertToDto(disciplina));
			}
			return recomendacoes;
		} else {
			throw new EntityNotFoundException("nenhum pes encontrado");
		}
	}

}
