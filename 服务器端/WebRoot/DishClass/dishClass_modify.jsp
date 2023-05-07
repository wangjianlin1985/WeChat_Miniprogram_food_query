<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dishClass.css" />
<div id="dishClass_editDiv">
	<form id="dishClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类别id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dishClass_classId_edit" name="dishClass.classId" value="<%=request.getParameter("classId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dishClass_className_edit" name="dishClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">类别描述:</span>
			<span class="inputControl">
				<textarea id="dishClass_classDesc_edit" name="dishClass.classDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="dishClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/DishClass/js/dishClass_modify.js"></script> 
