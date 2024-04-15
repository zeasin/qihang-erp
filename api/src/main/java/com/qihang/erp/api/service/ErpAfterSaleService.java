package com.qihang.erp.api.service;

import com.qihang.common.PageQuery;
import com.qihang.common.PageResult;
import com.qihang.erp.api.domain.ErpAfterSale;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author TW
* @description 针对表【erp_after_sale(售后处理表)】的数据库操作Service
* @createDate 2024-04-15 14:30:34
*/
public interface ErpAfterSaleService extends IService<ErpAfterSale> {
    PageResult<ErpAfterSale> queryPageList(ErpAfterSale bo, PageQuery pageQuery);
}
