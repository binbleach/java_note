
import Vue from 'vue'
import App from './App.vue'
import {c} from './hunhe'
Vue.config.productionTip = false
//全局加混合
Vue.mixin(c)
new Vue({

  render: h => h(App),
}).$mount('#app')
