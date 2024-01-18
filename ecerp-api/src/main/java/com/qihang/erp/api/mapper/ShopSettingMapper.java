package com.qihang.erp.api.mapper;

import java.util.List;
import com.qihang.erp.api.domain.ShopSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方平台设置Mapper接口
 * 
 * @author qihang
 * @date 2024-01-18
 */
@Mapper
public interface ShopSettingMapper 
{
    /**
     * 查询第三方平台设置
     * 
     * @param id 第三方平台设置主键
     * @return 第三方平台设置
     */
    public ShopSetting selectShopSettingById(Long id);

    /**
     * 查询第三方平台设置列表
     * 
     * @param shopSetting 第三方平台设置
     * @return 第三方平台设置集合
     */
    public List<ShopSetting> selectShopSettingList(ShopSetting shopSetting);

    /**
     * 新增第三方平台设置
     * 
     * @param shopSetting 第三方平台设置
     * @return 结果
     */
    public int insertShopSetting(ShopSetting shopSetting);

    /**
     * 修改第三方平台设置
     * 
     * @param shopSetting 第三方平台设置
     * @return 结果
     */
    public int updateShopSetting(ShopSetting shopSetting);

    /**
     * 删除第三方平台设置
     * 
     * @param id 第三方平台设置主键
     * @return 结果
     */
    public int deleteShopSettingById(Long id);

    /**
     * 批量删除第三方平台设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopSettingByIds(Long[] ids);
}
