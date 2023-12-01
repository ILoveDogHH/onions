package onions.service;

import onions.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginDao loginDao;

    public String getLogin(){
        return loginDao.getLogin();
    }

}
