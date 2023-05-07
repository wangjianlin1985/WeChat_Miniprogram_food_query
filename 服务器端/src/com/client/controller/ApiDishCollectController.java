package com.client.controller;

import java.text.SimpleDateFormat;
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
import com.chengxusheji.po.DishCollect;
import com.chengxusheji.po.Dish;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.DishCollectService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/dishCollect") 
public class ApiDishCollectController {
	@Resource DishCollectService dishCollectService;
	@Resource AuthService authService;

	@InitBinder("dishObj")
	public void initBinderdishObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("dishCollect")
	public void initBinderDishCollect(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishCollect.");
	}

	/*客户端ajax方式添加菜谱收藏信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(DishCollect dishCollect, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		dishCollect.setUserObj(userObj);
		
		if(dishCollectService.queryDishCollect(dishCollect.getDishObj(), userObj, "").size() > 0) {
			return JsonResultBuilder.ok("你已经收藏过了");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dishCollect.setCollectTime(sdf.format(new java.util.Date()));
		
        dishCollectService.addDishCollect(dishCollect); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新菜谱收藏信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated DishCollect dishCollect, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        dishCollectService.updateDishCollect(dishCollect);  //更新记录到数据库
        return JsonResultBuilder.ok(dishCollectService.getDishCollect(dishCollect.getCollectId()));
	}

	/*ajax方式显示获取菜谱收藏详细信息*/
	@RequestMapping(value="/get/{collectId}",method=RequestMethod.POST)
	public JsonResult getDishCollect(@PathVariable int collectId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键collectId获取DishCollect对象*/
        DishCollect dishCollect = dishCollectService.getDishCollect(collectId); 
        return JsonResultBuilder.ok(dishCollect);
	}

	/*ajax方式删除菜谱收藏记录*/
	@RequestMapping(value="/delete/{collectId}",method=RequestMethod.POST)
	public JsonResult deleteDishCollect(@PathVariable int collectId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			dishCollectService.deleteDishCollect(collectId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询菜谱收藏信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String collectTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (collectTime == null) collectTime = "";
		if(rows != 0)dishCollectService.setRows(rows);
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		userObj = new UserInfo();
		userObj.setUser_name(userName);
		
		List<DishCollect> dishCollectList = dishCollectService.queryDishCollect(dishObj, userObj, collectTime, page);
	    /*计算总的页数和总的记录数*/
	    dishCollectService.queryTotalPageAndRecordNumber(dishObj, userObj, collectTime);
	    /*获取到总的页码数目*/
	    int totalPage = dishCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishCollectService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", dishCollectList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有菜谱收藏
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<DishCollect> dishCollectList = dishCollectService.queryAllDishCollect(); 
		return JsonResultBuilder.ok(dishCollectList);
	}
}

