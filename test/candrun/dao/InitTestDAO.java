package candrun.dao;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

public class InitTestDAO extends JdbcDaoSupport{
	
	@Value("classpath:/initDb.sql")
	private Resource initDbSchema;
	
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(initDbSchema);
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}
	
}
