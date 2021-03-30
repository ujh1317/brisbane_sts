package model.faq;
import java.sql.Timestamp;
public class FaqDto {
	private int num;
	private String name;
	private String email;
	private String subject;
	private String content;

	private Timestamp regdate;
	private String pass;
	private int count;
	private String question;
	private String answer;
	private String ip;
	private int ref;
	private int re_step;
	private int re_level;


	public FaqDto(){}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Timestamp getRegdate() {
		return regdate;
	}


	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getRef() {
		return ref;
	}


	public void setRef(int ref) {
		this.ref = ref;
	}


	public int getRe_step() {
		return re_step;
	}


	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}


	public int getRe_level() {
		return re_level;
	}


	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}

}
