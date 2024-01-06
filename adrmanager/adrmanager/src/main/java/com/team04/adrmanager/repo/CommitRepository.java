package com.team04.adrmanager.repo;

import com.team04.adrmanager.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {
    Optional<Commit> findByCommitHash(String text);
}
