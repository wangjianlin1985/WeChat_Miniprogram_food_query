package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Dish;

public interface DishMapper {
	/*添加菜谱信息*/
	public void addDish(Dish dish) throws Exception;

	/*按照查询条件分页查询菜谱记录*/
	public ArrayList<Dish> queryDish(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有菜谱记录*/
	public ArrayList<Dish> queryDishList(@Param("where") String where) throws Exception;
	
	/*按照查询条件查询最新菜谱记录*/
	public ArrayList<Dish> queryZxDish(@Param("where") String where) throws Exception;
 
	/*按照查询条件的菜谱记录数*/
	public int queryDishCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条菜谱记录*/
	public Dish getDish(int dishId) throws Exception;

	/*更新菜谱记录*/
	public void updateDish(Dish dish) throws Exception;

	/*删除菜谱记录*/
	public void deleteDish(int dishId) throws Exception;

}
