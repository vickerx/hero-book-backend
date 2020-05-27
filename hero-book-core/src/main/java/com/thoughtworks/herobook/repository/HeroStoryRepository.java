package com.thoughtworks.herobook.repository;

import com.thoughtworks.herobook.entity.HeroStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroStoryRepository extends JpaRepository<HeroStory, Long> {
    @Query("from HeroStory hs order by hs.updatedTime desc")
    Page<HeroStory> findAllOrderByUpdatedTimeDesc(Pageable pageable);
}
