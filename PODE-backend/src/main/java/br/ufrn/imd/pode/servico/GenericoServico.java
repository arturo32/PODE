package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

@Service
@Transactional
public abstract class GenericoServico<T extends ModeloAbstrato<PK>, Dto, PK extends Serializable> {

	protected String nomeModelo;

	public GenericoServico() {
		this.nomeModelo = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
		this.nomeModelo = this.nomeModelo.substring(this.nomeModelo.lastIndexOf(".") + 1);
	}

	public String obterNomeModelo() {
		return this.nomeModelo;
	}

	public abstract Dto converterParaDTO(T entity);

	public abstract T converterParaEntidade(Dto dto);

	protected abstract void validarModoPersistencia(TipoPersistencia tipoPersistencia, Dto dto);

	protected abstract void validar(Dto dto);

	public Collection<Dto> converterParaListaDTO(Collection<T> entidades) {
		return entidades.stream().map(this::converterParaDTO).collect(Collectors.toList());
	}

	protected abstract GenericoRepositorio<T, PK> repositorio();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T buscarPorId(PK id) {
		Optional<T> entidade = repositorio().findById(id);
		if (entidade.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade do tipo '" + this.obterNomeModelo()
					+ "' de id: '" + id + "' não encontrada");
		}
		return entidade.get();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> buscarTodos(Integer lim, Integer pg) {
		return repositorio().findAllByAtivoIsTrueOrderByDataCriacaoDesc(PageRequest.of(pg, lim));
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> buscarPorIds(List<PK> ids) {
		return repositorio().findAllByAtivoIsTrueAndIdIsInOrderByDataCriacaoDesc(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> buscarPorIntervalo(PK startId, PK endId) {
		return repositorio().findAllByAtivoIsTrueAndIdBetweenOrderByDataCriacaoDesc(startId, endId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T salvar(Dto dto) {
		this.validarModoPersistencia(TipoPersistencia.ADICIONAR, dto);
		this.validar(dto);
		return repositorio().save(converterParaEntidade(dto));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T atualizar(Dto dto) {
		this.validarModoPersistencia(TipoPersistencia.ATUALIZAR, dto);
		this.validar(dto);
		return repositorio().save(converterParaEntidade(dto));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void remover(PK id) {
		Optional<T> entidade = repositorio().findById(id);
		if (entidade.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade do tipo '" + this.obterNomeModelo()
					+ "' de id: '" + id + "' não encontrada");
		} else {
			repositorio().deleteById(id);
		}
	}
}