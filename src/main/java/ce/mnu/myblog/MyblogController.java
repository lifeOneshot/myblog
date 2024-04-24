package ce.mnu.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path="/myblog")
public class MyblogController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = {"", "/"})
	public String main(Model model) {
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
	
//	@PostMapping(path="/login")
//	public String login(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd, 
//			HttpSession session, RedirectAttributes rd) {
//		model.addAttribute("user", new BlogUser());		
//		return "login";
//	}
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
}
