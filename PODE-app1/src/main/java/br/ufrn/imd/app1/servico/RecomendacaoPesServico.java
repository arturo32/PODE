package br.ufrn.imd.app1.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;

import br.ufrn.imd.app1.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.app1.modelo.view.PesChOptativaCumprida;

import javax.persistence.EntityNotFoundException;

@Service
public class RecomendacaoPesServico implements RecomendacaoServico {

	private VinculoBTIServico vinculoBTIServico;

	private PesServico pesServico;

	private DisciplinaBTIServico disciplinaBTIServico;

	@Autowired
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	public String getNomeServico() {
		return "pes";
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
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public RecomendacaoDTO recomendar(Long vinculoId) {
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
			RecomendacaoDTO recomendacoes = new RecomendacaoDTO();
			PesChObrigatoriaCumprida pesChObrigatoriaCumprida = null;
			for (PesChObrigatoriaCumprida item : pesComChObrigatoriaCumprida) {
				if (item.getId().equals(pesEscolhido)) {
					recomendacoes.setNome(item.getNome());
					recomendacoes.setCargaHorariaObrigatoria(item.getCho());
					recomendacoes.setCargaHorariaObrigatoriaPendente(item.getChp());
					pesChObrigatoriaCumprida = item;
				}
			}
			PesChOptativaCumprida pesChOptativaCumprida = null;
			for (PesChOptativaCumprida item : pesComChOptativaCumprida) {
				if (item.getId().equals(pesEscolhido)) {
					if (recomendacoes.getNome().isEmpty()) {
						recomendacoes.setNome(item.getNome());
					}
					recomendacoes.setCargaHorariaOptativaMinima(item.getChm() - item.getCho());
					recomendacoes.setCargaHorariaOptativaPendente(item.getChp());
					pesChOptativaCumprida = item;
				}
			}
			recomendacoes.setDisciplinasObrigatorias(
					this.disciplinaBTIServico.obterDisciplinasObrigatoriasPendentesPes(vinculoId, pesEscolhido));
			recomendacoes.setDisciplinasOptativas(
					this.disciplinaBTIServico.obterDisciplinasOptativasPendentesPes(vinculoId, pesEscolhido));
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
}
