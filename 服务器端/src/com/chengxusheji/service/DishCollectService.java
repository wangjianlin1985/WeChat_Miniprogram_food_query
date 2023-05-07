package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Dish;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.DishCollect;

import com.chengxusheji.mapper.DishCollectMapper;
@Service
public class DishCollectService {

	@Resource DishCollectMapper dishCollectMapper;
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

    /*添加菜谱收藏记录*/
    public void addDishCollect(DishCollect dishCollect) throws Exception {
    	dishCollectMapper.addDishCollect(dishCollect);
    }

    /*按照查询条件分页查询菜谱收藏记录*/
    public ArrayList<DishCollect> queryDishCollect(Dish dishObj,UserInfo userObj,String collectTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != dishObj && dishObj.getDishId()!= null && dishObj.getDishId()!= 0)  where += " and t_dishCollect.dishObj=" + dishObj.getDishId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_dishCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectTime.equals("")) where = where + " and t_dishCollect.collectTime like '%" + collectTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dishCollectMapper.queryDishCollect(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DishCollect> queryDishCollect(Dish dishObj,UserInfo userObj,String collectTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != dishObj && dishObj.getDishId()!= null && dishObj.getDishId()!= 0)  where += " and t_dishCollect.dishObj=" + dishObj.getDishId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_dishCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectTime.equals("")) where = where + " and t_dishCollect.collectTime like '%" + collectTime + "%'";
    	return dishCollectMapper.queryDishCollectList(where);
    }

    /*查询所有菜谱收藏记录*/
    public ArrayList<DishCollect> queryAllDishCollect()  throws Exception {
        return dishCollectMapper.queryDishCollectList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Dish dishObj,UserInfo userObj,String collectTime) throws Exception {
     	String where = "where 1=1";
    	if(null != dishObj && dishObj.getDishId()!= null && dishObj.getDishId()!= 0)  where += " and t_dishCollect.dishObj=" + dishObj.getDishId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_dishCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectTime.equals("")) where = where + " and t_dishCollect.collectTime like '%" + collectTime + "%'";
        recordNumber = dishCollectMapper.queryDishCollectCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取菜谱收藏记录*/
    public DishCollect getDishCollect(int collectId) throws Exception  {
        DishCollect dishCollect = dishCollectMapper.getDishCollect(collectId);
        return dishCollect;
    }

    /*更新菜谱收藏记录*/
    public void updateDishCollect(DishCollect dishCollect) throws Exception {
        dishCollectMapper.updateDishCollect(dishCollect);
    }

    /*删除一条菜谱收藏记录*/
    public void deleteDishCollect (int collectId) throws Exception {
        dishCollectMapper.deleteDishCollect(collectId);
    }

    /*删除多条菜谱收藏信息*/
    public int deleteDishCollects (String collectIds) throws Exception {
    	String _collectIds[] = collectIds.split(",");
    	for(String _collectId: _collectIds) {
    		dishCollectMapper.deleteDishCollect(Integer.parseInt(_collectId));
    	}
    	return _collectIds.length;
    }
}
