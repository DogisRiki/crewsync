const setAddress = function (pref, city) {
  $("#pref").val(pref);
  $("#city").val(city);
};

const callbackFunction = function (data) {
  if (data.results == null) {
    alert("該当の住所が見つかりませんでした。");
    return;
  }
  const result = data.results[0];
  const pref = result.address1;
  const city = result.address2 + result.address3;

  setAddress(pref, city);
};

$(function () {
  // サイドナビゲーション
  $(document).on("click", ".sidenav-toggler, .cover", function () {
    $(".sidenav").toggleClass("visible");
    $(".cover").toggleClass("visible");
  });

  // 画像ファイルのサムネイル取得
  $(document).on("change", "#photo-file", function () {
    const file = $(this).prop("files")[0];
    // 画像以外は処理を停止
    if (!file.type.match("image.*")) {
      // クリア
      $(this).val("");
      alert("画像ファイルを選択してください。");
      return;
    }

    // 画像表示
    const reader = new FileReader();
    reader.onload = function () {
      $("#thumbnail").attr("src", reader.result);
    };
    reader.readAsDataURL(file);
  });

  // 郵便番号検索
  $(document).on("click", "#search-address", function () {
    const baseURL = "https://zipcloud.ibsnet.co.jp/api/search";
    const zip = $("#zipcode").val();

    // 郵便番号未入力チェック
    if (zip == null || zip.length != 7) {
      alert("郵便番号は7桁で入力してください。");
    }

    // Ajaxでzipcloudへアクセス
    $.ajax({
      type: "GET",
      url: baseURL,
      dataType: "jsonp",
      jsonp: callbackFunction,
      data: {
        zipcode: zip,
        callback: "callbackFunction",
      },
    });
  });
});
