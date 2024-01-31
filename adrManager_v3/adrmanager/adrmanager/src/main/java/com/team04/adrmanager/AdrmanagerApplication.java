package com.team04.adrmanager;

import com.team04.adrmanager.model.*;
import com.team04.adrmanager.repo.AdrRepository;
import com.team04.adrmanager.repo.ArtifactRepository;
import com.team04.adrmanager.repo.CommitRepository;
import com.team04.adrmanager.repo.RelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class AdrmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdrmanagerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}