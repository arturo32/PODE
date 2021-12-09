package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.BusinessException;
import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.AbstractModel;
import br.ufrn.imd.pode.model.dto.AbstractDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public abstract class GenericService<T extends AbstractModel<PK>, Dto extends AbstractDTO, PK extends Serializable> {

	protected String modelName;

	public GenericService() {
		this.modelName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
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

	protected abstract GenericRepository<T, PK> repository();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findById(PK id) {
		Optional<T> entity = repository().findById(id);
		if (entity.isEmpty()) {
			throw new EntityNotFoundException("Entidade do tipo '" + this.getModelName()
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
			throw new BusinessException("Entidade do tipo '" + this.getModelName()
					+ "' com id: '" + dto.getId() + "'já existe, caso queira modificá-la, use o método update");
		}
		return repository().save(convertToEntity(dto));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T update(Dto dto) {
		if (dto.getId() == null) {
			throw new BusinessException("Entidade do tipo '" + this.getModelName()
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
			throw new EntityNotFoundException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		} else {
			repository().deleteById(id);
		}
	}
}