package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dish {
    /*菜谱id*/
    private Integer dishId;
    public Integer getDishId(){
        return dishId;
    }
    public void setDishId(Integer dishId){
        this.dishId = dishId;
    }

    /*菜谱类别*/
    private DishClass dishClassObj;
    public DishClass getDishClassObj() {
        return dishClassObj;
    }
    public void setDishClassObj(DishClass dishClassObj) {
        this.dishClassObj = dishClassObj;
    }

    /*菜谱名称*/
    @NotEmpty(message="菜谱名称不能为空")
    private String dishName;
    public String getDishName() {
        return dishName;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    /*菜谱图片*/
    private String dishPhoto;
    public String getDishPhoto() {
        return dishPhoto;
    }
    public void setDishPhoto(String dishPhoto) {
        this.dishPhoto = dishPhoto;
    }

    private String dishPhotoUrl;
    public void setDishPhotoUrl(String dishPhotoUrl) {
		this.dishPhotoUrl = dishPhotoUrl;
	}
	public String getDishPhotoUrl() {
		return  SessionConsts.BASE_URL + dishPhoto;
	}
    /*参考价格*/
    @NotNull(message="必须输入参考价格")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*菜谱介绍*/
    @NotEmpty(message="菜谱介绍不能为空")
    private String dishDesc;
    public String getDishDesc() {
        return dishDesc;
    }
    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    /*浏览量*/
    @NotNull(message="必须输入浏览量")
    private Integer viewNum;
    public Integer getViewNum() {
        return viewNum;
    }
    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDish=new JSONObject(); 
		jsonDish.accumulate("dishId", this.getDishId());
		jsonDish.accumulate("dishClassObj", this.getDishClassObj().getClassName());
		jsonDish.accumulate("dishClassObjPri", this.getDishClassObj().getClassId());
		jsonDish.accumulate("dishName", this.getDishName());
		jsonDish.accumulate("dishPhoto", this.getDishPhoto());
		jsonDish.accumulate("price", this.getPrice());
		jsonDish.accumulate("dishDesc", this.getDishDesc());
		jsonDish.accumulate("viewNum", this.getViewNum());
		jsonDish.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonDish;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}