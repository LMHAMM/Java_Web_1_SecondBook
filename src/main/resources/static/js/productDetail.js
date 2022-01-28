$(function(){
    //给type绑定点击事件
    $(".type").click(function () {
        var index = $(".type").index(this);
        var obj = $(".type").eq(index);
        $(".type").removeClass("checked");
        obj.addClass("checked");
    });
    //给color绑定点击事件
    $(".color").click(function () {
        var index = $(".color").index(this);
        var obj = $(".color").eq(index);
        $(".color").removeClass("checked");
        obj.addClass("checked");
    });
});

//书籍细节页面的增加数量
function increase() {
    var value = $("#quantity").val();
    var stock = $("#stock").text();
    value++;
    if(value > stock){
        value = stock;
    }
    $("#quantity").val(value);
}

//书籍细节页面的减少数量
function reduce() {
    var value = $("#quantity").val();
    value--;
    if(value == 0){
        value = 1;
    }
    $("#quantity").val(value);
}

//添加购物车
function addCart(bookid,price){
    let stockStr = $("#stock").text();
    let stock = parseInt(stockStr);
    if(stock == 0){
        alert("该书库存不足，请等待补货。");
        return false;
    }
    let quantity = $("#quantity").val();
    window.location.href="/cart/add/"+bookid+"/"+price+"/"+quantity;
}