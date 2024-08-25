package mattmck.mywebservice.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import mattmck.mywebservice.persistence.oradb.entity.OraDbXaTestTable;
import mattmck.mywebservice.persistence.oradb.repository.OraDbXaTestRepository;
import mattmck.mywebservice.persistence.pgdb.entity.PgDbXaTestTable;
import mattmck.mywebservice.persistence.pgdb.repository.PgDbXaTestRepository;

@Service
@Transactional
@Slf4j
public class TestService {

	@Autowired
	private PgDbXaTestRepository pgDbXaTestRepository;

	@Autowired
	private OraDbXaTestRepository oraDbXaTestRepository;
	
//	@Autowired
//	private JmsTemplate jmsTemplate;

//	@Autowired
//    @Qualifier("jmsTemplate1")
//    private JmsTemplate jmsTemplate1;
//	
//	@Autowired
//    @Qualifier("jmsTemplate2")
//    private JmsTemplate jmsTemplate2;
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public String testSuccess() {
		log.debug("testSuccess");

		testOracleInsert();
		testPostgresInsert();
		
		return  "SUCCESS " + LocalDateTime.now();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
	public String testFail() {
		log.debug("testFail");
		
		testOracleInsert();
		testPostgresInsert();
		
		throw new RuntimeException("FAIL");
	}

	
	
	private void testOracleInsert() {
		OraDbXaTestTable entity = new OraDbXaTestTable();
		entity.setId(Objects.toString(UUID.randomUUID()));
		entity.setValue("Test " + LocalDateTime.now());
		oraDbXaTestRepository.save(entity);
	}
	
	private void testPostgresInsert() {
		PgDbXaTestTable entity = new PgDbXaTestTable();
		entity.setId(Objects.toString(UUID.randomUUID()));
		entity.setValue("Test " + LocalDateTime.now());
		pgDbXaTestRepository.save(entity);
	}
}
