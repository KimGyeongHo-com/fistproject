package com.example.fistproject.controller;

import com.example.fistproject.dto.ArticleForm;
import com.example.fistproject.entity.Article;
import com.example.fistproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 연결해줌 자동으로!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        System.out.println(form.toString());

        // 1. DTO를 entity로 변환하여야 한다.
        Article article = form.toEntity();

        System.out.println(article.toString());
        // 2. repository에게 entity를 db안에 저장하게 함!
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "";
    }
}
