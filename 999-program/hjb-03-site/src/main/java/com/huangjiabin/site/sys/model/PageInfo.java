package com.huangjiabin.site.sys.model;
 
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huangjiabin.site.sys.util.CommonUtil;
import lombok.Data;
 
import java.io.Serializable;
 
/**
 * @author wl
 * @date 2021/7/27
 */
@Data
public class PageInfo<T> implements Serializable {
    private JSONArray records;
    private long total;
    private long pageSize;
    private long current;
 
    public void setPageInfo(IPage<T> page) {
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.current = page.getCurrent();
        this.records = CommonUtil.listToJsonArray(page.getRecords());
    }
}