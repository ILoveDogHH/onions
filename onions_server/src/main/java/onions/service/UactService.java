package onions.service;

import onions.dao.UactDao;
import onions.entity.UAct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UactService {

    @Autowired
    UactDao dao;



    public List<UAct> getUactInfo(int uid){
        return dao.getUserByLimit(uid);
    }

}
