package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DishCollect;

public interface DishCollectMapper {
	/*添加菜谱收藏信息*/
	public void addDishCollect(DishCollect dishCollect) throws Exception;

	/*按照查询条件分页查询菜谱收藏记录*/
	public ArrayList<DishCollect> queryDishCollect(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有菜谱收藏记录*/
	public ArrayList<DishCollect> queryDishCollectList(@Param("where") String where) throws Exception;

	/*按照查询条件的菜谱收藏记录数*/
	public int queryDishCollectCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条菜谱收藏记录*/
	public DishCollect getDishCollect(int collectId) throws Exception;

	/*更新菜谱收藏记录*/
	public void updateDishCollect(DishCollect dishCollect) throws Exception;

	/*删除菜谱收藏记录*/
	public void deleteDishCollect(int collectId) throws Exception;

}
