package ce.mnu.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path="/myblog")
public class MyblogController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = {"", "/"}) // 메인
	public String main(Model model) { 
		return "main";
	}
	
	@GetMapping(path="/signup") //회원가입
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
	
<<<<<<< Updated upstream
	@PostMapping(path="/login")//로그인 기능
	public String login(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd, 
			HttpSession session, RedirectAttributes rd) {
		BlogUser user = userRepository.findByEmail(email);
		if(user != null){
			if(passwd.equals(user.getPasswd())){
=======
	@GetMapping(path="/login") //로그인
	public String loginForm() {
		return "login";
	}
	
	@PostMapping(path="/login")
	public String login(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd, 
			HttpSession session, RedirectAttributes rd) {
		BlogUser user = userRepository.findByEmail(email);
		if(user != null) {
			if(passwd.equals(user.getPasswd())) {
>>>>>>> Stashed changes
				session.setAttribute("email", email);
				return "login_done";
			}
		}
<<<<<<< Updated upstream
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
		return "login";
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
=======
		rd.addFlashAttribute("reason", "wrong password")
		return "redirect:/error";
	}
//	@PostMapping(path="/find")
//	public String findUser(@RequestParam(name="email") String email, HttpSession session, Model model, RedirectAttributes rd){
//		BlogUser user = userRepository.findByEmail(email);
//		if(user != null) {
//			model.addAttribute("user", user);
//			return "find_done";
//		}
//		rd.addFlashAttribute("reason", "wrong email");
//		return "redirect:/error";
//	}
//	
//	@GetMapping(path="/find")
//	public String find() {
//		return "find_user";
//	}
>>>>>>> Stashed changes
}
