package ro.uvt.aplicatiejurnalcriptat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepoUser extends MongoRepository<User, String>{
	
	User findByEmail(String email);
	
}
