/**
 * 加入购物车
 */
function buy(bookid){
	$.post("buy.action", {bookid:bookid}, function(data){
		if(data=="ok"){
			layer.msg("加入购物车成功");
			location.reload();
		}else if(data=="login"){
			location.href="login.action";
		}else{
			alert("请求失败!");
		}
	});
}