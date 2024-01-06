package com.team04.adrmanager.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String commitHash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }


}
