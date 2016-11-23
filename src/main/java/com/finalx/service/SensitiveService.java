package com.finalx.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tengyu on 2016/7/27.
 */
@Service
public class SensitiveService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    private static final String DEFAULT_REPLACEMENT = "***";

    private TrieNode rootNode = new TrieNode();

    //判断是否为一个符号
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement=DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();
        TrieNode tempNode=rootNode;
        int begin=0;//开始的位置，前面对后面不对就会回到这里。
        int position=0;//当前比较的位置
        while (position < text.length()) {
            //1.获取文本字符
            Character c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            //查看字典树里是否有对应字符,用于临时比较，比完又赋值为rootNode
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                //如果没有就把字符保存到新字符串中，有就替换为***
                result.append(text.charAt(begin));
                position = begin + 1;
                begin=position;
                tempNode=rootNode;
            } else if (tempNode.isKeywordEnd()) {
                //发现敏感词，从begin到position的位置用replacement替换到
                result.append(replacement);
                ++position;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    /**
     * 添加一个关键字
     * @param lineText
     */
    private void addWord(String lineText) {
        TrieNode tempNode=rootNode;
        for (int i = 0; i < lineText.length(); i++) {
            Character c = lineText.charAt(i);
            if (isSymbol(c)) {
                continue;
            }
            //查看树中是否有字符，没有就添加进去
            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            //继续添加,色-》狼，情
            tempNode=node;
            if (i == lineText.length() - 1) {
                tempNode.setKeywordEnd(true);
            }

        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode=new TrieNode();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                lineText = lineText.trim();
                addWord(lineText);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("读取文件失败" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色   /(⊙o⊙)?情");
        s.addWord("色狼");
        s.addWord("色 情");
        s.addWord("fuck");
        System.out.println(s.filter("你好色情啊。fuck"));
    }

    /**
     * 敏感词树
     */
    private class TrieNode{
        private boolean end=false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }
}
