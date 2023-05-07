package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DishClass;

public interface DishClassMapper {
	/*添加菜谱类别信息*/
	public void addDishClass(DishClass dishClass) throws Exception;

	/*按照查询条件分页查询菜谱类别记录*/
	public ArrayList<DishClass> queryDishClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有菜谱类别记录*/
	public ArrayList<DishClass> queryDishClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的菜谱类别记录数*/
	public int queryDishClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条菜谱类别记录*/
	public DishClass getDishClass(int classId) throws Exception;

	/*更新菜谱类别记录*/
	public void updateDishClass(DishClass dishClass) throws Exception;

	/*删除菜谱类别记录*/
	public void deleteDishClass(int classId) throws Exception;

}
