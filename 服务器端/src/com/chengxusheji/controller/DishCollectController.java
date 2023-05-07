package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.DishCollectService;
import com.chengxusheji.po.DishCollect;
import com.chengxusheji.service.DishService;
import com.chengxusheji.po.Dish;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//DishCollect管理控制层
@Controller
@RequestMapping("/DishCollect")
public class DishCollectController extends BaseController {

    /*业务层对象*/
    @Resource DishCollectService dishCollectService;

    @Resource DishService dishService;
    @Resource UserInfoService userInfoService;
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
	/*跳转到添加DishCollect视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DishCollect());
		/*查询所有的Dish信息*/
		List<Dish> dishList = dishService.queryAllDish();
		request.setAttribute("dishList", dishList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "DishCollect_add";
	}

	/*客户端ajax方式提交添加菜谱收藏信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DishCollect dishCollect, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        dishCollectService.addDishCollect(dishCollect);
        message = "菜谱收藏添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String collectTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (collectTime == null) collectTime = "";
		if(rows != 0)dishCollectService.setRows(rows);
		List<DishCollect> dishCollectList = dishCollectService.queryDishCollect(dishObj, userObj, collectTime, page);
	    /*计算总的页数和总的记录数*/
	    dishCollectService.queryTotalPageAndRecordNumber(dishObj, userObj, collectTime);
	    /*获取到总的页码数目*/
	    int totalPage = dishCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishCollectService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DishCollect dishCollect:dishCollectList) {
			JSONObject jsonDishCollect = dishCollect.getJsonObject();
			jsonArray.put(jsonDishCollect);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DishCollect> dishCollectList = dishCollectService.queryAllDishCollect();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DishCollect dishCollect:dishCollectList) {
			JSONObject jsonDishCollect = new JSONObject();
			jsonDishCollect.accumulate("collectId", dishCollect.getCollectId());
			jsonArray.put(jsonDishCollect);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String collectTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (collectTime == null) collectTime = "";
		List<DishCollect> dishCollectList = dishCollectService.queryDishCollect(dishObj, userObj, collectTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    dishCollectService.queryTotalPageAndRecordNumber(dishObj, userObj, collectTime);
	    /*获取到总的页码数目*/
	    int totalPage = dishCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishCollectService.getRecordNumber();
	    request.setAttribute("dishCollectList",  dishCollectList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("dishObj", dishObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("collectTime", collectTime);
	    List<Dish> dishList = dishService.queryAllDish();
	    request.setAttribute("dishList", dishList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "DishCollect/dishCollect_frontquery_result"; 
	}

     /*前台查询DishCollect信息*/
	@RequestMapping(value="/{collectId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer collectId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键collectId获取DishCollect对象*/
        DishCollect dishCollect = dishCollectService.getDishCollect(collectId);

        List<Dish> dishList = dishService.queryAllDish();
        request.setAttribute("dishList", dishList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("dishCollect",  dishCollect);
        return "DishCollect/dishCollect_frontshow";
	}

	/*ajax方式显示菜谱收藏修改jsp视图页*/
	@RequestMapping(value="/{collectId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer collectId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键collectId获取DishCollect对象*/
        DishCollect dishCollect = dishCollectService.getDishCollect(collectId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDishCollect = dishCollect.getJsonObject();
		out.println(jsonDishCollect.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新菜谱收藏信息*/
	@RequestMapping(value = "/{collectId}/update", method = RequestMethod.POST)
	public void update(@Validated DishCollect dishCollect, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			dishCollectService.updateDishCollect(dishCollect);
			message = "菜谱收藏更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "菜谱收藏更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除菜谱收藏信息*/
	@RequestMapping(value="/{collectId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer collectId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dishCollectService.deleteDishCollect(collectId);
	            request.setAttribute("message", "菜谱收藏删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "菜谱收藏删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条菜谱收藏记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String collectIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dishCollectService.deleteDishCollects(collectIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出菜谱收藏信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String collectTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(collectTime == null) collectTime = "";
        List<DishCollect> dishCollectList = dishCollectService.queryDishCollect(dishObj,userObj,collectTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DishCollect信息记录"; 
        String[] headers = { "收藏id","收藏菜谱","收藏用户","收藏时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dishCollectList.size();i++) {
        	DishCollect dishCollect = dishCollectList.get(i); 
        	dataset.add(new String[]{dishCollect.getCollectId() + "",dishCollect.getDishObj().getDishName(),dishCollect.getUserObj().getName(),dishCollect.getCollectTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DishCollect.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
