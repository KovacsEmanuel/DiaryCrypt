package ro.uvt.aplicatiejurnalcriptat;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepoDiary extends MongoRepository<Diary, String> {

	void save(String cipherText);

	void save(IvParameterSpec ivParameterSpec);
	
	List<Diary> findByEmail(String email);

}
