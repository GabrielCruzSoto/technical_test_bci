package com.gcs.technicaltestbci.repositories;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gcs.technicaltestbci.entities.LoginAuditEntity;

public interface ILoginAuditRepository extends JpaRepository<LoginAuditEntity, UUID> {

      @Query("SELECT la " +
                  "FROM LoginAuditEntity la " +
                  "WHERE la.user.id = :userId")
      Page<LoginAuditEntity> findByUserId(@Param("userId") String userId, Pageable pageable);

      @Query("SELECT la FROM LoginAuditEntity la " +
                  "WHERE la.user.id = :userId " +
                  "ORDER BY la.createdOn DESC")
      Page<LoginAuditEntity> getLastLoginByUserId(@Param("userId") UUID userId, Pageable pageable);

      @Query("SELECT la " +
                  "FROM LoginAuditEntity la " +
                  "WHERE la.user.email = :userEmail")
      Page<LoginAuditEntity> findByUserEmail(@Param("userEmail")String email, Pageable pageable);

}
