package dl.test.lucene.dao;

import java.util.List;

import dl.test.lucene.pojo.Book;

public interface BookDao {
	public List<Book> queryBooks();
}
