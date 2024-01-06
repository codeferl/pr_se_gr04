package com.team04.adrmanager.runner;

import com.team04.adrmanager.model.AdrEntities;
import com.team04.adrmanager.repo.AdrRepository;
import com.team04.adrmanager.repo.ArtifactRepository;
import com.team04.adrmanager.repo.CommitRepository;
import com.team04.adrmanager.repo.RelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(1)
@Component
public class PrintDataRunner implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(PrintDataRunner.class);

    @Autowired
    private AdrRepository adrRepo;

    private String toStringShort(String str){
        int x = 15;
        if(str.length() < x){
            return str;
        }else{
            return str.substring(0,x-3) + "...";
        }
    }

    @Override
    public void run(String... args) throws Exception {
        List<AdrEntities> v = adrRepo.findAll();

        log.info("adr has size: " + v.size());
        v.forEach(x->
        {
            log.info("ID: " + x.getId() + "; TITLE: " + toStringShort(x.getTitel()) + "; CONTEXT: " + toStringShort(x.getContext())
            + "; DECISION: " + toStringShort(x.getDecisions()) + "; STATUS: " + toStringShort(x.getStatus()));

            log.info("Artifacts:");
            x.getArtifacts().forEach(y -> log.info("\tID: " + y.getId() + "; TEXT: " + toStringShort(y.getText())));

            log.info("Commits:");
            x.getCommit().forEach(y -> log.info("\tID: " + y.getId() + "; HASH: " + toStringShort(y.getCommitHash())));

            log.info("Relations:");
            x.getRelations().forEach(y -> log.info("\tID: " + y.getId() + "; TXT: " + toStringShort(y.getTxt())));

            log.info("--------------");
        });
    }
}
