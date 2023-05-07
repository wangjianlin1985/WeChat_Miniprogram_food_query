package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DishCollect {
    /*收藏id*/
    private Integer collectId;
    public Integer getCollectId(){
        return collectId;
    }
    public void setCollectId(Integer collectId){
        this.collectId = collectId;
    }

    /*收藏菜谱*/
    private Dish dishObj;
    public Dish getDishObj() {
        return dishObj;
    }
    public void setDishObj(Dish dishObj) {
        this.dishObj = dishObj;
    }

    /*收藏用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*收藏时间*/
    @NotEmpty(message="收藏时间不能为空")
    private String collectTime;
    public String getCollectTime() {
        return collectTime;
    }
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDishCollect=new JSONObject(); 
		jsonDishCollect.accumulate("collectId", this.getCollectId());
		jsonDishCollect.accumulate("dishObj", this.getDishObj().getDishName());
		jsonDishCollect.accumulate("dishObjPri", this.getDishObj().getDishId());
		jsonDishCollect.accumulate("userObj", this.getUserObj().getName());
		jsonDishCollect.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonDishCollect.accumulate("collectTime", this.getCollectTime().length()>19?this.getCollectTime().substring(0,19):this.getCollectTime());
		return jsonDishCollect;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}