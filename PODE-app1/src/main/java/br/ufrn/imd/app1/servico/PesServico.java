package br.ufrn.imd.app1.servico;

import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.app1.modelo.dto.PesDTO;
import br.ufrn.imd.app1.repositorio.PesRepositorio;
import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.app1.modelo.view.PesChOptativaCumprida;

@Service
@Transactional
public class PesServico extends GradeCurricularServico<Pes, PesDTO> {

	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private DisciplinaBTIServico disciplinaBTIServico;
	private PesRepositorio repositorio;

	@Autowired
	public void setRepositorio(PesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Override
	protected GenericoRepositorio<Pes, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public PesDTO converterParaDTO(Pes entity) {
		return new PesDTO(entity);
	}

	@Override
	public Pes converterParaEntidade(PesDTO dto) {
		Pes pes = new Pes();
		// Se for uma edição
		if (dto.getId() != null) {
			pes = this.buscarPorId(dto.getId());
		}
		pes.setId(dto.getId());
		if (dto.getNome() != null) {
			pes.setNome(dto.getNome());
		}
		if (dto.getChm() != null) {
			pes.setChm(dto.getChm());
		}
		if (dto.getChobm() != null) {
			pes.setChobm(dto.getChobm());
		}
		if (dto.getChopm() != null) {
			pes.setChopm(dto.getChopm());
		}
		if (dto.getDisciplinasObrigatorias() != null) {
			HashSet<DisciplinaPeriodo> disciplinaBTIS = new HashSet<>();
			for (Long disciplinaId : dto.getDisciplinasObrigatorias()) {
				if (disciplinaId == null) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
				try {
					disciplinaBTIS.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaId));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
			}
			pes.setDisciplinasObrigatorias(new HashSet<>(disciplinaBTIS));
		}

		if (dto.getDisciplinasOptativas() != null) {
			HashSet<DisciplinaBTI> disciplinaBTIS = new HashSet<>();
			for (Long disciplinaId : dto.getDisciplinasOptativas()) {
				if (disciplinaId == null) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
				try {
					disciplinaBTIS.add(this.disciplinaBTIServico.buscarPorId(disciplinaId));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
			}
			pes.setDisciplinasOptativas(new HashSet<>(disciplinaBTIS));
		}
		return pes;
	}

	@Override
	protected void validar(PesDTO dto) {
		//TODO validação
	}

	public Set<PesChObrigatoriaCumprida> obterPesComChObrigatoriaCumprida(long vinculoId) {
		return this.repositorio.findPesComChObrigatoriaCumprida(vinculoId);
	}

	public Set<PesChOptativaCumprida> obterPesComChOptativaCumprida(long vinculoId) {
		return this.repositorio.findPesComChOptativaCumprida(vinculoId);
	}

}
