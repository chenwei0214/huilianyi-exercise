package com.cloudhelios.atlantis.service.impl;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.exception.CustomException;
import com.cloudhelios.atlantis.util.Page;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-30 上午11:40
 * description:
 */
@Component
public class LuceneService {

//    private Analyzer analyzer;

    @Value("${lucene.path}")
    private String path;


    public IndexWriter getIndexWriter() {
        try {
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
            File indexFile = new File(path);
            Directory directory = FSDirectory.open(indexFile.toPath());
            return new IndexWriter(directory, cfg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("10004", "获取IndexWriter失败");
        }
    }

    public IndexReader getIndexReader() {
        try {
            File indexFile = new File(path);
            // 指定查询的索引目录
            Directory directory = FSDirectory.open(indexFile.toPath());
            // 创建流对象
            return DirectoryReader.open(directory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("10005", "获取IndexReader失败");
        }
    }

    public void addDocument(Document document) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = getIndexWriter();
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Document userToDocument(AtlUser atlUser) {
        Document document = new Document();
        Field id = new StringField("id", atlUser.getId().toString(), Field.Store.YES);
        document.add(id);
        if (!StringUtils.isEmpty(atlUser.getUsername())) {
            Field username = new TextField("username", atlUser.getUsername(), Field.Store.YES);
            document.add(username);
        }
        if (!StringUtils.isEmpty(atlUser.getEmail())) {
            Field email = new TextField("email", atlUser.getEmail(), Field.Store.YES);
            document.add(email);
        }
        if (!StringUtils.isEmpty(atlUser.getPhone())) {
            Field phone = new TextField("phone", atlUser.getPhone(), Field.Store.YES);
            document.add(phone);
        }
        if (!StringUtils.isEmpty(atlUser.getEmployeeId())) {
            Field employeeId = new TextField("employeeId", atlUser.getEmployeeId(), Field.Store.YES);
            document.add(employeeId);
        }
        return document;
    }

    public void addUsers(List<AtlUser> atlUsers) {
        if (atlUsers != null && !atlUsers.isEmpty()) {
            IndexWriter indexWriter = null;
            try {
                indexWriter = getIndexWriter();
                for (AtlUser atlUser : atlUsers) {
                    indexWriter.addDocument(userToDocument(atlUser));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (indexWriter != null) {
                    try {
                        indexWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updateDocument(Term term, Document document) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = getIndexWriter();
            indexWriter.updateDocument(term, document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void delDocument(Term term) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = getIndexWriter();
            getIndexWriter().deleteDocuments(term);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Page<AtlUser> searchUser(Query query, Page<AtlUser> page) {
        IndexReader indexReader = null;
        try {
            indexReader = getIndexReader();
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Sort sort = new Sort(new SortField("employeeId", SortField.Type.SCORE, true));
            TopFieldDocs topDocs = searcher.search(query, 200, sort);
            // 根据获取查询到的总数据
            long totalHits = topDocs.totalHits;
            page.setTotalRecord(totalHits);
            int currentPage = page.getPage();
            int pageSize = page.getPageSize();
            int totalPage = (int) totalHits / pageSize;
            if (totalHits % pageSize != 0) {
                totalPage++;
            }
            page.setTotalPage(totalPage);
            // 将查询到的索引数据赋值给数组
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            if (scoreDocs != null && scoreDocs.length != 0) {
                int begin = pageSize * (currentPage - 1);
                int end = Math.min(begin + pageSize, (int) totalHits);
                List<AtlUser> list = new ArrayList<>();
                for (int i = begin; i < end; i++) {
                    int docID = scoreDocs[i].doc;
                    Document document = searcher.doc(docID);
                    AtlUser atlUser = new AtlUser();
                    atlUser.setId(Long.parseLong(document.get("id")));
                    atlUser.setEmployeeId(document.get("employeeId"));
                    atlUser.setPhone(document.get("phone"));
                    atlUser.setEmail(document.get("email"));
                    atlUser.setUsername(document.get("username"));
                    list.add(atlUser);
                }
                page.setData(list);
            }
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("10003", "lucene查询用户信息失败");
        } finally {
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
