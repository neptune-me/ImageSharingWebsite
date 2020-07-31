$(function () {
  console.log("func");

  //<button  class="btLike full-length red btn btn-default"
  //id="<%= id %>" value="${ param.id }">
  $(".btLike").click(function () {
    //
    console.log("click");
    doLike(this);
  });
});

//delete myfavourite need iid and uid

function doLike(obj) {
  //<button  class="btLike full-length red btn btn-default"
  //id="<%= id %>" value="${ param.id }"
  var iid = obj.value;
  var uid = $("#localuser_uid").val(); //ok
  //<input type="text" id="localuser_uid" value="${ localuser.get("uid") }" disabled style="display:none">

  console.log("before post--> iid click: " + iid);
  console.log("           --> uid: " + uid);

  $.post(
    "./like.do",
    { iid: iid, uid: uid },
    function (data) {
      //should return [1/-1,6]

      console.log(data);
      if (data[0] == -1) {
        console.log("unlike");
        changeColor(false, obj);
      } else {
        console.log("like");
        changeColor(true, obj);
      }

      changeLikeNumber(data[1]);
    },
    "json"
  );
  console.log("after post");
}

function changeColor(isLike, btLike) {
  if (isLike) btLike.class = "btLike full-length red-button btn btn-default";
  else btLike.class = "btLike full-length white-button btn btn-default";

  if (isLike) btLike.id = "red-bgc";
  else btLike.id = "white-bgc";
}

function changeLikeNumber(number) {
  var spanForLikes = document.getElementsByClassName("like-number-span")[0];
  spanForLikes.innerHTML = number;
}

$(function () {
  $("[data-toggle='tooltip']").tooltip();
});

///
//发送评论

$(function () {
  var sendCommentInput = document.getElementById("sendCommentInput");
  var uid = $("#localuser_uid").val(); //ok
  var iid = $("#image_iid").val(); //ok

  console.log("uid: " + uid);
  console.log("iid: " + iid);
  console.log("comment: " + sendCommentInput.value);

  var btSend = document.getElementsByClassName("btSend")[0];
  btSend.onclick = function () {
    console.log("btSend click");
    console.log("comment: " + sendCommentInput.value);
    doSendComment(sendCommentInput.value, uid, iid);

    //$(".")
  };
});

//点赞

function doAgreement(commentID) {
  var uid = $("#localuser_uid").val(); //ok
  var iid = $("#image_iid").val(); //ok
  console.log("doAgreement----uid: " + uid);
  console.log("doAgreement----iid: " + iid);
  console.log("doAgreement----cid: " + commentID);

  $.post("./doAgree.do", { uid: uid, commentID: commentID }, function (data) {
    //[0] -1 0/失败 1/成功
    //[1] commentid 的 like数
    //[2] 0/ unlike 1/is like
    var doState = data[0];
    var agreeNumber = data[1];
    var isLike = data[2];

    console.log("data is: ");
    console.log(data);
    var div_comment_agree = document.getElementById(
      "div-agree-for-" + commentID
    );

    if (doState == 1) {
      //成功
      if (isLike == 1) {
        // like
        div_comment_agree.innerHTML =
          '<button class="btn red-button btAgreement" onclick=doAgreement("' +
          commentID +
          '") ><span class="glyphicon glyphicon-thumbs-up"> </span> ' +
          agreeNumber +
          "</button>";


      }else if(isLike == 0){
        div_comment_agree.innerHTML =
          '<button class="btn btn-default btAgreement" onclick=doAgreement("' +
          commentID +
          '") ><span class="glyphicon glyphicon-thumbs-up"> </span> ' +
          agreeNumber +
          "</button>";
      }
      console.log("操作成功！");

    } else {
      // failed
      console.log(doState);
      console.log("操作失败！");
    }
  },"json"
  );
}

function doSendComment(comment, uid, iid) {
  console.log("inside do send comment");
  $.post(
    "./sendComment.do",
    { comment: comment, uid: uid, iid: iid },
    function (data) {
      //data[0] key
      //data[1] str html
      console.log("------------->data:");
      console.log(data);
      if (data[0] > 0) {
        //成功发送
        var myCommentDiv = document.getElementById("myComment");
        myCommentDiv.innerHTML = data[1];
      }
      console.log("finish post");
    },
    "json"
  );
}
