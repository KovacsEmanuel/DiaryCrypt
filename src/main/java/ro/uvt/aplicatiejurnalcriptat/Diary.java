package ro.uvt.aplicatiejurnalcriptat;

import javax.crypto.spec.IvParameterSpec;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "diary")
public class Diary {

	private String diary;
	
	private byte[] iv;
	
	private String plainText;
	
	private String email;

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public String getDiary() {
		return diary;
	}

	public void setDiary(String diary) {
		this.diary = diary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
