package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.config.auth.LoginUser;
import org.example.springboot.config.auth.dto.SessionUser;
import org.example.springboot.service.PostsService;
import org.example.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { //기존 httpSession.getAttribute("user")로 가져오던 것을 개선. 이제 어떤 컨트롤러든 @LoginUser만으로 세션정보를 가져올 수 있음.
        model.addAttribute("posts", postsService.findAllDesc());
        //SessionUser user =(SessionUser) httpSession.getAttribute("user");
        //CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser를 저장하도록 했음. 따라서 로그인 성공시 httpSession.getAttribute에서 값을 가져올 수 있음.
        if (user != null) {
            model.addAttribute("userName", user.getName());
        } //세션에 저장된 값이 있을 때만 model에 userName으로 등록됨. 저장된 값이 없으면 model에 아무 값이 없어 로그인 버튼이 보임.
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
