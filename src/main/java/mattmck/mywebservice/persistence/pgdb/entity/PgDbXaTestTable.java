package mattmck.mywebservice.persistence.pgdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="PGDB_XA_TEST")
@Data
public class PgDbXaTestTable {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private String id;
	
	@Column(name="VALUE")
	private String value;
}
