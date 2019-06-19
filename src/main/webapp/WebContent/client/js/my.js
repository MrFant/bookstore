window.onload = function() {
    var interval;
    var second = document.getElementById("second");
    var svalue = second.innerHTML;
    interval=setInterval(function () {
    	svalue=svalue-1;
    	if (svalue===0){
			window.clearInterval(interval);
            location.href=window.location.protocol+'//'+window.location.host+"/WebContent/client/index.jsp";
            return;
		}
		second.innerHTML=svalue;

		
    }, 1000)

};


function changeSecond() {

	svalue = svalue - 1;
	if (svalue === 0) {
		window.clearInterval(interval);
		// 结束setInterval()
		// 下列两行代码用于获取项目名，例如：bookstore
		// var pathName = window.location.pathname.substring(1);
		// var webName = pathName === '' ? '' : pathName.substring(0, pathName.indexOf('/'));
		// // 拼接访问路径名，例如：http://localhost:8080/bookstore/index.jsp
		// location.href = window.location.protocol + '//' + window.location.host + '/'+ webName + '/index.jsp';
		return;
	}
	second.innerHTML = svalue;
}
