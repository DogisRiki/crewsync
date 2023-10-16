package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.constants.MessageKeys;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.services.UserRegisterService;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

import jakarta.validation.Valid;

@Controller
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    private final NotificationMessage notificationMessage;

    @Autowired
    public UserRegisterController(UserRegisterService userRegisterService, NotificationMessage notificationMessage) {
        this.userRegisterService = userRegisterService;
        this.notificationMessage = notificationMessage;
    }

    /**
     * ユーザー登録画面の初期表示処理を実行します
     *
     * @param email メールアドレス
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/manage/users/register")
    public String initUserRegister(@RequestParam(value = "id", required = false) Long id, Model model) {
        try {
            UserRegisterForm userRegisterForm = userRegisterService.initUserRegisterForm(id);
            model.addAttribute("userRegisterForm", userRegisterForm);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
        return RouteConstants.USER_REGISTER;
    }

    /**
     * ユーザー登録処理を実行します
     *
     * @param userRegisterForm ユーザー登録フォーム
     * @param bindingResult    バリデーション結果
     * @param userDetails      ユーザー認証情報
     * @param model            モデル
     * @return 遷移先
     */
    @PostMapping("/manage/users/register/submit")
    public String onRegisterUser(@Valid @ModelAttribute UserRegisterForm userRegisterForm, BindingResult bindingResult,
            @AuthenticationPrincipal LoginUserDetails userDetails, Model model) {

        LoginUser user = userDetails.getLoginUser();

        if (bindingResult.hasErrors()) {
            userRegisterService.restoreRegisterForm(userRegisterForm);
            model.addAttribute("userRegisterForm", userRegisterForm);
            return RouteConstants.USER_REGISTER;
        } else {
            try {
                userRegisterService.registerUser(userRegisterForm, user);
                model.addAttribute("userRegisterForm",
                        userRegisterService.initUserRegisterForm(userRegisterForm.getUser().getId()));
                model.addAttribute("notificationMessage", notificationMessage.builder()
                        .messageLevel(NotificationMessage.MESSAGE_LEVEL_SUCCESS)
                        .messageCode(MessageKeys.NOTIFY_SUCCESS)
                        .build());
            } catch (Exception e) {
                // サムネイルの再表示のため再度プロフィール画像をセットする
                ImageFile imageFile = new ImageFile();
                imageFile.setFileName("crewsync/src/main/resources/static/img/anonymous.png");
                userRegisterForm.setImageFile(imageFile);
                userRegisterService.restoreRegisterForm(userRegisterForm);
                model.addAttribute("userRegisterForm", userRegisterForm);
                model.addAttribute("notificationMessage", notificationMessage.builder()
                        .messageLevel(NotificationMessage.MESSAGE_LEVEL_ERROR)
                        .messageCode(MessageKeys.NOTIFY_ERROR)
                        .build());
            }
        }
        return RouteConstants.USER_REGISTER;
    }

}
