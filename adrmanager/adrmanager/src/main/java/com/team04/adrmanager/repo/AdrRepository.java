package com.team04.adrmanager.repo;


import com.team04.adrmanager.model.AdrEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdrRepository extends JpaRepository<AdrEntities, Long> {

}
