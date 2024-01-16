package onions.controller;

import com.alibaba.fastjson.JSONArray;
import onions.resultApi.ResultApi;
import onions.service.LoginService;
import onions.utils.RedissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Login {

    @Autowired
    LoginService loginService;

    @Autowired
    RedissionUtil redissionUtil;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginHtml(){
        return "Login";
    }


    @RequestMapping(value = "userLogin", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        boolean check = redissionUtil.mapKeyExists("user", username);
        if(!check){
            //直接进入到注册页面中
            return ResultApi.resultApi("账号不存在，请进行注册");
        }
        //验证下账号是否chengg
        String msg = redissionUtil.getMapValue("user", username);
        if(!msg.endsWith(password)){
            return ResultApi.resultApi("密码错误");
        }
        response.sendRedirect("/chat");
        return ResultApi.resultApi("登录成功");
    }

    @RequestMapping(value = "userRegister", method = RequestMethod.GET)
    public JSONArray register(@RequestParam String username, @RequestParam String password){
        boolean check = redissionUtil.mapKeyExists("user", username);
        if(check){
            //直接进入到注册页面中
            return ResultApi.resultApi("账号已注册");
        }
        redissionUtil.setMapValue("user", username, password);
        return ResultApi.resultApi("注册成功，请登录");
    }

    @RequestMapping(value = "chat", method = RequestMethod.GET)
    public String chat(){
        return "Chat";
    }



    




}
