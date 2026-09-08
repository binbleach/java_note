package com.huangjiabin.rag.controller;

import com.alibaba.cloud.ai.transformer.splitter.RecursiveCharacterTextSplitter;
import com.alibaba.cloud.ai.transformer.splitter.SentenceSplitter;
import com.huangjiabin.rag.reader.DocumentReaderFactory;
import com.huangjiabin.rag.reader.PdfMultimodalProcessor;
import com.huangjiabin.rag.splitter.MarkdownHeaderTextSplitter;
import com.huangjiabin.rag.splitter.ModalTextSplitter;
import com.huangjiabin.rag.splitter.OverlapParagraphTextSplitter;
import com.huangjiabin.rag.splitter.WordHeaderTextSplitter;
import com.huangjiabin.rag.utils.DocumentCleanUtils;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

/*
 *   文档分块
 */
@RestController
@RequestMapping("/rag")
public class RagSplitterController {

    @Autowired
    private DocumentReaderFactory documentReaderFactory;

    //springAi的固定大小分块
    @RequestMapping("/split")
    public String split(String filePath) {
        List<Document> documents;
        try {
            documents = DocumentCleanUtils.cleanDocuments(documentReaderFactory.read(new File(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Document document : documents) {
            System.out.println("bofore chunk : " + document.getText());
            System.out.println("");

            //springAi固定长度分块
            /*TextSplitter tokenTextSplitter = new TokenTextSplitter(
                    600,  //每块最多600tokens
                    300,    //每块最少600tokens
                    5,      //最少5块才入库
                    8000,   //最多拆分8000块
                    true    //保留句号，换行符

            );*/
            // 自定义的带重叠 (Overlap) 滑动窗口分块
            OverlapParagraphTextSplitter tokenTextSplitter = new OverlapParagraphTextSplitter(
                    100,
                    5);

            List<Document> chunkedDocuments = tokenTextSplitter.split(document);

            for (Document chunkedDocument : chunkedDocuments) {
                System.out.println("after chunk : " + chunkedDocument.getText());
                System.out.println("");
            }
            System.out.println("==============");
        }
        return "success";
    }

    //springAiAlibaba的递归分块
    @RequestMapping("/splitRecursive")
    public String splitRecursive(String filePath) {
        List<Document> documents;
        try {
            documents = documentReaderFactory.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Document document : documents) {
            System.out.println("bofore chunk : " + document.getText());
            System.out.println("");

            RecursiveCharacterTextSplitter splitter = new RecursiveCharacterTextSplitter(300, new String[]{"\n\n", "\n"});

            List<Document> chunkedDocuments = splitter.split(document);

            for (Document chunkedDocument : chunkedDocuments) {
                System.out.println("after chunk : " + chunkedDocument.getText());
                System.out.println("");
            }
            System.out.println("==============");
        }
        return "success";
    }


    /*
    *   语义分块（只支持英文）
    */
    @RequestMapping("/splitSentence")
    public String splitSentence() {
        SentenceSplitter splitter = new SentenceSplitter(100);

        //中文不支持
//        for (Document textSegment : splitter.split(new Document("""
//                《斗破苍穹》是中国网络作家天蚕土豆创作的玄幻小说，2009年4月14日起在起点中文网连载，2011年7月20日完结，首版由湖北少年儿童出版社出版。2010年7月，该作品部分章节被编为《废材当自强》由湖北少年儿童出版社出版 [22]。
//                小说以斗气大陆为背景，讲述天才少年萧炎从斗气尽失逐步成长为斗帝的历程，期间通过收集异火、修炼丹药突破困境，最终解开斗帝失踪之谜并前往大千世界 [23]。作品构建了炼药师体系、异火榜及天鼎榜等设定，其中炼药师需具备火木双属性斗气与灵魂感知力 [6]。
//                该小说全网点击量近100亿次，实体书累计销量超300万册，2017年7月荣登“2017猫片胡润原创文学IP价值榜”榜首 [13-14]。2020年8月被国家图书馆永久典藏并位列中国文化产业IP价值综合榜TOP50前五 [6]，其改编动画在腾讯视频创下2.6万热度值纪录，并推出盲盒、游戏等衍生品 [25]。幻维数码制作的动画年番《斗破苍穹》重现佛怒火莲等经典场景，多次入围华语剧集口碑榜前十 [24]。2025年1月入选“2024网络文学神作榜”，同年2月28日荣获2024阅文IP盛典20大荣耀IP [15-16]。2025年11月，上海金山区人民法院宣判国内首例AI著作权侵权案，用户擅自使用《斗破苍穹》角色“美杜莎”形象训练AI模型被判赔偿5万元 [26-29]。
//                """))) {
//            System.out.println(textSegment.getText());
//            System.out.println("-=======");
//        }

        for (Document textSegment : splitter.split(new Document("""
                   Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends, Ron Weasley and Hermione Granger, all of whom are students at Hogwarts School of Witchcraft and Wizardry. The main story arc concerns Harry's conflict with Lord Voldemort, a dark wizard who intends to become immortal, overthrow the wizard governing body known as the Ministry of Magic, and subjugate all wizards and non-magical people, known in-universe as Muggles.  \s

                   The series was originally published in English by Bloomsbury in the United Kingdom and Scholastic Press in the United States. A series of many genres, including fantasy, drama, coming-of-age fiction, and the British school story (which includes elements of mystery, thriller, adventure, horror, and romance), the world of Harry Potter explores numerous themes and includes many cultural meanings and references.[1] Major themes in the series include prejudice, corruption, madness, love, and death.[2] \s

                   Since the release of the first novel, Harry Potter and the Philosopher's Stone, on 26 June 1997, the books have found immense popularity and commercial success worldwide. They have attracted a wide adult audience as well as younger readers and are widely considered cornerstones of modern literature,[3][4] though the books have received mixed reviews from critics and literary scholars. As of February 2023, the books have sold more than 600 million copies worldwide, making them the best-selling book series in history, available in dozens of languages. The last four books all set records as the fastest-selling books in history, with the final instalment selling roughly 2.7 million copies in the United Kingdom and 8.3 million copies in the United States within twenty-four hours of its release. It holds the Guinness World Record for "Best-selling book series for children."[5] \s
                """))) {
            System.out.println(textSegment.getText());
            System.out.println("-=======");
        }


        return "success";
    }


    //父子分片-markdown
    @RequestMapping("/splitParent")
    public String splitParent() {
        MarkdownHeaderTextSplitter markdownHeaderTextSplitter = new MarkdownHeaderTextSplitter(Map.of("#", "一级标题", "##", "二级标题", "###", "三基标题"), false
                , false, true);

        String markdownTest = """
                #哒哒哒
                大叔大大
                
                ## dasdasda
                ### fcsafadfa
                dsada
                dasdas
                dasdsaddsadfwr
                
                ## dsadasd
                ## edawdada
                ### dasdasda
                dadafaf
                
                #### fsdfsfasfsf
                
                # e2ewaeaw
                ## dawdadas
                dsadasd
                ## dsadas
                ## dasda
                dsadad
                ### asfasdada
                ### dadad
                
                Eadsadada
                
                #### dfasda
                daddadsa
                """;
        List<Document> documents = markdownHeaderTextSplitter.split(new Document(markdownTest));
        for (Document document : documents) {
            System.out.println(document.getText());
            System.out.println(document.getMetadata());
            System.out.println("==============");

        }
        return "success";
    }

    //父子分片-文档
    @RequestMapping("/splitParentForWord")
    public String splitParentForWord() {
        System.out.println("========== 示例: 启用父子关系模式 ==========\n");

        // 创建分割器，启用父子关系
        WordHeaderTextSplitter splitter = new WordHeaderTextSplitter(
                Arrays.asList(1, 2, 3),
                false,
                false,
                false,  // 启用父子关系
                1000, 100
        );

        String filePath = "/Users/hollis/Downloads/OA系统使用操作手册.docx";
        try (InputStream is = new FileInputStream(filePath)) {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("wordInputStream", is);

            Document doc = new Document("", metadata);
            List<Document> segments = splitter.apply(Collections.singletonList(doc));

            // 打印父子关系
            System.out.println("文档结构树：\n");
            for (Document segment : segments) {
                Integer level = (Integer) segment.getMetadata().get("headingLevel");
                String chunkId = (String) segment.getMetadata().get("chunkId");
                String parentChunkId = (String) segment.getMetadata().get("parentChunkId");
                Integer segmentIndex = (Integer) segment.getMetadata().get("segmentIndex");

                if (level != null) {
                    String indent = "  ".repeat(level - 1);
                    System.out.println(indent + "- [级别" + level + "] " +
                            segment.getText());
                    System.out.println(indent + "  ChunkId: " + chunkId);
                    if (parentChunkId != null) {
                        System.out.println(indent + "  ParentId: " + parentChunkId);
                    }

                    if (segmentIndex != null) {
                        System.out.println(indent + "  segmentIndex: " + segmentIndex);
                    }
                }

                System.out.println("==============");
                System.out.println("==============");
                System.out.println("==============");
                System.out.println("==============");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }


    @Autowired
    private PdfMultimodalProcessor processer;

    @RequestMapping("mulitModal")
    public String mulitModal(String filePath) throws Exception {

        String result = processer.processPdf(new File(filePath));

        ModalTextSplitter modalTextSplitter = new ModalTextSplitter(300, 20);
        List<Document> documents = modalTextSplitter.split(new Document(result));

        for (Document document : documents) {
            System.out.println(document.getText());
            System.out.println(document.getMetadata());
            System.out.println("==============");
        }
        return "success";
    }


}