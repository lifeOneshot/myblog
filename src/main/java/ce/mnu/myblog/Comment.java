package ce.mnu.myblog;

import jakarta.persistence.*;


@Entity
@Table(name="COMMENTS")
public class Comment {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(length=20, nullable=false)
    private String author;

    @Column(length=2048, nullable=false)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARITCLE_NUM")
    private Article article;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOGUSER_USERNO")
    private BlogUser user;
    
    public void setId(Long id) {this.id=id;}
    public Long getId() {return id;}
    public void setAuthor(String a) {this.author=a;}
    public String getAuthor() {return author;}
    public void setContent(String c) {this.content=c;}
    public String getContent() {return content;}
    public void setArticleNum(Article article) {this.article=article;}
    public Article getArticleNum() {return article;}
    public void setUserNo(BlogUser user) {this.user=user;}
    public BlogUser getUserNo() {return user;}
    
}
