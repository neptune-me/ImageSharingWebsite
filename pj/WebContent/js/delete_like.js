$(function () {
  console.log("func");

  //
  $(".btDeleteLike").click(function () {
    //
    console.log("click");
    doDelete(this);
  });


  
$(".btChangeState").click(function(){
  console.log("btnChangeState clicked!");
  doChangeState(this);
});
});

function doChangeState(obj){
  var uid = obj.value;
  console.log("uid:");
  console.log(uid);
  console.log(obj);

  $.post(
    "./changeState.do",
    {uid: uid}, 
    function(data){
      console.log("data: ");
      console.log(data);

      var btn = document.getElementsByClassName("btChangeState")[0];
      if(data == 1){//当前状态：开启
        btn.innerHTML= "<span class='glyphicon glyphicon-eye-open'></span> 关闭收藏显示";
      }else{
        btn.innerHTML= "<span class='glyphicon glyphicon-eye-close'></span> 开启收藏显示";

      }
    }
  );

  console.log("after post");
}



//delete myfavourite need iid and uid

function doDelete(obj) {
  //<button  class="btDeleteLike red btn btn-default" name="delete" value="<%= image.getImageID() %>"></button>
  var iid = obj.value;
  var uid = $("#localuser_uid").val(); //ok
  //<input type="text" id="localuser_uid" value="${ localuser.get("uid") }" disabled style="display:none">

  console.log("before post--> iid click: " + iid);
  console.log("           --> uid: " + uid);

  $.post(
    "./like.do",
    { iid: iid, uid: uid },
    function (data) {
      //should return 1/-1
      //1--like
      //-1--unlike
      console.log(data);
      if (data[0] == -1) {
        //?
        //<article class="left"  id="article_<%= image.getImageID() %>">
        var article = document.getElementById("article_" + iid);
        article.style.display = "none";
        console.log("删除成功");
      } else {
        console.log("删除失败");
      }
    } ,"json"
  );
  console.log("after post");
}

$(function () {
  $("[data-toggle='tooltip']").tooltip();
});
