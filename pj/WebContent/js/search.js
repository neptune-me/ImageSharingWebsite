var ASC = document.getElementById("ASC");
var DESC = document.getElementById("DESC");
var for_ASC = document.getElementById("for_ASC");
var for_DESC = document.getElementById("for_DESC");

var DateUpload = document.getElementById("DateUpload");
var Likes = document.getElementById("Likes");
var for_DateUpload = document.getElementById("for_DateUpload");
var for_Likes = document.getElementById("for_Likes");

var mess = document.getElementById("mess");
var pics = document.getElementById("pics");
var for_mess = document.getElementById("for_mess");
var for_pics = document.getElementById("for_pics");

window.onload = function() {
	checkBind(ASC, DESC, for_ASC, for_DESC);
	bindButtonsAndRadio(ASC, DESC, for_ASC, for_DESC);

	checkBind(DateUpload, Likes, for_DateUpload, for_Likes);
	bindButtonsAndRadio(DateUpload, Likes, for_DateUpload, for_Likes);

	checkBind(mess, pics, for_mess, for_pics);
	bindButtonsAndRadio(mess, pics, for_mess, for_pics);
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

function checkBind(element1, element2, for_e1, for_e2) {
	if (element1.checked) {
		console.log("ASC checked");
		for_e1.setAttribute("class", "btn btn-default active");
		for_e2.setAttribute("class", "btn btn-default");
	} else {
		console.log("DESC checked");
		for_e1.setAttribute("class", "btn btn-default");
		for_e2.setAttribute("class", "btn btn-default active");
	}
}


$(function () {
	$("[data-toggle='tooltip']").tooltip();
  });

  