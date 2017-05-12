package com.hzm.test.lucene.dao;

import java.util.List;

import com.hzm.test.lucene.pojo.Book;

public interface BookDao {
	public List<Book> queryBooks();
}
