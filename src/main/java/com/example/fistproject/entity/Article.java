package com.example.fistproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식 가능.
@AllArgsConstructor
@NoArgsConstructor// 디폴트 생성자 추가 어노테이션
@ToString
@Getter
public class Article {

    @Id
    @GeneratedValue
    private Long id; // 주민번호(대푯값)

    @Column
    private String title;

    @Column
    private String content;



}
