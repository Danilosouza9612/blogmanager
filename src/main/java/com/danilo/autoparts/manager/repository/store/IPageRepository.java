package com.danilo.autoparts.manager.repository.store;

import com.danilo.autoparts.manager.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPageRepository extends JpaRepository<Page, Long> {
}
