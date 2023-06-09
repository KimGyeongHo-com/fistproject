package com.example.fistproject.controller;

import com.example.fistproject.dto.ArticleForm;
import com.example.fistproject.entity.Article;
import com.example.fistproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 어노테이션(@)
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 연결해줌 자동으로!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        log.info(form.toString());
        //System.out.println(form.toString()); ->로깅으로 대체해봅시다.


        // 1. DTO를 entity로 변환하여야 한다.
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());


        // 2. repository에게 entity를 db안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") //id 변수
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        // 1 : id로 데이터를 가져온다.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2 : 가져온 데이터를 모델에 등록하고
        model.addAttribute("article", articleEntity);
        // 3 : 보여줄 페이지 설정(view)
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1 : 모든 article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();
        // = IterableList<Article> articleEntityList = articleRepository.findAll();


        // 2 : 가져온 article 묶음을 view로 전달
        model.addAttribute("articleList",articleEntityList);

        // 3: view 페이지를 설정

        return "articles/index"; // articles/index.mustache
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){

        // 수정할 데이터를 가져오기
       Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록
        model.addAttribute("article",articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1: dto를 entity로 변환.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: entity를 디비로 저장
        // 2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2: 기존 데이터의 값을 수정,갱신
        if(target != null){
            articleRepository.save(articleEntity); //
        }

        // 3: 수정 결과 페이지로 redirect
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청이 들어왔습니다.");

        // 1: 삭제 대상을 가져옴.
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2: 그 대상을 삭제
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "delete Completed.");
        }
        // 3: 삭제가 완료되면 결과 페이지로 리다이렉트한다.
        return "redirect:/articles/";
    }
}
