package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.constants.MessageKeys;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.services.ProfileEditService;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

import jakarta.validation.Valid;

@Controller
public class ProfileController {

    private final ProfileEditService profileEditService;

    private final NotificationMessage notificationMessage;

    @Autowired
    public ProfileController(ProfileEditService profileEditService, NotificationMessage notificationMessage) {
        this.profileEditService = profileEditService;
        this.notificationMessage = notificationMessage;
    }

    /**
     * プロフィール編集画面の初期表示処理を実行します
     *
     * @param userDetails ユーザー認証情報
     * @param model       モデル
     * @return 遷移先
     */
    @GetMapping("/profile")
    public String initProfile(@AuthenticationPrincipal LoginUserDetails userDetails, Model model) {
        LoginUser user = userDetails.getLoginUser();
        ProfileEditForm form = profileEditService.initPersonalInfo(user);
        model.addAttribute("profileEditForm", form);
        return RouteConstants.PROFILE;
    }

    /**
     * プロフィール編集処理を実行します
     *
     * @param profileEditForm プロフィール編集フォーム
     * @param result          バリデーション結果
     * @param model           モデル
     * @param userDetails     ユーザー認証情報
     * @return 遷移先
     */
    @PostMapping("/profile/edit")
    public String onEditProfile(@Valid @ModelAttribute ProfileEditForm profileEditForm, BindingResult result,
            Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {

        LoginUser user = userDetails.getLoginUser();

        if (result.hasErrors()) {
            return RouteConstants.PROFILE;
        }

        try {
            profileEditService.editProfile(user, profileEditForm);
            model.addAttribute("notificationMessage", notificationMessage.builder()
                    .messageLevel(NotificationMessage.MESSAGE_LEVEL_SUCCESS)
                    .messageCode(MessageKeys.NOTIFY_SUCCESS)
                    .build());
        } catch (Exception e) {
            // サムネイルの再表示のため再度プロフィール画像をセットする
            ImageFile imageFile = profileEditService.getProfileImg(user.getId());
            user.setImageFile(imageFile);
            model.addAttribute("notificationMessage", notificationMessage.builder()
                    .messageLevel(NotificationMessage.MESSAGE_LEVEL_ERROR)
                    .messageCode(MessageKeys.NOTIFY_ERROR)
                    .build());
        }
        return RouteConstants.PROFILE;
    }
}
