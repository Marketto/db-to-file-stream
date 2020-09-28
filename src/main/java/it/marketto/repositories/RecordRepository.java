package it.marketto.repositories;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import it.marketto.entities.TestEntity;

public interface RecordRepository extends Repository<TestEntity, Long> {
	
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
	@Query("select id, data from test2")
	Stream<Object[]> getAll();
}
