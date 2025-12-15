
package com.odk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odk.Entity.SupportActivite;
import com.odk.Enum.TypeSupport;

@Repository
public interface SupportActiviteRepository extends JpaRepository<SupportActivite, Long>{
  List<SupportActivite> findByType(TypeSupport type);
    
}