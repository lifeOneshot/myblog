package ce.mnu.myblog;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class BlogUser {
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long userNo;
	
	@Column(length=50, unique=true, nullable=false)
	private String email;
	
	@Column(length=20, unique=true, nullable=false)
	private String name;
	
	@Column(length=20, nullable=false, name="password")
	private String passwd;
	
	public Long getUserNo() { return userNo; }
	public void setUserNo(Long n) { userNo=n; }
	
	public String getEmail() { return email; }
	public void setEmail(String e) { email=e; }
//테스트용
	
	public String getName() { return name; }
	public void setName(String n) {name=n;}
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<Comment> comments;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Article> articles;
	
	public List<Comment> getComments() { return comments;}
	
	public List<Article> getArticle() { return articles;}
	
	public String getPasswd() { return passwd; }
	public void setPasswd(String p) { passwd=p; }
}