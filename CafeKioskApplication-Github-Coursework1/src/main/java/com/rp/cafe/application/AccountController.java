package com.rp.cafe.application;

import java.io.IOException;
import java.math.BigDecimal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.rp.cafe.application.model.card.Card;
import com.rp.cafe.application.model.card.CardRepository;
import com.rp.cafe.application.model.transaction.Transaction;
import com.rp.cafe.application.model.transaction.TransactionRepository;
import com.rp.cafe.application.model.user.Users;
import com.rp.cafe.application.model.user.UsersCrudRepository;
import com.rp.cafe.application.model.user.UsersJpaRepository;
import com.rp.cafe.application.model.user.UsersRolesObjects;
import com.rp.cafe.application.model.user.role.RoleRepository;
import com.rp.cafe.application.model.user.role.UserRole;

@Controller
public class AccountController {

	// Transaction Table
	@Autowired
	private TransactionRepository tr;

	// Users Table
	@Autowired
	private UsersJpaRepository repo;

	// Card Table
	@Autowired
	private CardRepository cardrepo;

	// Role Table
	@Autowired
	private RoleRepository rolerepo;

	// Encoder
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersCrudRepository userRepository;

	// It sets tab active in the page and set web page title.
	public void page(Model model, String page, String title) {
		model.addAttribute(page, "active"); // set nav bar => page => active
		model.addAttribute("title", title);
	}

	// ----------------------------------------------------------------------------
	// Home Page
	// ----------------------------------------------------------------------------

	// set theme setting in session attribute
	@RequestMapping("/load")
	public String set(Model model, HttpSession session, Principal principal) {

		// get login user id
		long id = userRepository.getUserByUsername(principal.getName()).getId();

		// set login user id in session
		session.setAttribute("id", id);

		// get login user role
		String role = userRepository.getUserByUsername(principal.getName()).getRoles().toString();

		// set login user role in role
		session.setAttribute("role", role);

		Users user = userRepository.getUserByUsername(principal.getName());

		// get login user date and timing
		session.setAttribute("today", user.getDate().toString() + " " + user.getTiming().toString());

		// save new date and timing in the users table
		Users saveduser = userRepository.getUserByUsername(principal.getName());
		saveduser.setDate();
		saveduser.setTiming();
		userRepository.save(saveduser);

		// return reload dashboard page
		return "redirect:/dashboard";
	}

	@RequestMapping("/")
	public String home(Model model, HttpSession session) {

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "home", "ABC Cafe Online Kiosk");

