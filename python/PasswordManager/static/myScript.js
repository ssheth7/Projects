function myFunction(x){
        var pwd = document.getElementById("pwd");
        alert(pwd.nodeValue);
        var reg = /^[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{6,}$/g
        if(reg.test(pwd) || pwd == "test")
            x.style.background = "green";
        else x.style.background = "red";
}  