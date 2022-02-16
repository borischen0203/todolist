package com.boris.todolist.model.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table //mapping table name
@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String task;

    @Column
    Integer status;

    @Column
    String createTime;

    @Column
    String updateTime;
}