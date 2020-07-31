const REG_LOW_STRENGTH_PASS = /^[0-9]*$/;
const REG_USER = /^.{4,15}$/;
const REG_PASS = /^.{6,12}$/;
const REG_EMAIL = /^([a-z]|[A-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+((\.[a-zA-Z]{2,4}){1,2})$/;

// 全为数字，不可
const REG_PASS_WEAK = /^[a-zA-Z]{6,12}$/;
// “^\d+$” 全为字母 “^[a-zA-Z]+$” 全为数字
// (?! "xxx")除了全为字母 和全为数字的 字母、数字都有的6-12位字符串
const REG_PASS_MIDDLE = /^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,12}$/;
const REG_PASS_STRONG = /^ .{6,12}$/

const QUANJIAOKONGGE = "&emsp;";
const OK_TAG = QUANJIAOKONGGE
		+ "<span class='glyphicon glyphicon-ok-sign' ></span>" + QUANJIAOKONGGE;
const FALSE_TAG = QUANJIAOKONGGE
		+ "<span class='glyphicon glyphicon-remove-sign'> </span>"
		+ QUANJIAOKONGGE;

var form = document.getElementById("form-register");
form.onsubmit = function() {

	return checkSubmit();
}

function checkuser() {
	var user = document.getElementById("username");
	var span = document.getElementById("usernameSpan");

	if (user.value === "")
		span.innerHTML = FALSE_TAG + "请填写";
	else if (!REG_USER.test(user.value))
		span.innerHTML = FALSE_TAG + "4-15位字母、数字或下划线";
	else
		span.innerHTML = " " + OK_TAG;

	return REG_USER.test(user.value);
}

function checkpassword1() {
	var user = document.getElementById("password1");
	var span = document.getElementById("password1Span");

	var strength = document.getElementById("passStrength");
	var result = false;
	// 空
	if (user.value === "") {

		strength.style = "background-color:red;width:1%;";
		span.innerHTML = FALSE_TAG + "请填写";
		// 不合格
	}
	else if (!REG_PASS.test(user.value)) {
		span.innerHTML = FALSE_TAG + "6-12位字符";
		strength.style = "background-color:red;width:1%;";

		// 数字
	} else if (REG_LOW_STRENGTH_PASS.test(user.value)) {
		span.innerHTML = FALSE_TAG + "弱密码，请尝试加入字母或特殊字符";
		strength.style = "background-color:red;width:33%;";
		// 字母
	} else if (REG_PASS_WEAK.test(user.value)) {
		span.innerHTML = FALSE_TAG + "弱密码，请尝试加入数字或特殊字符";
		strength.style = "background-color:red;width:33%;";
		// 数字+字母
	} else if (REG_PASS_MIDDLE.test(user.value)) {
		span.innerHTML = OK_TAG + "请尝试加入特殊字符";
		strength.style = "background-color:orange;width:66%;";
		result = true;
	} else if (REG_PASS.test(user.value)) {
		span.innerHTML = " " + OK_TAG;
		strength.style = "background-color:green;width:100%;";
		result = true;
	}

	// checkpassword2();
	return result;
}
function checkpassword2() {
	var user1 = document.getElementById("password1");
	var user2 = document.getElementById("password2");
	var span = document.getElementById("password2Span");

	if (user2.value === "")
		span.innerHTML = FALSE_TAG + "请填写";
	else if (user1.value !== user2.value)
		span.innerHTML = FALSE_TAG + "不一致";
	else
		span.innerHTML = " " + OK_TAG;

	return user1.value === user2.value;
}

function checkmail() {
	var user = document.getElementById("mail");
	var span = document.getElementById("mailSpan");

	if (user.value === "")
		span.innerHTML = FALSE_TAG + "请填写";
	else if (!REG_EMAIL.test(user.value))
		span.innerHTML = FALSE_TAG + "请填写正确的邮箱格式";
	else
		span.innerHTML = " " + OK_TAG;

	return REG_EMAIL.test(user.value);
}

function checkValidCode(){
	
	var user = document.getElementById("validCode");
	var span = document.getElementById("validCodeSpan");

	if (user.value === "")
		span.innerHTML = FALSE_TAG + "请填写";

	else
		span.innerHTML = " " + OK_TAG;

	return user.value != "";
}

function checkSubmit() {
	checkuser();
	checkmail();
	checkpassword1();
	// checkpassword2();
	 checkValidCode();

	//checkFirstName();
	//checkLastName();
	return (checkuser() && checkmail() && checkpassword1() && checkValidCode()

	// checkpassword2() &&
	// checkCode()
	);
}

/**
 * 
 * var int1 = 0; var int2 = 0; function createCode() { var codeSpan =
 * document.getElementById("codeSpan");
 * 
 * int1 = Math.floor(Math.random() * 100); int2 = Math.floor(Math.random() *
 * 100);
 * 
 * var str = " " + int1 + " + " + int2 + " = ? 点击重新生成验证码"; codeSpan.innerText =
 * str; }
 * 
 * 
 * 
 * function checkFirstName() { var user = document.getElementById("firstName");
 * var span = document.getElementById("firstNameSpan"); // nonempty if
 * (user.value === "") span.innerHTML = FALSE_TAG + " 请填写"; else span.innerHTML = " " +
 * OK_TAG;
 * 
 * return (user.value !== ""); }
 * 
 * function checkLastName() { var user = document.getElementById("lastName");
 * var span = document.getElementById("lastNameSpan"); // nonempty if
 * (user.value === "") span.innerHTML = FALSE_TAG + " 请填写"; else span.innerHTML = " " +
 * OK_TAG;
 * 
 * return (user.value !== ""); } function checkCode() { var code =
 * document.getElementById("code"); var result = int1 + int2; return code.value ==
 * result; }
 * 
 * var form1 = document.getElementById("register-form");
 * 
 * form1.onsubmit = function () { console.log("checkuser is "+checkuser());
 * console.log("checkmail is "+checkmail()); console.log("checkpassword1 is
 * "+checkpassword1()); //console.log("checkpassword2 is "+checkpassword2());
 * //console.log("checkCode is "+checkCode());
 * 
 * return checkSubmit(); };
 * 
 * 
 * 
 * 
 */
