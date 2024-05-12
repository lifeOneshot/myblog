package ce.mnu.myblog;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	public String getUserData(HttpSession session) {
		String user = (String) session.getAttribute("email");
		return user;
	}
	
	public void writeComment(Comment comment, String email, Long num) {
		BlogUser user = userRepository.findByEmail(email);
		Article article = articleRepository.getReferenceById(num);
		comment.setUserNo(user);
		comment.setArticleNum(article);
		comment.setAuthor(user.getName());
		
		commentRepository.save(comment);
	}
	
//	public Page<Article> articleList(Pageable pageable){
//		return articleRepository.findAll(pagable);
//	}
	
	public Page<Comment> commentList(Article article, Pageable pageable){
		return commentRepository.findByArticle(article, pageable);
	}
	
	public void pageSet(Model model, Page<?> list, String listName) {
		int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute(listName, list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
	}


	
	
}
