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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    private Date generateRandomDate() {
        Random random = new Random();

        // Setze den Start- und Endbereich für das Jahr 2023
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 12, 31);

        // Berechne einen zufälligen Tag zwischen dem Start- und Enddatum
        long randomDay = start.toEpochDay() + random.nextInt((int) (end.toEpochDay() - start.toEpochDay()));

        // Erzeuge ein zufälliges LocalDate-Objekt und konvertiere es in ein Date-Objekt
        LocalDate randomLocalDate = LocalDate.ofEpochDay(randomDay);
        return Date.from(randomLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public AdrEntities parseAdr(String txt){

        String contextToken = "## Context";
        String decisionToken = "## Decision";
        String statusToken = "## Status";
        String consequencesToken = "## Consequences";
        String commitToken = "## Commit";
        String artifactsToken = "## Artifacts";
        String relationsToken = "## Relations";
        Date randomDate = generateRandomDate();

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
                parseRelations(relations),
                randomDate
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

