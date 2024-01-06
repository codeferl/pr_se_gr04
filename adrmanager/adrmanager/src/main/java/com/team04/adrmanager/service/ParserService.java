package com.team04.adrmanager.service;

import com.team04.adrmanager.model.AdrEntities;
import com.team04.adrmanager.model.Artifact;
import com.team04.adrmanager.model.Commit;
import com.team04.adrmanager.model.Relation;
import com.team04.adrmanager.repo.AdrRepository;
import com.team04.adrmanager.repo.ArtifactRepository;
import com.team04.adrmanager.repo.CommitRepository;
import com.team04.adrmanager.repo.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ParserService {

    @Autowired
    private AdrRepository adrRepo;
    @Autowired
    private RelationRepository relationRepo;
    @Autowired
    private CommitRepository commitRepo;

    @Autowired
    private ArtifactRepository artifactRepo;

    private String extract(String txt, String start, String end){
        return txt.substring(txt.indexOf(start) + start.length(), txt.indexOf(end)).trim();
    }
    public AdrEntities parseAdr(String txt){

        String contextToken = "## Context";
        String decisionToken = "## Decision";
        String statusToken = "## Status";
        String consequencesToken = "## Consequences";
        String commitToken = "## Commit";
        String artifactsToken = "## Artifacts";
        String relationsToken = "## Relations";

        String title = extract(txt,":", contextToken);
        String context = extract(txt, contextToken, decisionToken);
        String decision = extract(txt, decisionToken, statusToken);
        String status = extract(txt, statusToken,consequencesToken );
        String consequences = extract(txt, consequencesToken,commitToken );
        String commits = extract(txt, commitToken,artifactsToken );
        String artifacts = extract(txt, artifactsToken,relationsToken );
        String relations = txt.substring(txt.indexOf(relationsToken) + relationsToken.length());


        return adrRepo.save( new AdrEntities(
                title,
                context,
                decision,
                status,
                consequences,
                parseCommits(commits),
                parseArtifacts(artifacts),
                parseRelations(relations)
        ));
    }

    String[] splitList(String txt){
        return(txt.split("- "));
    }

    List<Commit> parseCommits(String txt){
        LinkedList<Commit> list = new LinkedList<>();
        for(String x : splitList(txt)){
            Optional<Commit> test = commitRepo.findByCommitHash(x);
            Commit a;
            if(test.isEmpty()){
                a = new Commit();
                a.setCommitHash(x);
                a = commitRepo.save(a);
            }else{
                a = test.get();
            }
            list.add(a);
        }
        return list;
    }

    List<Artifact> parseArtifacts(String txt){
        LinkedList<Artifact> list = new LinkedList<>();
        for(String x : splitList(txt)){

            Optional<Artifact> test = artifactRepo.findByText(x);
            Artifact a;
            if(test.isEmpty()){
                a = new Artifact();
                a.setText(x);
                a = artifactRepo.save(a);
            }else{
                a = test.get();
            }
            list.add(a);
        }
        return list;
    }

    List<Relation> parseRelations(String txt){
        LinkedList<Relation> list = new LinkedList<>();
        for(String x : splitList(txt)) {
            if(!x.isEmpty() && !x.isBlank() ) {
                Optional<Relation> test = relationRepo.findByTxt(x);
                Relation a;
                if (test.isEmpty()) {
                    a = new Relation();
                    a.setTxt(x);
                    a = relationRepo.save(a);
                } else {
                    a = test.get();
                }
                list.add(a);
            }
        }
        return list;
    }
}

