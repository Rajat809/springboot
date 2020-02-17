package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import com.via.springbootweb.CourseRepo;

//import com.via.springbootweb.Course;

@Controller
public class AppController {

	@Autowired
	private ProductService service; 
	
	@Autowired
	ProductRepository productRepo;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewProductPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);
		
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product", product);
		
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";		
	}
	
//	@RequestMapping("/search/{name}")
//	public String searchProduct(@PathVariable(name = "name") String name) {
//		service.search(name);
//		return "redirect:/";		
//	}
	
//	@RequestMapping("/search/{name}")
//	List<Product> product = ;
	
	@RequestMapping(path="/search/{name}" , method=RequestMethod.GET)
	public ResponseEntity<Product> findProductByName(@PathVariable("name") String name){
		Product product= productRepo.findByName(name);
		System.out.println(product);
		ResponseEntity<Product> re = null;
		if(product == null){
			re = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return re;
		}
		re = new ResponseEntity<>(product, HttpStatus.OK);
		return re;
	}
	
}
