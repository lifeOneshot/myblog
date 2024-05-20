package ce.mnu.myblog;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long num;

    @Column(length=20, nullable=false)
    private String author;

    @Column(length=50, nullable=false)
    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String body;
    
    @Column(nullable=false)
    private int viewcount = 0; // 추가된 조회수 속성
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<Comment> comments;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOGUSER_USERNO")
    private BlogUser user;

    public Long getNum() { return num; }
	public void setNum(Long n) { num=n; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String a) { author=a;}
	
	public String getTitle() { return title; }
	public void setTitle(String t) { title=t; }
	
	public String getBody() { return body; }
	public void setBody(String b) { body=b; }
	
	public void setUserNo(BlogUser user) {this.user=user;}
    public BlogUser getUserNo() {return user;}
    
	public List<Comment> getComments() { return comments;}
	
	public int getViewCount() { return viewcount; }
    public void setViewCount(int c) { this.viewcount = c; }
}