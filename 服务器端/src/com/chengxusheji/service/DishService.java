package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DishClass;
import com.chengxusheji.po.Dish;

import com.chengxusheji.mapper.DishMapper;
@Service
public class DishService {

	@Resource DishMapper dishMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加菜谱记录*/
    public void addDish(Dish dish) throws Exception {
    	dishMapper.addDish(dish);
    }

    /*按照查询条件分页查询菜谱记录*/
    public ArrayList<Dish> queryDish(String addTime,DishClass dishClassObj,String dishName,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
    	if(null != dishClassObj && dishClassObj.getClassId()!= null && dishClassObj.getClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dishMapper.queryDish(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Dish> queryDish(String addTime,DishClass dishClassObj,String dishName) throws Exception  { 
     	String where = "where 1=1";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
    	if(null != dishClassObj && dishClassObj.getClassId()!= null && dishClassObj.getClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
    	return dishMapper.queryDishList(where);
    }
    
    
    /*按照查询条件查询所有记录*/
    public ArrayList<Dish> queryZxDish() throws Exception  { 
     	String where = "where 1=1"; 
    	return dishMapper.queryZxDish(where);
    }
    

    /*查询所有菜谱记录*/
    public ArrayList<Dish> queryAllDish()  throws Exception {
        return dishMapper.queryDishList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String addTime,DishClass dishClassObj,String dishName) throws Exception {
     	String where = "where 1=1";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
    	if(null != dishClassObj && dishClassObj.getClassId()!= null && dishClassObj.getClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
        recordNumber = dishMapper.queryDishCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取菜谱记录*/
    public Dish getDish(int dishId) throws Exception  {
        Dish dish = dishMapper.getDish(dishId);
        return dish;
    }

    /*更新菜谱记录*/
    public void updateDish(Dish dish) throws Exception {
        dishMapper.updateDish(dish);
    }

    /*删除一条菜谱记录*/
    public void deleteDish (int dishId) throws Exception {
        dishMapper.deleteDish(dishId);
    }

    /*删除多条菜谱信息*/
    public int deleteDishs (String dishIds) throws Exception {
    	String _dishIds[] = dishIds.split(",");
    	for(String _dishId: _dishIds) {
    		dishMapper.deleteDish(Integer.parseInt(_dishId));
    	}
    	return _dishIds.length;
    }
}
