package ce.mnu.myblog;

import jakarta.persistence.*;

@Entity
public class BlogUser {
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long userNo;
	
	@Column(length=50, unique=true, nullable=false)
	private String email;
	
	@Column(length=20, nullable=false)
	private String name;
	
	@Column(length=20, nullable=false, name="password")
	private String passwd;
	
	public Long getUserNo() { return userNo; }
	public void setUserNo(Long n) { userNo=n; }
	
	public String getEmail() { return email; }
	public void setEmail(String e) { email=e; }
	
	public String getName() { return name; }
	public void setName(String n) { name=n; }
	
	public String getPasswd() { return passwd; }
	public void setPasswd(String p) { email=p; }
}
