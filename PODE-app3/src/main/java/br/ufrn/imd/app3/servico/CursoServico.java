package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.servico.GradeCurricularServico;

import br.ufrn.imd.app3.modelo.Curso;
import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.dto.CursoDTO;
import br.ufrn.imd.app3.repositorio.CursoRepositorio;

@Service
@Transactional
public class CursoServico extends GradeCurricularServico<Curso, CursoDTO> {

	private ConteudoCursadoServico conteudoCursadoServico;
	private ConteudoServico conteudoServico;
	private CursoRepositorio repositorio;

	@Autowired
	public void setRepositorio(CursoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
	}

	@Autowired
	public void setDisciplinaBTIServico(ConteudoServico conteudoServico) {
		this.conteudoServico = conteudoServico;
	}

	@Override
	protected GenericoRepositorio<Curso, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public CursoDTO converterParaDTO(Curso entity) {
		return new CursoDTO(entity);
	}

	@Override
	public Curso converterParaEntidade(CursoDTO dto) {
		Curso curso = new Curso();

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
		// TODO

		if (dto.getDisciplinasObrigatorias() != null) {
			HashSet<ConteudoCursado> disciplinas = new HashSet<>();
			for (Long idDisciplinaPeriodo : dto.getDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
				try{
					disciplinas.add(this.conteudoCursadoServico.buscarPorId(idDisciplinaPeriodo));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
			}
			curso.setDisciplinasObrigatorias(new HashSet<>(disciplinas));
		}

		if (dto.getDisciplinasOptativas() != null) {
			HashSet<Conteudo> disciplinas = new HashSet<>();
			for (Long idDisciplina : dto.getDisciplinasOptativas()) {
				if (idDisciplina == null) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
				try {
					disciplinas.add(this.conteudoServico.buscarPorId(idDisciplina));
				} catch (EntityNotFoundException entityNotFoundException){
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
			}
			curso.setDisciplinasOptativas(new HashSet<>(disciplinas));
		}

		return curso;
	}

	@Override
	protected void validar(CursoDTO dto) {
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
		//TODO

		//Verifica disciplinasObrigatorias
		if (dto.getDisciplinasObrigatorias() != null) {
			for (Long idDisciplinaPeriodo : dto.getDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null || idDisciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.conteudoCursadoServico.buscarPorId(idDisciplinaPeriodo);
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
						this.conteudoServico.buscarPorId(idDisciplina);
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
