$(function () {
	$.ajax({
		url : "Dish/" + $("#dish_dishId_edit").val() + "/update",
		type : "get",
		data : {
			//dishId : $("#dish_dishId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (dish, response, status) {
			$.messager.progress("close");
			if (dish) { 
				$("#dish_dishId_edit").val(dish.dishId);
				$("#dish_dishId_edit").validatebox({
					required : true,
					missingMessage : "请输入菜谱id",
					editable: false
				});
				$("#dish_dishClassObj_classId_edit").combobox({
					url:"DishClass/listAll",
					valueField:"classId",
					textField:"className",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#dish_dishClassObj_classId_edit").combobox("select", dish.dishClassObjPri);
						//var data = $("#dish_dishClassObj_classId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#dish_dishClassObj_classId_edit").combobox("select", data[0].classId);
						//}
					}
				});
				$("#dish_dishName_edit").val(dish.dishName);
				$("#dish_dishName_edit").validatebox({
					required : true,
					missingMessage : "请输入菜谱名称",
				});
				$("#dish_dishPhoto").val(dish.dishPhoto);
				$("#dish_dishPhotoImg").attr("src", dish.dishPhoto);
				$("#dish_price_edit").val(dish.price);
				$("#dish_price_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入参考价格",
					invalidMessage : "参考价格输入不对",
				});
				$("#dish_dishDesc_edit").val(dish.dishDesc);
				$("#dish_dishDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入菜谱介绍",
				});
				$("#dish_viewNum_edit").val(dish.viewNum);
				$("#dish_viewNum_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入浏览量",
					invalidMessage : "浏览量输入不对",
				});
				$("#dish_addTime_edit").datetimebox({
					value: dish.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#dishModifyButton").click(function(){ 
		if ($("#dishEditForm").form("validate")) {
			$("#dishEditForm").form({
			    url:"Dish/" +  $("#dish_dishId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#dishEditForm").form("validate"))  {
	                	$.messager.progress({
							text : "正在提交数据中...",
						});
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#dishEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