		// return templates/Index.html
		return "Index";
	}

	// ----------------------------------------------------------------------------
	// Login Page 
	// ----------------------------------------------------------------------------

	@RequestMapping("/login")
	public String login(Model model) {

		// set home tab active and set ABC Cafe web page title
		page(model, "login", "ABC Cafe Online Kiosk Login");

		// return templates/login.html
		return "login";
	}

	// ----------------------------------------------------------------------------
	// Create User Account Page 
	// ----------------------------------------------------------------------------

	@RequestMapping("/account")
	public String account(Model model, HttpSession session) {

		// set the roles
		List<UserRole> roles = rolerepo.findAll();

		// set the roles list in drop down box
		model.addAttribute("roles", roles);

		// set empty Users Object into the create user form
		model.addAttribute("Users", new Users());

		// set the feedback message
		model.addAttribute("message", session.getAttribute("message"));

		// clear the feedback message
		session.setAttribute("message", "");

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "account", "ABC Cafe Online Kiosk Create Account");

		// return templates/account.html
		return "account";
	}

	@PostMapping("/user/save")
	public RedirectView saveUser(Users user, Model model, HttpSession session) throws IOException {

		// find existed user
		Users users = userRepository.getUserByUsername(user.getUsername());

		// no existed user
		if (users == null) {

			// encrypt the password
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// save user object into user table.
			Users savedUser = repo.save(user);

			// set the feedback message
			session.setAttribute("message", user.getUsername() + " is created in the system.");

		// existed user
		} else {

			// set the feedback message
			session.setAttribute("message",
					"Username: " + user.getUsername() + " is existed in the system. Please try it again");

		}

		System.out.println("This is user save page");
		
		// return reload templates/account.html
		return new RedirectView("/account", true);
	}

	// ----------------------------------------------------------------------------
	// Dashboard Page
	// ----------------------------------------------------------------------------

	@RequestMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {

		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		if (existed == 1) {

			// place user id
			model.addAttribute("userid", session.getAttribute("id"));

			// get login user bank card detail
			Card card = cardrepo.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

			// show login user card balance
			model.addAttribute("balance", card.yourCardBalance());

		}

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "dashboard", "ABC Cafe Online Kiosk Dashboard");

		System.out.println("This is Dashboard page");

		// return templates/dashboard.html
		return "dashboard";
	}

	// ----------------------------------------------------------------------------
	// Change Password Page
	// ----------------------------------------------------------------------------

	@RequestMapping("/user/password")
	public String password(Model model, HttpSession session) {

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "password", "ABC Cafe Online Kiosk Change Password");

		System.out.println("This is Change Password page");

		// return templates/user/password.html
		return "user/password";
	}

	@RequestMapping("/user/passwordchange")
	public String passwordchange(@RequestParam String password, Model model, HttpSession session) {

		Users user = userRepository.findById(Long.parseLong(session.getAttribute("id").toString())).get();

		user.setPassword(passwordEncoder.encode(password));

		userRepository.save(user);

		// set feedback message
		model.addAttribute("message", "Your new password is ready to use.");

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "password", "ABC Cafe Online Kiosk Change Password");

		System.out.println("This is Change Password page");

		// return templates/user/password.html
		return "user/password";
	}

	// ----------------------------------------------------------------------------
	// Bank Card Page
	// ----------------------------------------------------------------------------

	@RequestMapping("/card/link")
	public String cardlink(Model model, HttpSession session) {

		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set the feedback message
		model.addAttribute("message", session.getAttribute("message"));

		// clear the feedback message
		session.setAttribute("message", "");

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk Link Bank Card");
		
		if(session.getAttribute("card") != null) {
			model.addAttribute("Card", session.getAttribute("card"));
			model.addAttribute("card", "");
			
		} else {
			// set empty Users Object into the create user form
			model.addAttribute("Card", new Card());

		}

		// set user id into the create user form
		model.addAttribute("userid", session.getAttribute("id"));

		System.out.println("This is link bank card page");

		// return templates/card/link.html
		return "card/link";
	}

	@PostMapping("/card/save")
	public RedirectView saveCard(Card card, Model model, HttpSession session) throws IOException {

		if (Long.toString(card.getCardnumber()).length() == 16 && card.getCvv().toString().length() == 3) {
			// set card balance to 0.00
			card.setCardbalance(BigDecimal.ZERO);

			// save user object into user table.
			Card savedCard = cardrepo.save(card);

			session.setAttribute("message", "You have linked your bank card.");

		} else {
			session.setAttribute("card", card);
			session.setAttribute("message", "Card Number must be 16 numbers and CVV must be 3 numbers.");
		}

		System.out.println("This is card save page");
		
		// return reload templates/card/link.html
		return new RedirectView("/card/link", true);
	}

	@RequestMapping("/card/view")
	public String cardview(Model model, HttpSession session) {

		// check login user id bank card is existed or not
		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		if (existed == 1) {

			// get login user bank card detail
			Card card = cardrepo.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

			// place bank card detail in the table
			model.addAttribute("card", card);

		}

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk Bank Card");

		System.out.println("This is view bank card page");

		// return templates/card/view.html
		return "card/view";
	}

	// ----------------------------------------------------------------------------
	// Transaction Page
	// ----------------------------------------------------------------------------

	@RequestMapping("/transaction/topup")
	public String topup(Model model, HttpSession session) {

		// check login user id bank card is existed or not
		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		if (existed == 1) {

			// place user id
			model.addAttribute("userid", session.getAttribute("id"));

			// get login user bank card detail
			Card card = cardrepo.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

			// show login user card balance
			model.addAttribute("balance", card.yourCardBalance());

		}

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk Top Up Transaction");

		// set feedback message
		model.addAttribute("message", "");

		System.out.println("This is top up transaction page");

		// return templates/transaction/topup.html
		return "transaction/topup";
	}

	@RequestMapping("/transaction/add") // execute action
	public String addCardBalance(@RequestParam long balance, Model model, HttpSession session) {

		// check login user id bank card is existed or not
		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		// place user id
		model.addAttribute("userid", session.getAttribute("id"));

		// get login user bank card detail
		Card card = cardrepo.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

		// create new transaction object
		Transaction transaction = new Transaction(0, "Top-up", new BigDecimal(balance),
				Long.parseLong(session.getAttribute("id").toString()));

		// save new transaction object in transaction table
		tr.save(transaction);

		// add Card Balance
		BigDecimal result = card.getCardbalance().add(new BigDecimal(balance));

		// set new card balance
		card.setCardbalance(result);

		// save card result
		Card savedCard = cardrepo.save(card);

		// show login user card balance
		model.addAttribute("balance", card.yourCardBalance());

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk Top Up Transaction");

		// set feedback message
		model.addAttribute("message", "You have top up $" + balance + " to your account.");

		System.out.println("This is top up transaction page 2");

		// return templates/transaction/topup.html
		return "transaction/topup";
	}

	@RequestMapping("/transaction/view")
	public String tranview(Model model, HttpSession session) {

		// check login user id bank card is existed or not
		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		// place user id
		model.addAttribute("userid", session.getAttribute("id"));

		// place transaction table in ascending order
		model.addAttribute("transactions", tr.sortASCbyUserID(Long.parseLong(session.getAttribute("id").toString())));
		
		// set button text
		model.addAttribute("sortbutton", "Ascending Order");
		model.addAttribute("sortbuttonrevert", "Descending");

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk View Transaction");

		System.out.println("This is view transaction page");

		// return templates/transaction/view.html
		return "transaction/view";
	}

	@RequestMapping("/transaction/sort")
	public String transort(@RequestParam String sort, Model model, HttpSession session) {

		// check login user id bank card is existed or not
		long existed = cardrepo.checkCardExist(Long.parseLong(session.getAttribute("id").toString()));

		// set to show or hide the form.
		model.addAttribute("exist", existed);

		// place user id
		model.addAttribute("userid", session.getAttribute("id"));

		if (sort.equals("Ascending")) {
			// place transaction table in ascending order
			model.addAttribute("transactions",
					tr.sortASCbyUserID(Long.parseLong(session.getAttribute("id").toString())));
			// set button text
			model.addAttribute("sortbutton", "Ascending Order");
			model.addAttribute("sortbuttonrevert", "Descending");

		} else {
			// place transaction table in descending order
			model.addAttribute("transactions",
					tr.sortDESCbyUserID(Long.parseLong(session.getAttribute("id").toString())));
			// set button text
			model.addAttribute("sortbutton", "Descending Order");
			model.addAttribute("sortbuttonrevert", "Ascending");

		}

		// set home tab in active and set "ABC Cafe" in web page title
		page(model, "", "ABC Cafe Online Kiosk View Transaction");

		System.out.println("This is sort transaction page");

		// return templates/transaction/view.html
		return "transaction/view";
	}

}
