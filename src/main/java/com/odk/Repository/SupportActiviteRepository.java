
package com.odk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odk.Entity.SupportActivite;

@Repository
public interface SupportActiviteRepository extends JpaRepository<SupportActivite, Long>{

    
}