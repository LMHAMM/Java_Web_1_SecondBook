$(function(){
    //计算总价
    let array = $(".qprice");
    let totalCost = 0;
    for(var i = 0;i < array.length;i++){
        var val = parseInt($(".qprice").eq(i).html());
        totalCost += val;
    }
    $("#totalprice").html(totalCost);
    //订单界面settlement2
    $("#settlement2_totalCost").val(totalCost);
});



function addQuantity(obj){
    let index = $(".car_btn_2").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    let stock = parseInt($(".productStock").eq(index).val());
    if(quantity == stock){
        alert("库存不足！");
        return false;
    }
    quantity++;
    let price = parseFloat($(".productPrice").eq(index).val());
    let cost = quantity * price;


    let id = parseInt($(".id").eq(index).val());
    //改变数量后，随之改变数据库
    //基于jQeury的Ajax
    $.ajax({
        url:"/cart/updatebook/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data){
            if(data == "success"){
                $(".qprice").eq(index).text(cost);
                $(".car_ipt").eq(index).val(quantity);

                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html());
                    totalCost += val;
                }
                $("#totalprice").html(totalCost);
            }
        }
    })




}

function subQuantity(obj){
    let index = $(".car_btn_1").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    if(quantity == 1){
        alert("至少选择一件商品！");
        return false;
    }
    quantity--;
    let price = parseFloat($(".productPrice").eq(index).val());
    let cost = quantity * price
    let id = parseInt($(".id").eq(index).val());

    $.ajax({
        url:"/cart/updatebook/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data){
            if(data == "success"){
                $(".qprice").eq(index).text(cost);
                $(".car_ipt").eq(index).val(quantity);

                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html());
                    totalCost += val;
                }
                $("#totalprice").html(totalCost);
            }
        }
    })


}

//移出购物车
function removeCart(obj){
    let index = $(".delete").index(obj);
    let id = parseInt($(".id").eq(index).val());
    if(confirm("是否要从购物车中删除该书籍？")){
        window.location.href = "/cart/deleteById/"+id;
    }
}
//判断购物车是否为空
//商品总价为0的话不跳转
function settlement2(){
    var totalCost = $("#totalprice").text();
    if(totalCost == "0"){
        alert("购物车中无商品，请先去选购");
        return false;
    }
    window.location.href="/cart/settlement2";
}