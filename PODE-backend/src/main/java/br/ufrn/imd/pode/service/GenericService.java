package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.AbstractModel;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericService<T extends AbstractModel<PK>, PK extends Serializable> {

	protected String modelName;

	public GenericService() {
		this.modelName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
	}

	private String getModelName() {
		return this.modelName;
	}

	protected abstract GenericRepository<T, PK> repository();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findById(PK id) {
		Optional<T> entity = repository().findById(id);
		if (!entity.isPresent()) {
			throw new EntityNotFoundException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		}
		return entity.get();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAll(Integer lim, Integer pg) {
		return repository().findAllByAtivoIsTrueOrderByDataCriacaoDesc(PageRequest.of(pg, lim));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T save(T entity) {
		return repository().save(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> saveAll(List<T> entities) {
		return repository().saveAll(entities);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(PK id) {
		Optional<T> entity = repository().findById(id);
		if (!entity.isPresent()) {
			throw new EntityNotFoundException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		} else {
			repository().deleteById(id);
		}
	}
}