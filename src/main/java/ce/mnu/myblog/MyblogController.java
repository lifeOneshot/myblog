package ce.mnu.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.*;


@Controller
@RequestMapping(path={"", "/"})
public class MyblogController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = {"", "/"})
	public String main(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		if (email != null) {
			model.addAttribute("login_C", true);
			model.addAttribute("logout_C", false);
			return "main";
		}
		model.addAttribute("login_C", false);
		model.addAttribute("logout_C", true);
		return "main";
	}
	
	@GetMapping(path="/signup")
	public String signup(Model model) {
		model.addAttribute("user", new BlogUser());
		return "signup_input";
	}
	
	@PostMapping(path="/signup")
	public String signup(@ModelAttribute BlogUser user, Model model) {
		userRepository.save(user);
		model.addAttribute("name", user.getName());
		return "signup_done";
	}
	
	@PostMapping(path="/login")//로그인 기능
	public String login(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd, 
			HttpServletRequest request,
			HttpSession session, RedirectAttributes rd) {
		BlogUser user = userRepository.findByEmail(email);
		if(user != null){
			if(passwd.equals(user.getPasswd())){
				session.setAttribute("email", email);
				return "redirect:/";
			}
		}
		request.setAttribute("msg", "이메일 혹은 비밀번호가 틀렸습니다.");
        request.setAttribute("url", "/login");
        return "alert";
	}

	@GetMapping(path="/login") //로그인
	public String loginForm(){
		return "login";
	}

	@GetMapping(path="/logout")//로그아웃
	public String logout(HttpSession session, Model model){
		session.invalidate();
		return "redirect:/";
	}
	
	//유저 찾기
	@PostMapping(path="/find")
	public String findUser(@RequestParam(name="email") String email,
			HttpServletRequest request,
			HttpSession session, Model model, RedirectAttributes rd){
		BlogUser user = userRepository.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "find_done";
		}
		request.setAttribute("msg", "이메일 혹은 비밀번호가 틀렸습니다.");
        request.setAttribute("url", "/login");
        return "alert";
	}
	
	@GetMapping(path="/find")
	public String find() {
		return "find_user";
	}
	
	//게시판 관련
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ArticleService articleService;
	
	@GetMapping(path="/bbs/write")
	public String boardForm(Model model, 
			HttpSession session,
			HttpServletRequest request) {
		String user = articleService.getUserData(session);
		
		if (user == null) {
			request.setAttribute("msg", "로그인이 필요한 기능입니다.");
	        request.setAttribute("url", "/login");
	        return "alert";
		}
		model.addAttribute("article", new Article());
		return "new_article";
	}
	
	@PostMapping(path="/bbs/add")
	public String addArticle(@ModelAttribute Article article, 
			HttpSession session,
			HttpServletRequest request,
			RedirectAttributes rd, Model model) {
		String user = articleService.getUserData(session);
		BlogUser currentUser = userRepository.findByEmail(user);
		
	    if (currentUser != null) {
	        article.setAuthor(currentUser.getName());
	    }
		
		articleRepository.save(article);
		model.addAttribute("article", article);
		return "saved";
	}
	
	@GetMapping(path="/bbs")
	public String getAllArticles(@RequestParam(name="pno", defaultValue="0") String pno, Model model) {
		Integer pageNo = 0;
		if(pno != null) {
			pageNo = Integer.valueOf(pno);
		}
		
		Integer pageSize = 10;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "num");
		Page<ArticleHeader> data = articleRepository.findArticleHeaders(paging);
		
		model.addAttribute("articles", data);
		return "articles";
	}
	
	@GetMapping(path="/read")
	public String readArticle(@RequestParam(name="num") String num,
			HttpSession session, Model model) {
		Long no = Long.valueOf(num);
		Article article = articleRepository.getReferenceById(no);
		model.addAttribute("article", article);
		session.setAttribute("num", no);
		
		Integer page = 0;
		Integer pageSize = 20;
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Comment> commentList = articleService.commentList(article, pageable);
		
		articleService.pageSet(model, commentList, "comments");
		model.addAttribute("comments", commentList);
		
		return "article";
	}
	
	@PostMapping(path="/comment")
	public String writeComment(@ModelAttribute Comment comment, @ModelAttribute Article article,
			Model model, HttpServletRequest request,
			HttpSession session, RedirectAttributes rd) {
		
		String user = articleService.getUserData(session);
		Long num = (Long)session.getAttribute("num");
		
		if (user == null) {
			request.setAttribute("msg", "로그인이 필요한 기능입니다.");
	        request.setAttribute("url", "/login");
	        return "alert";
		}
		
		String articleNum = num.toString();
		articleService.writeComment(comment, user, num);

		
		return "redirect:/read?num="+articleNum;
	}
}
