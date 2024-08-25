package mattmck.mywebservice.persistence.oradb.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mattmck.mywebservice.persistence.oradb.entity.OraDbXaTestTable;

@Repository
@Transactional
public interface OraDbXaTestRepository extends JpaRepository<OraDbXaTestTable, Long> {
	
}
