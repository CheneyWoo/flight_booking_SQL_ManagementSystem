window.onload=function(){
    checkCookie();
    $.ajax({
        type: "get",
        url: 'common_login_servlet?source=sign-in',
        dataType: 'json',
        success: function (json) {
            if(json.login_status == 1)
            {
                DisplayIcon(1);
            }
        }
    });
}

function signin() {
    // form.action = "common_login_servlet";
    //form.submit();
    var psd = $("#password").val();
    var phone = $("#phone").val();
    var src = "sign-in";
    $.ajax({
        type: "post",
        url: 'common_login_servlet',
        data: {source:src, userPsd: psd, userID: phone},
        dataType: 'json',
        success: function (data) {
            //alert(JSON.stringify(data));
            var flag = data.flag;
            DisplayIcon(flag);
        },
        error: function (XMLHttpRequest, textStatus) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });
}

$("#checkbox").click(function(){
    if(this.checked) {
        if (($("#phone").val() == "") || ($("#password").val() == "")) {
            //alert("请输入账号和密码！");
        }else{
            var userID=$("#phone").val();
            var userPsd=$("#password").val();
            setCookie("userID",userID,30);
            setCookie("password",userPsd,30);
            setCookie("remember",true,30);
        }
    }else{
        clearCookie("username");
        clearCookie("password");
        clearCookie("remember");
    }
});
var clearCookie=function(name){
    setCookie(name, "", -1);
};
var getCookie=function(name){
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
};
var setCookie=function(name,value,expiredays){
    var exdate=new Date();
    exdate.setDate(exdate.getDate()+expiredays);
    document.cookie=name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
};
var checkCookie=function(){
    var userID=getCookie('userID');
    var userPsd=getCookie('password');
    var remember=getCookie('remember');
    if(remember){
        $('#uniform-checkbox span').addClass("checked");
        if (userID!=null && userID!="")
        {$("#phone").val(userID);}else{}
        if (userPsd!=null && userPsd!="")
        {$("#password").val(userPsd);}else{}
    }
};
