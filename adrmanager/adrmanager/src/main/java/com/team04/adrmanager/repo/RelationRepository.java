package com.team04.adrmanager.repo;

import com.team04.adrmanager.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    Optional<Relation> findByTxt(String txt);
}
