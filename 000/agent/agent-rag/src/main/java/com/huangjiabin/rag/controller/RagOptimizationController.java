package com.huangjiabin.rag.controller;

import com.huangjiabin.rag.optimization.QueryRouteService;
import com.huangjiabin.rag.optimization.QuerySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
*    rag检索优化
*/
@RestController
@RequestMapping("/optimize")
public class RagOptimizationController {

    @Autowired
    private QueryRouteService queryRouteService;

    @Autowired
    private QuerySqlService querySqlService;

    /*
    *   查询路由优化
    */
    @RequestMapping("querySql")
    public String querySql(String query) {
        return querySqlService.text2sql(query);
    }

    /*
    *   查询构造优化
    */
    @GetMapping("/route")
    public String route(String query) {
        return queryRouteService.route(query);
    }

}
