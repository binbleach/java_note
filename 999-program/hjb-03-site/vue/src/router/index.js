import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import ArticleList from '@/components/ArticleList'
import DataCharts from '@/components/DataCharts'
import PostArticle from '@/components/PostArticle'
import ResourcesTable from "../components/ResourcesTable";
import BlogDetail from '@/components/BlogDetail'
import ReserveTable from "../components/ReserveTable";
import User from "../components/User";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '登录',
      hidden: true,
      component: Login
    },
    {
      path: '/home',
      name: '',
      component: Home,
      hidden: true
    },
    {
      path: '/home',
      component: Home,
      name: '首页',
      iconCls: 'fa fa-bar-chart',
      children: [
        {
          path: '/home',
          iconCls: 'fa fa-bar-chart',
          name: '首页',
          component: DataCharts
        }
      ]
    },
    {
      path: '/home',
      component: Home,
      name: '场地管理',
      iconCls: 'fa fa-file-text-o',
      children: [
        {
          path: '/articleList',
          name: '场地列表',
          component: ArticleList,
          meta: {
            keepAlive: true
          }
        },
      ]
    },
    {
      path: '/home',
      component: Home,
      name: '资源管理',
      children: [
        {
          path: '/user',
          iconCls: 'fa fa-user-o',
          name: '资源管理',
          component: ResourcesTable
        }
      ]
    },
    {
      path: '/home',
      component: Home,
      name: '预约管理',
      children: [
        {
          path: '/cateMana',
          iconCls: 'fa fa-reorder',
          name: '预约管理',
          component: ReserveTable
        }
      ]
    },
    {
      path: '/home',
      component: Home,
      name: '用户管理',
      iconCls: 'fa fa-bar-chart',
      children: [
        {
          path: '/user',
          iconCls: 'fa fa-bar-chart',
          name: '安全管理',
          component: User
        }
      ]
    },
    {
      path: '/home',
      component: Home,
      name: '安全管理',
      iconCls: 'fa fa-bar-chart',
      children: [
        {
          path: '/charts',
          iconCls: 'fa fa-bar-chart',
          name: '安全管理',
          component: DataCharts
        }
      ]
    }
  ]
})
