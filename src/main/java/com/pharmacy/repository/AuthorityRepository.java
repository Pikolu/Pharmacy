package com.pharmacy.repository;

import com.pharmacy.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alexander on 28.12.2015.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
