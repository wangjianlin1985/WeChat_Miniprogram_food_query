$(function () {
	$.ajax({
		url : "DishClass/" + $("#dishClass_classId_edit").val() + "/update",
		type : "get",
		data : {
			//classId : $("#dishClass_classId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (dishClass, response, status) {
			$.messager.progress("close");
			if (dishClass) { 
				$("#dishClass_classId_edit").val(dishClass.classId);
				$("#dishClass_classId_edit").validatebox({
					required : true,
					missingMessage : "请输入类别id",
					editable: false
				});
				$("#dishClass_className_edit").val(dishClass.className);
				$("#dishClass_className_edit").validatebox({
					required : true,
					missingMessage : "请输入类别名称",
				});
				$("#dishClass_classDesc_edit").val(dishClass.classDesc);
				$("#dishClass_classDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入类别描述",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#dishClassModifyButton").click(function(){ 
		if ($("#dishClassEditForm").form("validate")) {
			$("#dishClassEditForm").form({
			    url:"DishClass/" +  $("#dishClass_classId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#dishClassEditForm").form("validate"))  {
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
			$("#dishClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
