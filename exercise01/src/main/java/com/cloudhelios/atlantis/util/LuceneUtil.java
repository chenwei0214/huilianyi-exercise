package com.cloudhelios.atlantis.util;

import com.cloudhelios.atlantis.exception.CustomException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

/**
 * author: chenwei
 * createDate: 18-8-28 下午8:53
 * description:
 */
public class LuceneUtil {

    public static IndexWriter getIndexWriter(Analyzer analyzer, String path) {
        try {
            IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
            File indexFile = new File(path);
            Directory directory = FSDirectory.open(indexFile.toPath());
            return new IndexWriter(directory, cfg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("10004", "获取IndexWriter失败");
        }
    }

    public static IndexReader getIndexReader(String path) {
        try {
            File indexFile = new File(path);
            // 指定查询的索引目录
            Directory directory = FSDirectory.open(indexFile.toPath());
            // 创建流对象
            return DirectoryReader.open(directory);
            // 通过流对象创建索引搜索对象
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("10005", "获取IndexReader失败");
        }
    }

}
