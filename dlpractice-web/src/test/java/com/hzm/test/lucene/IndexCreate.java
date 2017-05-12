package com.hzm.test.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.hzm.test.lucene.dao.BookDao;
import com.hzm.test.lucene.dao.impl.BookDaoImpl;
import com.hzm.test.lucene.pojo.Book;

public class IndexCreate {
	
	@Test
	public void create() throws IOException{
		// 采集数据
		BookDao dao = new BookDaoImpl();
		List<Book> list = dao.queryBooks();
		// 将采集到的数据封装到Document对象中
		List<Document> documents = new ArrayList<Document>();
		Document document;
		for (Book book : list) {
			document = new Document();
			// store:如果是yes，则说明存储到文档域中
			Field id = new StringField("id", book.getId().toString(), Store.YES);	// 图书ID
			Field name = new TextField("name", book.getName(), Store.YES);// 图书名称
			Field price = new FloatField("price", book.getPrice(),Store.YES);// 图书价格
			Field pic = new StoredField("pic", book.getPic());// 图书图片地址
			Field description = new TextField("description",book.getDescription(), Store.YES);// 图书描述
			// 将field域设置到Document对象中
			document.add(id);
			document.add(name);
			document.add(price);
			document.add(pic);
			document.add(description);
			documents.add(document);
		}

		
		Analyzer analyzer = new StandardAnalyzer();//创建分词器---标准分词器
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);//创建保存索引的配置
		File indexfile = new File("F:/tools_dead_local/lucene-home");//指定索引库的地址
		Directory directory = FSDirectory.open(indexfile);
		IndexWriter writer = new IndexWriter(directory, cfg);
		for (Document doc : documents) {//通过IndexWriter对象将Document写入到索引库中
			writer.addDocument(doc);
		}
		writer.close();// 关闭writer
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月24日下午7:10:26
	 * @description 
	 * @return void
	 * 
	 * 	通过Query子类来创建查询对象
	 *		Query子类常用的有：TermQuery、NumericRangeQuery、BooleanQuery
	 *			不能输入lucene的查询语法，不需要指定分词器
	 *		通过QueryParser来创建查询对象（常用）
	 *		QueryParser、MultiFieldQueryParser
	 *			可以输入lucene的查询语法、可以指定分词器
	 *
	 */
	//termQuery精确的词项查询
	@Test
	public void termQuery(){
		Query query = new TermQuery(new Term("description","java"));
		doSearch(query);
	}
	/**
	 * @author houzm
	 * @time 2017年4月24日下午7:38:01
	 * @description 数字范围查询--亲测中失败，本来应该有2条数据，但是返回0
	 * @return void
	 */
	@Test
	public void numericRangeQuery(){
		//创建NumericRangeQuery对象
		//参数：域的名称，最小值，最大值，是否包含最小值，是否包含最大值
		Query query = NumericRangeQuery.newFloatRange("price", 55f, 60f, true, true);
		doSearch(query);
	}
	
	@Test
	public void booleanQuery(){
		//创建BooleanQuery
		BooleanQuery query = new BooleanQuery();
		//创建TermQuery对象
		Query q1 = new TermQuery(new Term("description","java"));
		Query q2 = NumericRangeQuery.newFloatRange("price", 55f, 60f, true, false);
		query.add(q1, Occur.MUST);
		query.add(q2, Occur.MUST);
		doSearch(query);
	}
	private void doSearch(Query query) {
		// 创建IndexSearcher
		// 指定索引库的地址
		try {
			File indexFile = new File("F:/tools_dead_local/lucene-home");
			Directory directory = FSDirectory.open(indexFile);
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			// 通过searcher来搜索索引库
			// 第二个参数：指定需要显示的顶部记录的N条
			TopDocs topDocs = searcher.search(query, 10);

			// 根据查询条件匹配出的记录总数
			int count = topDocs.totalHits;
			System.out.println("匹配出的记录总数:" + count);
			// 根据查询条件匹配出的记录
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			for (ScoreDoc scoreDoc : scoreDocs) {
				// 获取文档的ID
				int docId = scoreDoc.doc;

				// 通过ID获取文档
				Document doc = searcher.doc(docId);
				System.out.println("商品ID：" + doc.get("id"));
				System.out.println("商品名称：" + doc.get("name"));
				System.out.println("商品价格：" + doc.get("price"));
				System.out.println("商品图片地址：" + doc.get("pic"));
				System.out.println("==========================");
				// System.out.println("商品描述：" + doc.get("description"));
			}
			// 关闭资源
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deletedIndex() throws Exception{
		//创建分词器，标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		//创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		Directory directory = FSDirectory.open(new File("F:/tools_dead_local/lucene-home"));
		//创建IndexWriter
		IndexWriter writer = new IndexWriter(directory, cfg);
		//Terms
		writer.deleteDocuments(new Term("id", "1"));
		writer.close();
	}
	@Test
	public void deletedAll() throws Exception{
		//创建分词器，标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		//创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		Directory directory = FSDirectory.open(new File("F:/tools_dead_local/lucene-home"));
		//创建IndexWriter
		IndexWriter writer = new IndexWriter(directory, cfg);
		//Terms
		writer.deleteAll();
		writer.close();
	}
	@Test
	public void updateIndex() throws Exception{
		//创建分词器，标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		//创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		Directory directory = FSDirectory.open(new File("F:/tools_dead_local/lucene-home"));
		//创建IndexWriter
		IndexWriter writer = new IndexWriter(directory, cfg);
		
		//第一个参数：指定查询条件
		//第二个参数：修改后的对象
		//修改时，如果根据查询条件，可以查询出结果，
		//	则将以前的删掉，然后覆盖新的document对象，
		//	如果没有查询出结果，则新增一个doc
		Document doc = new Document();
		doc.add(new TextField("name","lisi",Store.YES));
		writer.updateDocument(new Term("name","houzm"), doc);
		writer.close();
	}
}
