package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "math_example")
public class MathExample {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "example")
    private String example;
    @Column(name = "result")
    private String result;

    public MathExample() {
    }

    public MathExample(int id, String example, String result) {
        this.id = id;
        this.example = example;
        this.result = result;
    }

    public MathExample(String example) {
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return  this.example + "=" + this.result;
    }
}
