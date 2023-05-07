package com.client.controller;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chengxusheji.po.DishClass;
import com.chengxusheji.service.DishClassService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/dishClass") 
public class ApiDishClassController {
	@Resource DishClassService dishClassService;
	@Resource AuthService authService;

	@InitBinder("dishClass")
	public void initBinderDishClass(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishClass.");
	}

	/*客户端ajax方式添加菜谱类别信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated DishClass dishClass, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        dishClassService.addDishClass(dishClass); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新菜谱类别信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated DishClass dishClass, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        dishClassService.updateDishClass(dishClass);  //更新记录到数据库
        return JsonResultBuilder.ok(dishClassService.getDishClass(dishClass.getClassId()));
	}

	/*ajax方式显示获取菜谱类别详细信息*/
	@RequestMapping(value="/get/{classId}",method=RequestMethod.POST)
	public JsonResult getDishClass(@PathVariable int classId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键classId获取DishClass对象*/
        DishClass dishClass = dishClassService.getDishClass(classId); 
        return JsonResultBuilder.ok(dishClass);
	}

	/*ajax方式删除菜谱类别记录*/
	@RequestMapping(value="/delete/{classId}",method=RequestMethod.POST)
	public JsonResult deleteDishClass(@PathVariable int classId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			dishClassService.deleteDishClass(classId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询菜谱类别信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)dishClassService.setRows(rows);
		List<DishClass> dishClassList = dishClassService.queryDishClass(page);
	    /*计算总的页数和总的记录数*/
	    dishClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = dishClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishClassService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", dishClassList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有菜谱类别
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<DishClass> dishClassList = dishClassService.queryAllDishClass(); 
		return JsonResultBuilder.ok(dishClassList);
	}
}

