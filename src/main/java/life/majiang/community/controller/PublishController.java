package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    private User user;

    //get会显示页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    //post会发起请求
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(isNull(title)){
            model.addAttribute("error","标题不能为空");
        }
        if(isNull(description)){
            model.addAttribute("error","问题描述不能为空");
        }
        if(isNull(tag)){
            model.addAttribute("error","标签不能为空");
        }
        Question question = new Question();
        question.setTitle(title).setDescription(description).setTag(tag).setCreator(user.getId()).setGmtCreate(System.currentTimeMillis()).setGmtModified(System.currentTimeMillis());
        questionMapper.create(question);
        return "redirect:/";
    }

    public boolean isNull(String string ){
        return string==null || string.split("")==null;
    }
}
