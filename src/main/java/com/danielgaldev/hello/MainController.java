package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.Book;
import hello.BookRepository;

@Controller
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private BookRepository bookRepository;

	@GetMapping(path="/add")
	public @ResponseBody String addNewBook (@RequestParam String author
			, @RequestParam String title) {

		Book b = new Book();
		b.setTitle(title);
		b.setAuthor(author);
		bookRepository.save(b);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Book> getAllBooks() {
		return bookRepository.findAll();
	}
}
