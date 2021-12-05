package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.AbstractModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends AbstractModel<PK>, PK extends Serializable> extends JpaRepository<T, PK> {
	@Override
	@Query(value = "select * from #{#entityName} where ativo = true", nativeQuery = true)
	List<T> findAll();

	List<T> findAllByAtivoIsTrueOrderByDataCriacaoDesc(Pageable pageable);

	@Override
	@Query(value = "select * from #{#entityName} where id = ?1 and ativo = true", nativeQuery = true)
	Optional<T> findById(PK arg0);

	@Query(value = "select * from #{#entityName} where id = ?1 and ativo = false", nativeQuery = true)
	Optional<T> findDeletedById(PK arg0);

	@Override
	default void delete(T arg0) {
		deleteById(arg0.getId());
	}

	default void trueDelete(T arg0) {
		trueDeletebyId(arg0.getId());
	}

	@Transactional
	@Modifying
	@Query(value = "DELETE from #{#entityName} e where e.id = :d_id", nativeQuery = true)
	void trueDeletebyId(@Param(value = "d_id") PK d_id);

	@Override
	@Transactional
	@Modifying
	@Query(value = "UPDATE #{#entityName} SET ativo=false where id = ?1", nativeQuery = true)
	void deleteById(PK arg0);

	@Override
	default void deleteAll(Iterable<? extends T> arg0) {
		arg0.forEach(entity -> deleteById(entity.getId()));
	}
}

