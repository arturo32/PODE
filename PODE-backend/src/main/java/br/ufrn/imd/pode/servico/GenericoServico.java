package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

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

@Service
public abstract class GenericoServico<T extends ModeloAbstrato<PK>, Dto extends AbstratoDTO, PK extends Serializable> {

	protected String modelName;

	public GenericoServico() {
		this.modelName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
		this.modelName = this.modelName.substring(this.modelName.lastIndexOf(".")+1);
	}

	public String getModelName() {
		return this.modelName;
	}

	public abstract Dto convertToDto(T entity);

	public abstract T convertToEntity(Dto dto);

	public abstract Dto validate(Dto dto);

	public Collection<Dto> convertToDTOList(Collection<T> entities) {
		return entities.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public Collection<T> convertToEntityList(Collection<Dto> dtos) {
		return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
	}

	protected abstract GenericoRepositorio<T, PK> repository();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findById(PK id) {
		Optional<T> entity = repository().findById(id);
		if (entity.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		}
		return entity.get();
	}



	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAll(Integer lim, Integer pg) {
		return repository().findAllByAtivoIsTrueOrderByDataCriacaoDesc(PageRequest.of(pg, lim));
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findByIds(List<PK> ids) {
		return repository().findAllByAtivoIsTrueAndIdIsInOrderByDataCriacaoDesc(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findByInterval(PK startId, PK endId) {
		return repository().findAllByAtivoIsTrueAndIdBetweenOrderByDataCriacaoDesc(startId, endId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T save(Dto dto) {
		if (dto.getId() != null) {
			throw new NegocioException("Entidade do tipo '" + this.getModelName()
					+ "' com id: '" + dto.getId() + "'já existe, caso queira modificá-la, use o método update");
		}
		return repository().save(convertToEntity(dto));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T update(Dto dto) {
		if (dto.getId() == null) {
			throw new NegocioException("Entidade do tipo '" + this.getModelName()
					+ "' com id: '" + dto.getId() + "'ainda não existe, caso queira salvá-la, use o método save");
		}
		return repository().save(convertToEntity(dto));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> saveAll(List<T> entities) {
		return repository().saveAll(entities);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(PK id) {
		Optional<T> entity = repository().findById(id);
		if (entity.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		} else {
			repository().deleteById(id);
		}
	}
}