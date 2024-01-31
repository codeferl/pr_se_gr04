package com.team04.adrmanager.repo;

import com.team04.adrmanager.model.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
     Optional<Artifact> findByText(String text);
}
