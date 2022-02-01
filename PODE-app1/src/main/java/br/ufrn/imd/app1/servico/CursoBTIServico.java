package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.repositorio.CursoBTIRepositorio;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@Transactional
public class CursoBTIServico extends GenericoServico<CursoBTI, CursoBTIDTO, Long> {

	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private DisciplinaBTIServico disciplinaBTIServico;
	private CursoBTIRepositorio repositorio;

	@Autowired
	public void setRepositorio(CursoBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	protected GenericoRepositorio<CursoBTI, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public CursoBTIDTO converterParaDTO(CursoBTI entity) {
		return new CursoBTIDTO(entity);
	}

	@Override
	public CursoBTI converterParaEntidade(CursoBTIDTO dto) {
		CursoBTI curso = new CursoBTI();

		//Se for uma edição
		if (dto.getId() != null){
			curso = this.buscarPorId(dto.getId());
		}

		curso.setId(dto.getId());
		if (dto.getNome() != null){
			curso.setNome(dto.getNome());
		}
		if (dto.getChm() != null){
			curso.setChm(dto.getChm());
		}
		if (dto.getChobm() != null){
			curso.setChobm(dto.getChobm());
		}
		if (dto.getChopm() != null){
			curso.setChopm(dto.getChopm());
		}
		if (dto.getChcm() != null){
			curso.setChcm(dto.getChcm());
		}
		if (dto.getChem() != null){
			curso.setChem(dto.getChem());
		}
		if (dto.getChminp() != null){
			curso.setChminp(dto.getChminp());
		}
		if (dto.getChmaxp() != null){
			curso.setChmaxp(dto.getChmaxp());
		}
		if (dto.getPrazoMinimo() != null){
			curso.setPrazoMinimo(dto.getPrazoMinimo());
		}
		if (dto.getPrazoMaximo() != null){
			curso.setPrazoMaximo(dto.getPrazoMaximo());
		}
		if (dto.getPrazoEsperado() != null){
			curso.setPrazoEsperado(dto.getPrazoEsperado());
		}

		if (dto.getDisciplinasObrigatorias() != null) {
			HashSet<DisciplinaPeriodo> disciplinas = new HashSet<>();
			for (Long idDisciplinaPeriodo : dto.getDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
				try{
					disciplinas.add(this.disciplinaPeriodoServico.buscarPorId(idDisciplinaPeriodo));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
			}
			curso.setDisciplinasObrigatorias(new HashSet<>(disciplinas));
		}

		if (dto.getDisciplinasOptativas() != null) {
			HashSet<DisciplinaBTI> disciplinas = new HashSet<>();
			for (Long idDisciplina : dto.getDisciplinasOptativas()) {
				if (idDisciplina == null) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
				try {
					disciplinas.add(this.disciplinaBTIServico.buscarPorId(idDisciplina));
				} catch (EntityNotFoundException entityNotFoundException){
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
			}
			curso.setDisciplinasOptativas(new HashSet<>(disciplinas));
		}

		return curso;
	}

	@Override
	protected void validar(CursoBTIDTO dto) {
		// TODO
	}
}
