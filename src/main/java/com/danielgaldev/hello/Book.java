package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String author;

    private String title;

  	public Integer getId() {
    		return id;
  	}

  	public void setId(Integer id) {
    		this.id = id;
  	}

  	public String getAuthor() {
    		return author;
  	}

  	public void setAuthor(String author) {
    		this.author = author;
  	}

  	public String getTitle() {
    		return title;
  	}

  	public void setTitle(String title) {
    		this.title = title;
  	}
}
