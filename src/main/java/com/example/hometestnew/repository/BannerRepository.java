package com.example.hometestnew.repository;

import com.example.hometestnew.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAll(); // This method is inherited from JpaRepository

//    @Query("SELECT b.bannerImage FROM Banner b")
//    List<String> findAllBannerImages();
}
