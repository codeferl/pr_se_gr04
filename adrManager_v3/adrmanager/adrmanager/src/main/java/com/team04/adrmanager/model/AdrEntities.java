package com.team04.adrmanager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class AdrEntities implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    private Date randomDate;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String titel;
    @Column(columnDefinition = "TEXT")
    private String context;
    @Column(columnDefinition = "TEXT")
    private String decisions;

    @Column(columnDefinition = "TEXT")
    private String status;
    @Column(columnDefinition = "TEXT")
    private String consequenes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Commit> commit;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Artifact> artifacts;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Relation> relations;

    public AdrEntities() {}

    public AdrEntities(String titel, String context, String decisions, String status, String consequenes,  List<Commit> commit, List<Artifact> artifacts, List<Relation> relations, Date randomDate) {
        this.titel = titel;
        this.context = context;
        this.decisions = decisions;
        this.status = status;
        this.consequenes = consequenes;
        this.commit = commit;
        this.artifacts = artifacts;
        this.relations = relations;
        this.randomDate = randomDate;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDecisions() {
        return decisions;
    }

    public void setDecisions(String decisions) {
        this.decisions = decisions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsequenes() {
        return consequenes;
    }

    public void setConsequenes(String consequenes) {
        this.consequenes = consequenes;
    }

    public List<Commit> getCommit() {
        return commit;
    }

    public void setCommit(List<Commit> commit) {
        this.commit = commit;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public Date getRandomDate() {
        return randomDate;
    }

    public void setRandomDate(Date randomDate) {
        this.randomDate = randomDate;
    }

    @Override
    public String toString() {
        return "Adr{" +
                "titel='" + titel + '\'' +
                ", context='" + context + '\'' +
                ", decisions='" + decisions + '\'' +
                ", status='" + status + '\'' +
                ", consequenes='" + consequenes + '\'' +
                ", commit='" + commit + '\'' +
                ", artifacts='" + artifacts + '\'' +
                ", relations='" + relations + '\'' +
                ", randomDate=" + randomDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}//end class
