package com.team04.adrmanager.runner;

import com.team04.adrmanager.AdrmanagerApplication;
import com.team04.adrmanager.model.AdrEntities;
import com.team04.adrmanager.repo.AdrRepository;
import com.team04.adrmanager.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Order(0)
@Component
public class GetDataRunner implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(GetDataRunner.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AdrRepository adrRepo;

    @Autowired
    ParserService parser;

    //Link zum file über die github api repos/{username}/{repo}/contents/{path}?ref={branch}
    static String API_URL = "https://api.github.com/repos/flohuemer/graal/contents/wasm/docs/arch/adr-xxx.md?ref=adrs";
    static int MAX_FILES = 27;

    @Override
    public void run(String... args) throws Exception {

        //Drop db
        adrRepo.deleteAll();


        for (int i = 1; i <= MAX_FILES; i++) {
            String number = "" + i;
            while (number.length() < 3) number = "0" + number;

            String current_key = API_URL.replace("xxx", number);

            //Sometimes there is an api rate limit, just wait a few minutes and try again.
            AdrFile adrFile = restTemplate.getForObject(
                    current_key, AdrFile.class);

            String res = new String(adrFile.content.getBytes(), "UTF-8");
            res = res.replace("\n", "");
            res = new String(Base64.getDecoder().decode(res));

            AdrEntities adr = parser.parseAdr(res);
            log.info("" + i + "/" + MAX_FILES);
        }
    }
}

class AdrFile {
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
