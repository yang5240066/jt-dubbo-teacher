package com.tedu.lucene;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class SearchTest {
	
	@Test
	public void search() throws Exception{
		//1,指定从哪查数据
		Directory directory = FSDirectory.open(new File("./index"));
		//2,创建读的对象
		IndexReader indexReader = IndexReader.open(directory);
		//3,创建查询对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//4,指定关键字
		Query query = new TermQuery(new Term("sellPoint","love"));
		TopDocs topDocs = indexSearcher.search(query, 20);
		//5,遍历
		for(ScoreDoc scoreDoc : topDocs.scoreDocs){
			int index = scoreDoc.doc;
			Document document = indexSearcher.doc(index);
			String sellPoint = document.get("sellPoint");
			System.out.println(index + ":" + sellPoint);
		}
		//6,关闭资源
		indexReader.close();
	}
	
}
