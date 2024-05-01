package ce.mnu.myblog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long num;

    @Column(length=20, nullable=false)
    private String author;

    @Column(length=50, nullable=false)
    private String title;

    @Column(length=2048, nullable=false)
    private String body;

    public Long getNum() { return num; }
	public void setNum(Long n) { num=n; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String a) { author=a; }
	
	public String getTitle() { return title; }
	public void setTitle(String t) { title=t; }
	
	public String getBody() { return body; }
	public void setBody(String b) { body=b; }
}