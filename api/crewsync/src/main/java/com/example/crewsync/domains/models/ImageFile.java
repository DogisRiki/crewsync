package com.example.crewsync.domains.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * プロフィール編集画面の画像ファイルを保持するモデルクラスです
 */
@Data
@Slf4j
public class ImageFile {

    private String fileName;

    /**
     * Base64でエンコードしたファイルデータを文字列で返します
     *
     * @return エンコード済み文字列
     */
    public String encodedString() {

        if (!StringUtils.hasText(fileName)) {
            fileName = "crewsync/src/main/resources/static/img/anonymous.png";
        }

        File imageFile = new File(fileName);
        StringBuffer base64String = new StringBuffer();

        try {
            // 画像ファイルのタイプを取得する(jpgやpngなど)
            String contentType = Files.probeContentType(imageFile.toPath());
            // 画像ファイルからバイト配列を作成する
            byte[] fileData = Files.readAllBytes(imageFile.toPath());
            base64String.append("data:");
            base64String.append(contentType);
            base64String.append(";base64,");
            base64String.append(Base64.getEncoder().encodeToString(fileData));
        } catch (IOException e) {
            log.warn("ファイルのエンコードに失敗しました: {}", e.getMessage());
            return "";
        }
        return base64String.toString();
    }
}
