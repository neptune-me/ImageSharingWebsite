$(function () {
  console.log("func");
  doPage(1);
});
//分两种情况，
	//一种，根据热度排序，可以拿到所有的images，用limit顺序进行显示
	//根据时间排序
	//jsonarray，内部为image对象
function doPage(page) {
  //<button class='active btPage' value='" + a + "'>" + a + "</button>
  if(page == undefined)
    page = 1;
  
  console.log("before post--> target click: " + page);
  console.log("before post--> : " + filter_condi+"; "+input_content
  +";order_way: "+order_way+"; order: "+order+"; show_way: "+show_way);


  $.post(
    "./page.do",
    //filter_condi[title] input_content order_way order show_way page
    {
      filter_condi: filter_condi,
      input_content: input_content,
      order_way: order_way,
      order: order,
      show_way: show_way,
      page: page//val
    },
    function (data) {
      //should return [String[],String]
      console.log(data);
      console.log(data.length);
      
      console.log("--> inside post : data[data.length-1]:");
      console.log(data[data.length-1]);

      var imagesDiv = $("#images");
      imagesDiv.empty();
      
      for (let a = 0; a < data.length-1; a++) {
        var articleDiv = document.createElement("div");
        articleDiv.innerHTML = data[a];
        imagesDiv.append(articleDiv);
      }

      var pageNumberDiv = document.getElementsByClassName("page_number")[0];
      pageNumberDiv.innerHTML = data[data.length-1];
    },
    "json"
  );
  console.log("after post");
}

