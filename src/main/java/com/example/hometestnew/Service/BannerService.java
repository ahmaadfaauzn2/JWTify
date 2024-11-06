package com.example.hometestnew.Service;




import com.example.hometestnew.models.Banner;
import com.example.hometestnew.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public List<Banner> getAllBanners(){
        return bannerRepository.findAll();
    }

}
