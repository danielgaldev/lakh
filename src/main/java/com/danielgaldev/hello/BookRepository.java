package hello;

import org.springframework.data.repository.CrudRepository;

import hello.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
