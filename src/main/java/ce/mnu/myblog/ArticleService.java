package ce.mnu.myblog;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

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
	
	//세션 이메일 체크 메서드
	public void loginCheck(HttpSession session, Model model) { 
		String EmailData = (String)session.getAttribute("email");
		System.out.println(EmailData);
		if (EmailData != null) {
			model.addAttribute("logined", true);
		}
		else{
			model.addAttribute("logined", false);
		}
		System.out.println(model+ " == "+EmailData +" ==" +model.getAttribute("logined"));
	}

	//댓글 생성 함수
	public void writeComment(Comment comment, String email, Long num) {
		BlogUser user = userRepository.findByEmail(email);
		Article article = articleRepository.getReferenceById(num);
		int updatedViewCount = article.getViewCount() - 1;
		article.setViewCount(updatedViewCount);
		articleRepository.save(article);
		comment.setUserNo(user);
		comment.setArticleNum(article);
		comment.setAuthor(user.getName());
		
		commentRepository.save(comment);
	}
	
//	public Page<Article> articleList(Pageable pageable){
//		return articleRepository.findAll(pagable);
//	}
	
	//댓글 페이징 함수
	public Page<Comment> commentList(Article article, Pageable pageable){
		return commentRepository.findByArticle(article, pageable);
	}
	
	//자동 페이징 추가
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
