package ro.uvt.aplicatiejurnalcriptat;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	@Autowired
	private CustomUserDetailsService userDetailService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");

		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");

		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createNewUser(User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User existingUser = userDetailService.findUserByEmail(user.getEmail());

		if (existingUser != null) {
			bindingResult.rejectValue("email", "error.user", "Username already regisered.");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		} else {
			userDetailService.saveUser(user);
			modelAndView.addObject("successMessage", "User registered successfully.");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");

		}

		return modelAndView;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userDetailService.findUserByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullname());
		modelAndView.addObject("adminMessage", "Page only available for admins.");
		modelAndView.setViewName("dashboard");

		return modelAndView;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");

		return modelAndView;
	}

	@RequestMapping(value = { "/jurnal" }, method = RequestMethod.GET)
	public ModelAndView diary() {
		ModelAndView modelAndView = new ModelAndView();
		Diary diary = new Diary();
		modelAndView.addObject("diary", diary);
		modelAndView.setViewName("jurnal");

		return modelAndView;
	}

	@RequestMapping(value = { "/jurnal" }, method = RequestMethod.POST)
	public ModelAndView diaryEncrypt(Diary diary) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		ModelAndView modelAndView = new ModelAndView();
		userDetailService.saveDiary(diary);
		modelAndView.addObject("successMessage", "Message registered successfully.");
		modelAndView.setViewName("jurnal");
		
		return modelAndView;
	}
}
