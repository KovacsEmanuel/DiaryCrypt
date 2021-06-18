package ro.uvt.aplicatiejurnalcriptat;

import javax.crypto.spec.IvParameterSpec;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "diary")
public class Diary {

	private String diary;
	
	private IvParameterSpec ivParameterSpec;
	
	private String plainText;

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public IvParameterSpec getIvParameterSpec() {
		return ivParameterSpec;
	}

	public void setIvParameterSpec(IvParameterSpec ivParameterSpec) {
		this.ivParameterSpec = ivParameterSpec;
	}

	public String getDiary() {
		return diary;
	}

	public void setDiary(String diary) {
		this.diary = diary;
	}

}
