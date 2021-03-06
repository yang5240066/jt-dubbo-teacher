package com.tedu.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {
	
	@Test
	public void createIndext() throws Exception{
		
		//1,创建一个保存数据文件夹
		Directory directory = FSDirectory.open(new File("./index"));
		//2,指定一个中文分词器
		Analyzer analyzer = new IKAnalyzer();
		//3,设置配置信息
		IndexWriterConfig config = 
				new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		//4,创建一个写对象,document
		Document document = new Document();
		//5,创建要写的数据,document
		IndexWriter indexWriter = new IndexWriter(directory, config);
		//6,设置数据的属性
		//id,title,sellPoint.price
		document.add(new LongField("id", 1, Store.YES));
		document.add(new StringField("title", "BMW", Store.YES));
		document.add(new TextField("sellPoint", "I love you very much", Store.YES));
		document.add(new LongField("price", 999900, Store.YES));
		//7,写操作
		indexWriter.addDocument(document);
		//8,关闭资源
		indexWriter.close();
	}
	
}
