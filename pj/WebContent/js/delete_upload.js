$(function () {
    console.log("func");
  
    //
    $(".btDelete").click(function () {
      //
      console.log("click");
      doDeleteUpload(this);
    });
  });
  
  //delete myfavourite need iid and uid
  
  function doDeleteUpload(obj) {
    //<button class="btDelete white-button btn btn-default"  value="<%= image.getImageID() %>">
    var iid = obj.value;
    var uid = $("#localuser_uid").val(); //ok
    //<input type="text" id="localuser_uid" value="${ localuser.get("uid") }" disabled style="display:none">
  
    console.log("before post--> iid click: " + iid);
    console.log("           --> uid: " + uid);
  
    $.post(
      "./delete.do",
      { iid: iid, uid: uid },
      function (data) {
        //should return 1/-1 for success/unsuccess
        console.log(data);
        if (data == 1) {
          //1成功 /0没有这么个照片 /-1删除失败
          
          //<article class="left"  id="article_<%= image.getImageID() %>">
          var article = document.getElementById("article_" + iid);
          article.style.display = "none";
          console.log("删除成功");
        } else {
          console.log("删除失败");
        }

        //console.log(data2);
    } //不是json
    );
    console.log("after post");
  }
  
  $(function () {
    $("[data-toggle='tooltip']").tooltip();
  });
  