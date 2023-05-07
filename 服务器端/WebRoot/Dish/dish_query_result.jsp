<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dish.css" /> 

<div id="dish_manage"></div>
<div id="dish_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="dish_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="dish_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="dish_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="dish_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="dish_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="dishQueryForm" method="post">
			发布时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			菜谱类别：<input class="textbox" type="text" id="dishClassObj_classId_query" name="dishClassObj.classId" style="width: auto"/>
			菜谱名称：<input type="text" class="textbox" id="dishName" name="dishName" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="dish_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="dishEditDiv">
	<form id="dishEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">菜谱id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_dishId_edit" name="dish.dishId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">菜谱类别:</span>
			<span class="inputControl">
				<input class="textbox"  id="dish_dishClassObj_classId_edit" name="dish.dishClassObj.classId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">菜谱名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_dishName_edit" name="dish.dishName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">菜谱图片:</span>
			<span class="inputControl">
				<img id="dish_dishPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="dish_dishPhoto" name="dish.dishPhoto"/>
				<input id="dishPhotoFile" name="dishPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">参考价格:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_price_edit" name="dish.price" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">菜谱介绍:</span>
			<span class="inputControl">
				<textarea id="dish_dishDesc_edit" name="dish.dishDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">浏览量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_viewNum_edit" name="dish.viewNum" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_addTime_edit" name="dish.addTime" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Dish/js/dish_manage.js"></script> 
