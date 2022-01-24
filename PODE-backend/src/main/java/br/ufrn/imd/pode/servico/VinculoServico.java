package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public abstract class VinculoServico extends GenericoServico<Vinculo, VinculoDTO, Long> {

	private VinculoRepositorio repositorio;
	private EstudanteServico estudanteServico;

	@Override
	public VinculoDTO converterParaDTO(Vinculo vinculo) {
		return new VinculoDTO(vinculo);
	}

	@Override
	public abstract Vinculo converterParaEntidade(VinculoDTO dto);

	@Override
	protected VinculoDTO validar(VinculoDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		if (dto.getIdEstudante() != null) {
			try {
				estudanteServico.buscarPorId(dto.getIdEstudante());
			} catch (EntidadeNaoEncontradaException e) {
				exceptionHelper.add("estudante de id: " + dto.getIdEstudante() + " não encontrado");
			}
		} else {
			exceptionHelper.add("estudante não foi informado");
		}

		// Verifica matricula
		if (StringUtils.isEmpty(dto.getMatricula())) {
			exceptionHelper.add("matricula não foi informada");
		}

		if (dto.getPeriodoInicialAno() == null) {
			exceptionHelper.add("ano inicial não foi informado");
		}

		if (dto.getPeriodoAtualAno() == null) {
			exceptionHelper.add("ano atual não foi informado");
		}

		if (dto.getPeriodoInicialPeriodo() != null) {
			if (dto.getPeriodoInicialPeriodo() != 1 || dto.getPeriodoInicialPeriodo() != 2) {
				exceptionHelper.add("periodo inicial invalido, deve ser 1 ou 2");
			}
		} else {
			exceptionHelper.add("periodo inicial não foi informado");
		}

		if (dto.getPeriodoAtualPeriodo() != null) {
			if (dto.getPeriodoInicialPeriodo() != 1 || dto.getPeriodoInicialPeriodo() != 2) {
				exceptionHelper.add("periodo atual invalido, deve ser 1 ou 2");
			}
		} else {
			exceptionHelper.add("periodo atual não foi informado");
		}

		return dto;
	}

	@Override
	protected GenericoRepositorio<Vinculo, Long> repositorio() {
		return this.repositorio;
	}

	public VinculoRepositorio getRepositorio() {
		return repositorio;
	}

	@Autowired
	public void setRepositorio(VinculoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setEstudanteServico(EstudanteServico estudanteServico) {
		this.estudanteServico = estudanteServico;
	}

	public abstract Double obterPercentualConclusao(Long id);

	protected abstract Double gerarPercentualConclusao(Long id);
}
