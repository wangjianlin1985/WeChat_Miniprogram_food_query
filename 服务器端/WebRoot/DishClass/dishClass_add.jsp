<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dishClass.css" />
<div id="dishClassAddDiv">
	<form id="dishClassAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dishClass_className" name="dishClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">类别描述:</span>
			<span class="inputControl">
				<textarea id="dishClass_classDesc" name="dishClass.classDesc" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="dishClassAddButton" class="easyui-linkbutton">添加</a>
			<a id="dishClassClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/DishClass/js/dishClass_add.js"></script> 
