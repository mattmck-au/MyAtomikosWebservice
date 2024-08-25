package mattmck.mywebservice.persistence.pgdb.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mattmck.mywebservice.persistence.pgdb.entity.PgDbXaTestTable;

@Repository
@Transactional
public interface PgDbXaTestRepository extends JpaRepository<PgDbXaTestTable, Long> {

}
