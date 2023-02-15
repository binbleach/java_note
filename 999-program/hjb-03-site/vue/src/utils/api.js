import axios from 'axios'
import store from "../store";

let base = '';
//export const requestUrl = 'http://120.77.182.180:8080/activity_place'
export const requestUrl = 'http://127.0.0.1:8077';

axios.interceptors.request.use(function (config) {
  // 在发送请求前的操作
  // 判断是否存在token,如果存在将每个页面header添加token
  console.log("store.state.token",store.state.token)
  if (store.state.token&&store.state.token!=null) {
    config.headers.common['Authorization'] = "Bearer " + store.state.token
  }
  return config
}, function (error) {
  router.push('/')
  return Promise.reject(error)
})

export const postRequest = (url, params) => {
  console.log("==============",`${base}${url}`)
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      // Do whatever you want to transform the data
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}
export const postRequestRow = (url, params) => {
  console.log("==============",`${base}${url}`)
  console.log("parma=======",JSON.stringify(params))
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: JSON.stringify(params),
    // transformRequest: [function (data) {
    //   // Do whatever you want to transform the data
    //   let ret = ''
    //   for (let it in data) {
    //     ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    //   }
    //   return ret
    // }],
    headers: {
      'Content-Type': "application/json;charset=UTF-8",
    }
  });
}
export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}
export const deleteRequest = (url) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`
  });
}
export const getRequest = (url,params) => {
  return axios({
    method: 'get',
    data:params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    url: `${base}${url}`
  });
}
