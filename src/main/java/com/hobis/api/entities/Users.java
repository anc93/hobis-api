package com.hobis.api.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonPropertyOrder({"id", "username", "totalScore"})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, unique = true, name = "user_name")
    private String username;

    @Column(length = 45, name = "total_score")
    private Double totalScore;

    public Users(Integer id, String username, Double totalScore) {
        super();
        this.id = id;
        this.username = username;
        this.totalScore = totalScore;
    }

    public Users() {

    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
