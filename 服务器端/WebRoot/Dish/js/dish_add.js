$(function () {
	$("#dish_dishClassObj_classId").combobox({
	    url:'DishClass/listAll',
	    valueField: "classId",
	    textField: "className",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#dish_dishClassObj_classId").combobox("getData"); 
            if (data.length > 0) {
                $("#dish_dishClassObj_classId").combobox("select", data[0].classId);
            }
        }
	});
	$("#dish_dishName").validatebox({
		required : true, 
		missingMessage : '请输入菜谱名称',
	});

	$("#dish_price").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入参考价格',
		invalidMessage : '参考价格输入不对',
	});

	$("#dish_dishDesc").validatebox({
		required : true, 
		missingMessage : '请输入菜谱介绍',
	});

	$("#dish_viewNum").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入浏览量',
		invalidMessage : '浏览量输入不对',
	});

	$("#dish_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#dishAddButton").click(function () {
		//验证表单 
		if(!$("#dishAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#dishAddForm").form({
			    url:"Dish/add",
			    onSubmit: function(){
					if($("#dishAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#dishAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#dishAddForm").submit();
		}
	});

	//单击清空按钮
	$("#dishClearButton").click(function () { 
		$("#dishAddForm").form("clear"); 
	});
});
