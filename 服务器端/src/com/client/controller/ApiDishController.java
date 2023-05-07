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
import com.chengxusheji.po.Dish;
import com.chengxusheji.po.DishClass;
import com.chengxusheji.service.DishService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/dish") 
public class ApiDishController {
	@Resource DishService dishService;
	@Resource AuthService authService;

	@InitBinder("dishClassObj")
	public void initBinderdishClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishClassObj.");
	}
	@InitBinder("dish")
	public void initBinderDish(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dish.");
	}

	/*客户端ajax方式添加菜谱信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Dish dish, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        dishService.addDish(dish); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新菜谱信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Dish dish, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        dishService.updateDish(dish);  //更新记录到数据库
        return JsonResultBuilder.ok(dishService.getDish(dish.getDishId()));
	}

	/*ajax方式显示获取菜谱详细信息*/
	@RequestMapping(value="/get/{dishId}",method=RequestMethod.POST)
	public JsonResult getDish(@PathVariable int dishId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键dishId获取Dish对象*/
        Dish dish = dishService.getDish(dishId); 
        dish.setViewNum(dish.getViewNum() + 1);
        dishService.updateDish(dish);
        return JsonResultBuilder.ok(dish);
	}

	/*ajax方式删除菜谱记录*/
	@RequestMapping(value="/delete/{dishId}",method=RequestMethod.POST)
	public JsonResult deleteDish(@PathVariable int dishId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			dishService.deleteDish(dishId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询菜谱信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(String addTime,@ModelAttribute("dishClassObj") DishClass dishClassObj,String dishName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (addTime == null) addTime = "";
		if (dishName == null) dishName = "";
		if(rows != 0)dishService.setRows(rows);
		List<Dish> dishList = dishService.queryDish(addTime, dishClassObj, dishName, page);
	    /*计算总的页数和总的记录数*/
	    dishService.queryTotalPageAndRecordNumber(addTime, dishClassObj, dishName);
	    /*获取到总的页码数目*/
	    int totalPage = dishService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", dishList);
	    return JsonResultBuilder.ok(resultMap);
	}

	
	//客户端查询最新菜谱信息
	@RequestMapping(value="/zxList",method=RequestMethod.POST)
	public JsonResult zxList(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
		List<Dish> dishList = dishService.queryZxDish();
	     
	    HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
	    resultMap.put("list", dishList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端ajax获取所有菜谱
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Dish> dishList = dishService.queryAllDish(); 
		return JsonResultBuilder.ok(dishList);
	}
}

