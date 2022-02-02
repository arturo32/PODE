package br.ufrn.imd.app1.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.repositorio.CursoBTIRepositorio;
import org.springframework.util.StringUtils;

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
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica chm
		boolean statusChm = false;
		if (dto.getChm() == null || dto.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}

		//Verifica cho
		boolean statusCho = false;
		if (dto.getChobm() == null || dto.getChobm() <= 0) {
			exceptionHelper.add("chobm inválido");
		} else {
			statusCho = true;
		}

		//Verifica chom
		boolean statusChom = false;
		if (dto.getChopm() == null || dto.getChopm() <= 0) {
			exceptionHelper.add("chopm inválido");
		} else {
			statusChom = true;
		}

		//Verifica chcm
		boolean statusChcm = false;
		if (dto.getChcm() == null || dto.getChcm() <= 0) {
			exceptionHelper.add("chcm inválido");
		} else {
			statusChcm = true;
		}

		//Verifica cho em relação a chm
		if (statusCho && statusChm) {
			if (dto.getChopm() > dto.getChm()) {
				exceptionHelper.add("impossível chopm ser maior do que chm");
			}
		}

		//Verifica chom em relação a chm
		if (statusChom && statusChm) {
			if (dto.getChobm() > dto.getChm()) {
				exceptionHelper.add("impossível chobm ser maior do que chm");
			}
		}

		//Verifica chcm em relação a chm
		if (statusChcm && statusChm) {
			if (dto.getChcm() > dto.getChm()) {
				exceptionHelper.add("impossível chcm ser maior do que chm");
			}
		}

		//Verifica a relação entre cho, chom, chcm e chm
		if (statusCho && statusChm && statusChom && statusChcm) {
			if ((dto.getChobm() + dto.getChopm() + dto.getChcm()) != dto.getChm()) {
				exceptionHelper.add("cho, cho e chcm somados devem resultar em chm");
			}
		}

		//Verifica chem
		if (dto.getChem() == null || dto.getChem() <= 0) {
			exceptionHelper.add("chem inválido");
		}

		//Verifica chminp
		boolean chminp = false;
		if (dto.getChminp() == null || dto.getChminp() <= 0) {
			exceptionHelper.add("chminp inválido");
		} else {
			chminp = true;
		}

		//Verifica chmaxp
		boolean chmaxp = false;
		if (dto.getChmaxp() == null || dto.getChmaxp() <= 0) {
			exceptionHelper.add("chmaxp inválido");
		} else {
			chmaxp = true;
		}

		//Verifica a relação entre chminp e chmaxp
		if (chminp && chmaxp) {
			if (dto.getChminp() > dto.getChmaxp()) {
				exceptionHelper.add("impossível chminp ser maior do que chmaxp");
			}
		}
		//Verifica prazoMinimo
		boolean statusPrazoMinimo = false;
		if (dto.getPrazoMinimo() == null || dto.getPrazoMinimo() <= 0) {
			exceptionHelper.add("prazoMinimo inválido");
		} else {
			statusPrazoMinimo = true;
		}

		//Verifica prazoMaximo
		boolean statusPrazoMaximo = false;
		if (dto.getPrazoMaximo() == null || dto.getPrazoMaximo() <= 0) {
			exceptionHelper.add("prazoMaximo inválido");
		} else {
			statusPrazoMaximo = true;
		}

		//Verifica prazoEsperado
		boolean statusPrazoEsperado = false;
		if (dto.getPrazoEsperado() == null || dto.getPrazoEsperado() <= 0) {
			exceptionHelper.add("prazoEsperado inválido");
		} else {
			statusPrazoEsperado = true;
		}

		//Verifica a relação entre prazoMinimo, prazoMaximo e prazoEsperado
		if (statusPrazoMinimo && statusPrazoMaximo && statusPrazoEsperado) {
			if (dto.getPrazoMinimo() > dto.getPrazoMaximo()) {
				exceptionHelper.add("impossível prazoMinimo ser maior do que prazoMaximo");
			}
			if (dto.getPrazoEsperado() < dto.getPrazoMinimo()) {
				exceptionHelper.add("impossível prazoEsperado ser menor do que prazoMinimo");
			}
			if (dto.getPrazoEsperado() > dto.getPrazoMaximo()) {
				exceptionHelper.add("impossível prazoEsperado ser maior do que prazoMaximo");
			}
		}

		//Verifica disciplinasObrigatorias
		if (dto.getDisciplinasObrigatorias() != null) {
			for (Long idDisciplinaPeriodo : dto.getDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null || idDisciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoServico.buscarPorId(idDisciplinaPeriodo);
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + idDisciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasOptativas
		if (dto.getDisciplinasOptativas() != null) {
			for (Long idDisciplina : dto.getDisciplinasOptativas()) {
				if (idDisciplina == null || idDisciplina < 0) {
					exceptionHelper.add("disciplinaOptativa inconsistente");
				} else {
					try {
						this.disciplinaBTIServico.buscarPorId(idDisciplina);
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaOptativa(id=" + idDisciplina + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}
}
