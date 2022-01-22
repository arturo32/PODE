package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.modelo.dto.RecomendacaoPesDTO;
import br.ufrn.imd.pode.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.pode.modelo.view.PesChOptativaCumprida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecomendacaoServico {

	private PesServico pesServico;

	private DisciplinaServico disciplinaServico;

	private VinculoServico vinculoServico;

	private DisciplinaPeriodoServico disciplinaPeriodoServico;

	@Autowired
	public void setVinculoServico(VinculoServico vinculoServico) {
		this.vinculoServico = vinculoServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Autowired
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
	}

	@Autowired
	public void setDisciplinaServico(DisciplinaServico disciplinaServico) {
		this.disciplinaServico = disciplinaServico;
	}

	public long validar(long vinculoId) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/* verifica vinculoId */
		if (vinculoId < 0) {
			exceptionHelper.add("vinculo inconsistente");
		} else {
			try {
				this.vinculoServico.buscarPorId(vinculoId);
			} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
				exceptionHelper.add("vinculo(id=" + vinculoId + ") inexistente");
			}
		}
		/* verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return vinculoId;
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public RecomendacaoPesDTO recomendarDisciplinasPorProximidadeConclusaoPes(long vinculoId) {
		/* Etapa 1 - buscar os pes com carga horária obrigatória cumprida */
		Set<PesChObrigatoriaCumprida> pesComChObrigatoriaCumprida = this.pesServico
				.obterPesComChObrigatoriaCumprida(vinculoId);
		/* Etapa 2 - buscar os pes com carga horária optativa cumprida */
		Set<PesChOptativaCumprida> pesComChOptativaCumprida = this.pesServico.obterPesComChOptativaCumprida(vinculoId);
		/** Etapa 3 - montar lista de possibilidades */
		HashMap<Long, Integer> possibilidadesPes = new HashMap<Long, Integer>();
		for (PesChObrigatoriaCumprida item : pesComChObrigatoriaCumprida) {
			possibilidadesPes.put(item.getId(), item.getChp());
		}
		for (PesChOptativaCumprida item : pesComChOptativaCumprida) {
			if (possibilidadesPes.containsKey(item.getId())) {
				possibilidadesPes.put(item.getId(), possibilidadesPes.get(item.getId()) + item.getChp());
			} else {
				possibilidadesPes.put(item.getId(), item.getChp());
			}
		}
		/* Etapa 4 - escolher um pes */
		if (possibilidadesPes.size() > 0) {
			Long pesEscolhido = null;
			Integer menorChp = 0;
			for (Map.Entry<Long, Integer> item : possibilidadesPes.entrySet()) {
				if (menorChp == 0 || (item.getValue() > 0 && item.getValue() < menorChp)) {
					menorChp = item.getValue();
					pesEscolhido = item.getKey();
				}
			}
			RecomendacaoPesDTO recomendacoes = new RecomendacaoPesDTO();
			PesChObrigatoriaCumprida pesChObrigatoriaCumprida = null;
			for (PesChObrigatoriaCumprida item : pesComChObrigatoriaCumprida) {
				if (item.getId() == pesEscolhido) {
					recomendacoes.setNome(item.getNome());
					recomendacoes.setCargaHorariaObrigatoria(item.getCho());
					recomendacoes.setCargaHorariaObrigatoriaPendente(item.getChp());
					pesChObrigatoriaCumprida = item;
				}
			}
			PesChOptativaCumprida pesChOptativaCumprida = null;
			for (PesChOptativaCumprida item : pesComChOptativaCumprida) {
				if (item.getId() == pesEscolhido) {
					if (recomendacoes.getNome().isEmpty()) {
						recomendacoes.setNome(item.getNome());
					}
					recomendacoes.setCargaHorariaOptativaMinima(item.getChm() - item.getCho());
					recomendacoes.setCargaHorariaOptativaPendente(item.getChp());
					pesChOptativaCumprida = item;
				}
			}
			recomendacoes.setDisciplinasObrigatorias(
					this.disciplinaServico.obterDisciplinasObrigatoriasPendentesPes(vinculoId, pesEscolhido));
			recomendacoes.setDisciplinasOptativas(
					this.disciplinaServico.obterDisciplinasOptativasPendentesPes(vinculoId, pesEscolhido));
			/** Cuida do caso de ter cursado nenhuma disciplina obrigatoria */
			if (recomendacoes.getCargaHorariaObrigatoria() == 0) {
				recomendacoes.setCargaHorariaObrigatoria(pesChOptativaCumprida.getCho());
				recomendacoes.setCargaHorariaObrigatoriaPendente(recomendacoes.getCargaHorariaObrigatoria());
			}
			/** Cuida do caso de ter cursado nenhuma disciplina optativa */
			if (recomendacoes.getCargaHorariaOptativaMinima() == 0) {
				recomendacoes.setCargaHorariaOptativaMinima(
						pesChObrigatoriaCumprida.getChm() - pesChObrigatoriaCumprida.getCho());
				recomendacoes.setCargaHorariaOptativaPendente(recomendacoes.getCargaHorariaOptativaMinima());
			}
			return recomendacoes;
		} else {
			throw new EntidadeNaoEncontradaException("nenhuma disciplina de qualquer pes foi cursada");
		}
	}

	public List<DisciplinaPeriodoDTO> recomendarDisciplinasPorPlanoDeCurso(Long idVinculo) {
		Vinculo vinculo = vinculoServico.buscarPorId(idVinculo);
		return vinculo.getPlanoCurso().getDisciplinasPendentes().stream()
				.filter(disciplinaPeriodo -> disciplinaPeriodo.getPeriodo() <= vinculo.getPeriodoAtual())
				.collect(Collectors.toSet()).stream().map(disciplinaPeriodoServico::converterParaDTO)
				.sorted(Comparator.comparing(DisciplinaPeriodoDTO::getPeriodo)).collect(Collectors.toList());
	}

}
