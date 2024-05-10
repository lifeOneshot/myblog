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
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.*;


@Controller
@RequestMapping(path="/myblog")
public class MyblogController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = {"", "/"})
	public String main(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		if (email != null) {
			model.addAttribute("asdf", true);
			model.addAttribute("qwer", false);
			return "main";
		}
		model.addAttribute("asdf", false);
		model.addAttribute("qwer", true);
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
			HttpSession session, RedirectAttributes rd) {
		BlogUser user = userRepository.findByEmail(email);
		if(user != null){
			if(passwd.equals(user.getPasswd())){
				session.setAttribute("email", email);
				return "login_done";
			}
		}
		rd.addFlashAttribute("reason", "wrong password");
		return "redirect:/error";
	}

	@GetMapping(path="/login") //로그인
	public String loginForm(){
		return "login";
	}

	@GetMapping(path="/logout")//로그아웃
	public String logout(HttpSession session){
		session.invalidate();
		return "main";
	}
	
	//유저 찾기
	@PostMapping(path="/find")
	public String findUser(@RequestParam(name="email") String email, HttpSession session, Model model, RedirectAttributes rd){
		BlogUser user = userRepository.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "find_done";
		}
		rd.addFlashAttribute("reason", "wrong email");
		return "redirect:/error";
	}
	
	@GetMapping(path="/find")
	public String find() {
		return "find_user";
	}
	
	//게시판 관련
	@Autowired
	private ArticleRepository articleRepository;
	
	@GetMapping(path="/bbs/write")
	public String boardForm(Model model) {
		model.addAttribute("article", new Article());
		return "new_article";
	}
	
	@PostMapping(path="/bbs/add")
	public String addArticle(@ModelAttribute Article article, HttpSession session, 
			RedirectAttributes rd, Model model) {
		String user = (String) session.getAttribute("email");
		if (user == null) {
			rd.addAttribute("reason", "login required");
			return "redirect:/error";
		}
		
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
		System.out.println(pageNo + " - " +paging+ " - " + data);
		
		model.addAttribute("articles", data);
		return "articles";
	}
	
	@GetMapping(path="/read")
	public String readArticle(@RequestParam(name="num") String num,
			HttpSession session, Model model) {
		Long no = Long.valueOf(num);
		Article article = articleRepository.getReferenceById(no);
		model.addAttribute("article", article);
		
		return "article";
	}
}
