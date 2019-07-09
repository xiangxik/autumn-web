package com.autumn.modules.core.service;

import com.autumn.modules.core.entity.Corp;
import com.autumn.modules.core.repo.CorpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorpService {

    @Autowired
    private CorpRepository corpRepository;

    @Autowired
    private UserService userService;

    public List<Corp> findAll() {
        return corpRepository.findAll();
    }

    public Corp save(Corp corp) {
        return corpRepository.save(corp);
    }

    public Corp createNewCorp(String corpName, String corpCode, String adminUsername, String adminPassword) {
        Corp corp = new Corp();
        corp.setName(corpName);
        corp.setCode(corpCode);
        Corp createdCorp = corpRepository.save(corp);

        userService.createAdmin(adminUsername, adminPassword, corpCode);


        return createdCorp;
    }

    public boolean existCorp() {
        return corpRepository.count() > 0;
    }

}
