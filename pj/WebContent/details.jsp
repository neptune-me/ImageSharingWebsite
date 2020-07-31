<%@page import="java.util.*"%>
<%@page import="com.Model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unsplash | 详情</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/index_css.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<%@include file="include/header.jsp"%>
<%@include file="include/float_button.jsp"%>

<style>
.comment-div {
  height: auto;
}

.hr-div {
  margin: 5px 0;
  background-color: rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 2px;
  overflow: hidden;
}



#theImg{
  position: relative;

}

</style>


</head>


<body>
<% 
request.setCharacterEncoding("UTF-8");

if(request.getAttribute("image") == null){
	response.sendRedirect("./index.nav");
	
}else{
%>


	<% Image theImage = (Image)request.getAttribute("image"); %>

	<section id="pageup">
	    <div class="panel panel-default">
	        <div class="panel-heading">
	            图片详情
	        </div>
	        <div class="panel-body">
	            <div class="row">
	            	<div class="col-xs-12">
	                    <h3 id="work-name"><%= theImage.getTitle() %></h3>
	                    <small>by</small> <small id="author-name"><%= theImage.getUser().getUsername() %></small>
	                    <span class="footer-time">Upload Time: <%= theImage.getDateUploadStr() %></span>
	                    <hr>
	                    
                	</div>
	            	
	            	<div class="col-xs-12 col-sm-6">
	                    <div class="details-img-container" id="theImgDiv">
		                    <img id="theImg" src="travel-images/large/<%= theImage.getPath() %>">
	                    	<hr>
	                    </div>
	                    <div class="col-xs-12"><br></div>
	                    
	                    
	                    
	                    <div class="col-xs-1"><br></div>
	                    <div class="btGroup col-xs-10" id="btGroup">
		                    <div class="row">
			                    <div class="col-xs-12 col-sm-3"> </div>
			                    <div class="col-xs-12 col-sm-3">
			                    	<button class="btn btn-default full-length " id="shang"><span class="glyphicon glyphicon-chevron-up"></span></button>
		    					</div>	
			                    <div class="col-xs-12 col-sm-3"> </div>
			                    <div class="col-xs-12 col-sm-3"> </div>
		                    </div>
		                    <div class="col-xs-12"><br></div>
		                    <div class="row">
			                    <div class="col-xs-12 col-sm-3">
		    					    <button class="btn btn-default full-length  " id="zuo"><span class="glyphicon glyphicon-chevron-left"></span></button>
		    					</div>	
			                    <div class="col-xs-12 col-sm-3">
		    						<button class=" btn btn-default full-length" id="xia"><span class="glyphicon glyphicon-chevron-down"></span></button>
		    					</div>	
			                    <div class="col-xs-12 col-sm-3">
		        					<button class="btn btn-default full-length" id="you"><span class="glyphicon glyphicon-chevron-right"></span></button>
		    					</div>	
			                    <div class="col-xs-12 col-sm-3">
		        					<button  class="btn btn-default full-length" id="initbt"><span class="glyphicon glyphicon-refresh"></span></button>
			                    </div>
		                    </div>

		                    <div class="col-xs-12 col-sm-6"> 
		                    </div>
		                    <div class="col-xs-12 col-sm-6"> 
		                    </div>
        				</div>
        				
        				<script>
        					
        				
        					var bt_control = document.getElementById("control-bt");
        					var bt_group = document.getElementById("btGroup");
        					var bt_control2 = document.getElementById("control-bt2");
        					
        					bt_group.style.display="none";
        					bt_control2.style.display="none";
        					
        					bt_control.onclick = function(){
        						bt_group.style.display = "block";
        						bt_control.style.display = "none";
        						bt_contro2.style.display = "inline";
        					}
        					bt_control2.onclick = function(){
        						bt_group.style.display = "none";
        						bt_control.style.display = "inline-block";
        						bt_contro2.style.display = "none";
        						
        					}
        					
        				</script>
        				
        				
                	</div>
                	
                	<div class="col-xs-12 col-sm-6">
	                    <div class="panel panel-default">
	                        <div class="panel-body">
	                            <h4 class="colored">City: </h4><%= theImage.getiGeo().getCity() %><br>
	                            <h4 class="colored">Country: </h4><%= theImage.getiGeo().getCountry() %><br>
	                            <h4 class="colored">Continent: </h4><%= theImage.getiGeo().getContinent() %><br>
	                            <h4 class="colored">Content: </h4><%= theImage.getContent() %><br>
	                            <h4 class="colored">Description: </h4><%= theImage.getDescription() %><br>
	                        </div>
	                    </div>
	                    
	                    <% if(session.getAttribute("localuser") != null){ %>
	                    <!-- like按鈕 -->
	                    <div class="details ">
	                    	<input type="text" id="localuser_uid" value="${ localuser.get("uid") }" disabled style="display:none">
                    		
	                    	<% 
	                    	Boolean isLike = (Boolean)request.getAttribute("isLike");
	                    	String btLikeClass = "btLike full-length white-button btn btn-default";
	                    	if(isLike != null){
	                    		btLikeClass =  "btLike full-length red-button btn btn-default";
		                    	 }
	                    	%>
					        <!-- a href="./like.do?id=<!%= theImage.getImageID() %>"  -->
					        <button  class="<%= btLikeClass %>" value="${ param.id }">
					        	<span class="glyphicon glyphicon-thumbs-up"> </span>  
					        	<span class="like-number-span"><%= theImage.getLikes() %></span>
					        </button>
						    <!--  >/a-->
						    <hr>
						    <%
						    Map<String,String> localuser = (Map<String,String>)session.getAttribute("localuser");
						    int uid = Integer.parseInt(localuser.get("uid"));
						    if(theImage.getUser().getUserID() == uid){%>
						     <a href="./upload.pers?iid=<%= theImage.getImageID() %>" class="full-length btn btn-default">
	                			<span class="glyphicon glyphicon-edit"></span> <span> Modify</span>
			               	 </a>
			               	 <% } %>
						</div>

						<!-- end like -->
				        
						<% } %>
					</div> <!-- end 右側欄目 -->
					
                  
                    <script>
                    
                    //get img\img-div
    var imgDiv = document.getElementById("theImgDiv");
    var img = document.getElementById("theImg");
    var ini_w = img.width;
    var ini_h = img.height;
                    
                    
    ///alert('width:' + img.width + ',height:' + img.height);
    //alert('top:' + img.style.top + ',left:' + img.style.left);
                    
 	//100%-130 160 190%                    
 	var beishu = 100;
 	var gap_beishu = 30;
 	
 	 //get 4 操控bt
    var shang = document.getElementById("shang");
    var xia = document.getElementById("xia");
    var zuo = document.getElementById("zuo");
    var you = document.getElementById("you");
    
    var initbt = document.getElementById("initbt");
    
    var ini_t = 0;
    var left = 0;
    var gap = 20;
    
    
    
    
    
    // 5 bts
        initbt.onclick = function () {
        initImg();
    }
    
    shang.onclick = function () {
        ini_t += gap;
        ini_t = notNeg(ini_t);
        doMove(left, ini_t, img);
    }

    xia.onclick = function () {
        ini_t -= gap;
        ini_t = notNeg(ini_t);
        doMove(left, ini_t, img);

    }


    zuo.onclick = function () {
        left -= gap;
        left = notNeg(left);
        doMove(left, ini_t, img);

    }

    you.onclick = function () {
        left += gap;
        left = notNeg(left);
        doMove(left, ini_t, img);

    }


    function notNeg(num) {
        if (num >= 0)
            num = 0;

        console.log("=========not nev 不能>0=========")
        return num;
    }

    function doMove() {
        console.log("----------------do move--------------");
        console.log("left: " + left);
        console.log("top:" + ini_t);
        console.log("倍数：" + beishu);
        //ini_w  left=小w-大w = ini_w-(ini_w*beishu)~0
        //ini_h
        if (left < ini_w - (ini_w * (beishu/100)))
            left = left + gap;
        if (ini_t < ini_h - (ini_h * (beishu/100)))
            ini_t = ini_t + gap;

        img.style.left = left + "px";
        img.style.top = ini_t + "px";
        console.log("new left:"+left);
        console.log("new top"+ini_t);
        console.log("----------------finish move--------------");

    }


    function initImg() {
        left = 0;
        ini_t = 0;
        beishu = 100;

        doMove(left, ini_t, img);
        img.style.width = beishu + "%";
    }
    doEnlarge(imgDiv, img);

    
    
    // enlarge
    doEnlarge(imgDiv, img);

    imgDiv.style.height = ini_h + "px";

    function doEnlarge(theDiv, img) {
        theDiv.onmouseover = function (ev) {

            //双击放大
            theDiv.ondblclick = function (ev) {

                var w = parseInt(img.style.width);
                var h = parseInt(img.style.height);
                console.log("ondblclick");
                console.log(w);
                
                var beginBeishu = 100+gap_beishu+"";

                switch (w + "") {
                    case beginBeishu:
                        beishu = 160;
                        break;
                    case "160":
                        beishu = 190;
                        break;
                    case "190":
                        beishu = 100;
                        break;
                    case "100":
                        beishu = 130;
                        break;
                    default:
                        beishu = 130;
                        break;
                }
                img.style.width = beishu + "%";

                console.log('width:' + img.width + ',height:' + img.height + ";倍数" + beishu);

            };
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
                    
                    </script>
                    
                    
                    
                    
                    
                    <!-- 评论区 -->
                   
<%
if(session.getAttribute("localuser") != null) {
	int id= Integer.parseInt(request.getParameter("id"));
	String order_way = (request.getParameter("order-way") == null)?"DateUpload":request.getParameter("order-way");
	String o_way_1 = order_way.equals("DateUpload")?"checked":"";
	String o_way_2 = order_way.equals("Likes")?"checked":"";
		
%>                    
 					<h2 class="block-center"><br></h2>
                    <input type="text" id="image_iid" value="<%= request.getParameter("id") %>" disabled style="display:none">
                    <div class="col-xs-12"><hr></div>
                    
                    <div class="col-xs-2"></div>
                    
                    <div class="col-xs-8">
            	        <div class="col-xs-10">
	            	        <input class="form-control full-length form-default" style="height:auto" id="sendCommentInput"
    	            	    placeholder="Please Input Your Comment Here">
                   		</div>
                    	<div class="col-xs-2">
        	            	<button class="btn btn-danger btSend" >發送</button>
                    	</div>
                    
                    	<hr>
            	        <div class="hr-div"><hr></div>
            	        <!-- 按钮组进行显示的格式化 -->
            	        <div class="btGroup col-xs-12">
            	        
            	        <!-- 排序方式 -->
							<div class="col-xs-2"> 
							</div>

                             <div class="col-xs-4">
                               	<a href="./details.nav?id=<%= request.getParameter("id") %>&order-way=DateUpload" type="button" class="btn btn-default btn-xs" id="for_DateUpload">
								<span class="glyphicon glyphicon-time"></span>
                                		<input class="" id="DateUpload" name="order-way" type="radio"  <%= o_way_1 %> value="DateUpload" style="display:none">
                               		按时间排序
								</a>
                               	<a href="./details.nav?id=<%= request.getParameter("id") %>&order-way=Likes" type="button" class="btn btn-default btn-xs" id="for_Likes">
                               		<span class="glyphicon glyphicon-heart"></span>
                                		<input class="" id="Likes" name="order-way"  type="radio" <%= o_way_2 %> value="Likes" style="display:none">
                               		按热度排序
								</a>
                             </div>
                              
                             <div class="col-xs-4">
                               	<div type="button" class="btn btn-default btn-xs" id="for_ASC" disabled>
								<span class="glyphicon glyphicon-sort-by-attributes"></span>
                                		<input class="" id="ASC" name="order" type="radio" <%  %> value="ASC" style="display:none">
                                		由低到高
								</div>
                           		<div type="button" class="btn btn-default btn-xs" id="for_DESC" disabled>
                           			<span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
                               		<input class="" id="DESC" name="order"  type="radio" <%  %> value="DESC" style="display:none">
                                		由高到低
								</div>
                            </div>
                            
            	        	<div class="col-xs-2"> 
							</div>
            	        
            	        
            	        </div>
            	        
            	        <div class="hr-div"><hr></div>

<!-- ajax立马上传显示我发送的评论 -->
<div id="myComment"></div>
<%
if(request.getAttribute("comments") != null){
System.out.println("comments is not null!=======================");
ArrayList<Comment> comments= (ArrayList<Comment>)request.getAttribute("comments");
ArrayList<Integer> myCommentsAgreement = (ArrayList<Integer>)request.getAttribute("myCommentsAgreement");
	System.out.print("===>list agreem:\n"+myCommentsAgreement);

//分頁？
for(Comment comment:comments){
	boolean isAgreed = myCommentsAgreement.contains(comment.getCommentID());
	System.out.print("===>isAgreed:"+isAgreed);
%>

<div class="AComment"><!-- 一个div 表示一个评论+点赞按钮 -->
            	        <div class="col-xs-10">
	            	        <div class="comment-div full-length ">
	            	        	<p>
	            	        	<a href="./userpics.pers?uid=<%= comment.getUserID() %>">
	            	        	<%= comment.getUser().getUsername() %> : 
	            	        	</a>
	            	        	<%= comment.getComment() %>
	            	        	
	            	        	</p>
	            	        	<p class="footer-time">
	            	        	<%= comment.getDateCommentStr() %>
	            	        	</p>
	            	        </div>
	            	        <div class="hr-div"><hr></div>
	            	        
                   		</div>
                    	<div class="col-xs-2" id="div-agree-for-<%= comment.getCommentID() %>">
                    		<% if(!isAgreed){ %>
        	            	<button class="btn btn-default btAgreement" onclick=doAgreement(<%= comment.getCommentID() %>) >
	            	        	<span class="glyphicon glyphicon-thumbs-up"> </span> 
	            	        	<%= comment.getAgreements() %>
        	            	</button>
                    		<% } else { %>
        	            	<button class="btn  red-button btAgreement" onclick=doAgreement(<%= comment.getCommentID() %>) >
	            	        	<span class="glyphicon glyphicon-thumbs-up"> </span> 
	            	        	<%= comment.getAgreements() %>
        	            	</button>
                    		
                    		<% } %>
                    	</div>
</div>
<% }
if(comments.size() == 0){
	
 %>
							<div class="block-center col-xs-10">
								<p> 欢迎添加评论！ </p>
							</div>
<% }
}%>
                    </div>
               	<script type="text/javascript">
               	
               	
               	

               	var DateUpload = document.getElementById("DateUpload");
               	var Likes = document.getElementById("Likes");
               	var for_DateUpload = document.getElementById("for_DateUpload");
               	var for_Likes = document.getElementById("for_Likes");


               	window.onload = function() {
               		

               		checkBind(DateUpload, Likes, for_DateUpload, for_Likes);
               		bindButtonsAndRadio(DateUpload, Likes, for_DateUpload, for_Likes);



               	}
               	
           		function checkBind(element1, element2, for_e1, for_e2) {
           			if (element1.checked) {
           				console.log("ASC checked");
           				for_e1.setAttribute("class", "btn btn-default active btn-xs");
           				for_e2.setAttribute("class", "btn btn-default btn-xs");
           			} else {
           				console.log("DESC checked");
           				for_e1.setAttribute("class", "btn btn-default btn-xs");
           				for_e2.setAttribute("class", "btn btn-default active btn-xs");
           			}
           		}
           		
           		

           		function bindButtonsAndRadio(element1, element2, for_e1, for_e2) {

           			for_e1.onclick = function() {
           				element1.checked = true;
           				element2.checked = false;
           				checkBind(element1, element2, for_e1, for_e2);
           			};

           			for_e2.onclick = function() {
           				element1.checked = false;
           				element2.checked = true;
           				checkBind(element1, element2, for_e1, for_e2);
           			};
           		}

               	
               	
               	
               	
               	
               	
               	
               	
               	
               	
               	
               	
               	
               	
              
              //发送评论

               	$(function () {
               	  var sendCommentInput = document.getElementById("sendCommentInput");
               	  var uid = $("#localuser_uid").val(); //ok
               	  var iid = $("#image_iid").val(); //ok

               	  console.log("uid: " + uid);
               	  console.log("iid: " + iid);
               	  //console.log("comment: " + sendCommentInput.value);

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


               	</script>

                    <div class="col-xs-2"></div>
                    
                    
                    
<% } %>                    
	            </div><!-- end row -->
	        </div><!-- end pane body -->
	    </div>
	</section>




					
				
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				
			</div>
			<div class="modal-body">
				确定要删除照片吗？
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				 <button class="white-button btn btn-default" name="deleteUpload" data-toggle="modal" data-target="#myModal">
					                		<span class="glyphicon glyphicon-trash"></span> <span> Delete</span>
					                	</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
					
		
<% } %>
<script src="./js/details_like.js"></script>
<script src="./js/details_comment.js"></script>
</body>
</html>