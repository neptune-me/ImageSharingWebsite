
$(function () {
  setOption();
  console.log("func");

  $("#select_country").change(function () {
    setOption();
  });
});

function setOption() {
  var select_city = $("#select_city");
  console.log("get selected city");
  console.log(select_city.val());
  if ($("#select_country").val() == "") {
    select_city.empty();
    var option = "<option value='' id='default_city'>---</option>";
    console.log("country is empty change/");
    select_city.append(option);
  } else {
    console.log("setOption! before post");
    
    $.post(
      "./cityQuery.do",
      { countryCodeISO: $("#select_country").val(), status: "all" },
      function (data) {
        console.log("setOption! inside post data");
        console.log(data);
        console.log(data.length);

        select_city.empty();
        var length = data.length;
        for (var i = 0; i < length; i++) {
          var option =
            "<option value='" +
            data[i].cityCode +
            "'>" +
            data[i].city +
            "</option>";
          select_city.append(option);
          console.log(data[i].cityCode + ": " + data[i].city);
        }
      },
      "json"
    );
    console.log("setOption! after post");
  }
}

const OK_TAG = "<span class='glyphicon glyphicon-ok-sign' ></span>";
const FALSE_TAG = " <span class='glyphicon glyphicon-remove-sign'> </span>";

let form2 = document.getElementById("upload_form");

form2.onsubmit = function () {
  console.log("checkPath() is " + checkPath());
  console.log("checkTitle() is " + checkTitle());
  console.log("checkDescription() is " + checkDescription());
  console.log("checkCountry() is " + checkCountry());
  console.log("checkCity() is " + checkCity());
  console.log("checkContent() is " + checkContent());

  return checkSubmit2();
};

function checkSubmit2() {
  checkPath();
  checkTitle();
  checkDescription();
  checkCountry();
  checkCity();
  checkContent();

  return (
    checkPath() &&
    checkTitle() &&
    checkDescription() &&
    checkCountry() &&
    checkCity() &&
    checkContent()
  );
}

function checkPath() {
  let path = document.getElementById("upload_file");
  let span = document.getElementById("pathSpan");
  if (path.getAttribute("disabled") != "") {
    if (path.value === "") span.innerHTML = FALSE_TAG + " 请填写";
    else span.innerHTML = " " + OK_TAG;
    return !(path.value === "");
  }

  return true;
}

function checkTitle() {
  let title = document.getElementById("title");
  let span = document.getElementById("titleSpan");
  if (title.value === "") span.innerHTML = FALSE_TAG + " 请填写";
  else span.innerHTML = " " + OK_TAG;

  return !(title.value === "");
}

function checkDescription() {
  let description = document.getElementById("description");
  let span = document.getElementById("descriptionSpan");
  if (description.value === "") span.innerHTML = FALSE_TAG + " 请填写";
  else span.innerHTML = " " + OK_TAG;

  return !(description.value === "");
}

function checkCountry() {
  let select_country = document.getElementById("select_country");
  let span = document.getElementById("countrySpan");
  if (select_country.value === "") span.innerHTML = FALSE_TAG + " 请选择";
  else span.innerHTML = " " + OK_TAG;

  return !(select_country.value === "");
}

function checkCity() {
  let select_city = document.getElementById("select_city");
  let span = document.getElementById("citySpan");
  if (select_city.value === "") span.innerHTML = FALSE_TAG + " 请选择";
  else span.innerHTML = " " + OK_TAG;

  return !(select_city.value === "");
}

function checkContent() {
  let select_content = document.getElementById("select_content");
  let span = document.getElementById("contentSpan");
  if (select_content.value === "") span.innerHTML = FALSE_TAG + " 请选择";
  else span.innerHTML = " " + OK_TAG;

  return !(select_content.value === "");
}

var input = document.getElementById("upload_file");
var image_div = document.getElementById("image_div");
if (typeof FileReader === "undefined") {
  image_div.innerHTML = "抱歉，你的浏览器不支持 FileReader";
  input.setAttribute("disabled", "disabled");
}

input.onchange = function () {
  var reader = new FileReader();
  reader.readAsDataURL(input.files[0]);
  reader.onload = function () {
    image_div.innerHTML =
      '<img src="' + reader.result + '" class="image-show">';
  };
};

$(function () {
  $("[data-toggle='tooltip']").tooltip();
});
