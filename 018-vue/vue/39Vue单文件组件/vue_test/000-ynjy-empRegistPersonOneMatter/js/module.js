import Vue from 'vue'
import VueBus from 'vue-bus'
import {
  avatar,
  button,
  form,
  input,
  modal,
  steps,
  containerMask,
  message,
  spin,
  icon,
  menu,
  checkbox,
  drawer,
  badge,
  pageTool,
  tabs,
  tag,
  dropdown,
  select,
  upload,
  divider,
  list,
  userInput, // 包括 TaUserInput
  breadcrumb,
  notification,
  configProvider,
  Switch,
  userSelect,
  col,
  row,
  autoComplete,
  carefulDelete,
  tableOperate,
  collapse,
  tagSelect,
  BigTable,
  pagination,
  table,
  radio,
  suggest,
  card,
  carousel,
  cascader,
  tooltip,
  popoverEx,
  datePicker,
  timeline,
  inputNumber,
  progress,
  timePicker,
  slider,
  affix,
  borderLayout,
  empty,
  alert,
  formModel,
} from '@yh/ta404-ui'

// 基础工具
import formUtil from '@yh/ta404-ui/es/utils/js/form.util'
import windowUtil from '@yh/ta404-ui/es/utils/js/window.util'
import cryptoFn from '@yh/ta404-ui/es/utils/js/cryptoFn'
import sendRequest from '@/common/psmp/js/request.js'
// TaUtils
import {
  getNowPageParam,
  objectToUrlParam,
  getCookie,
  setCookie,
  getToken,
  webStorage,
  isIE,
  notSupported,
  isIE9,
  isIE10,
  isIE11,
  isChrome,
  isFireFox,
  isSafari,
  isSilversea,
  clientSystem,
  clientScreenSize,
  clientBrowser,
  getWidth,
  getHeight,
  getStyle,
  pinyin,
  getCurDate,
  getCurDateMonth,
  getCurDateTime,
  getCurDateFullTime,
  getCurQuarter,
  getCurIssue,
} from '@yh/ta-utils'
import axios from 'axios'

// 分别引入404ui组件
import richEditor from '@yh/ta404-ui/es/rich-editor'
import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer'
import evaluateCom from '@projectCommon/componentsYn/evaluateCom.vue'

Vue.use(userInput)
Vue.use(VueBus)
Vue.use(autoComplete)
Vue.use(carefulDelete)
Vue.use(tableOperate)
Vue.use(collapse)
Vue.use(userInput)
Vue.use(col)
Vue.use(row)
Vue.use(tagSelect)
Vue.use(userSelect)
Vue.use(avatar)
Vue.use(button)
Vue.use(form)
Vue.use(input)
Vue.use(modal)
Vue.use(icon)
Vue.use(spin)
Vue.use(steps)
Vue.use(menu)
Vue.use(badge)
Vue.use(checkbox)
Vue.use(pageTool)
Vue.use(drawer)
Vue.use(containerMask)
Vue.use(message)
Vue.use(tabs)
Vue.use(tag)
Vue.use(dropdown)
Vue.use(select)
Vue.use(upload)
Vue.use(divider)
Vue.use(list)
Vue.use(breadcrumb)
Vue.use(configProvider)
Vue.use(Switch)
Vue.use(BigTable)
Vue.use(table)
Vue.use(radio)
Vue.use(suggest)
Vue.use(card)
Vue.component('evaluateCom', evaluateCom)

Vue.use(affix)
Vue.use(carousel)
Vue.use(cascader)
Vue.use(tooltip)
Vue.use(pagination)
Vue.use(popoverEx)
Vue.use(datePicker)
Vue.use(timeline)
Vue.use(inputNumber)
Vue.use(progress)
Vue.use(timePicker)
Vue.use(slider)
Vue.use(richEditor)
Vue.use(borderLayout)
Vue.use(empty)
Vue.use(alert)
Vue.component('Viewer', Viewer)
Vue.use(Viewer, {
  defaultOptions: { zIndex: 9999 },
})
Viewer.setDefaults({
  Options: {
    inline: false,
    button: true,
    navbar: false,
    title: false,
    toolbar: true,
    tooltip: true,
    movable: true,
    zoomable: true,
    rotatable: true,
    scalable: true,
    transition: true,
    fullscreen: true,
    keyboard: true,
    url: 'data-source',
  },
})
// 从@yh/ta-utils中引入的工具
const polyfill = {
  pinyin,
  getNowPageParam,
  objectToUrlParam,
  getCookie,
  setCookie,
  getToken,
  webStorage,
  isIE,
  notSupported,
  isIE9,
  isIE10,
  isIE11,
  isChrome,
  isFireFox,
  isSafari,
  isSilversea,
  clientSystem,
  clientScreenSize,
  clientBrowser,
  getWidth,
  getHeight,
  getStyle,
  getCurDate,
  getCurDateMonth,
  getCurDateTime,
  getCurDateFullTime,
  getCurQuarter,
  getCurIssue,
}

const indexUtils = {
  ...formUtil,
  ...windowUtil,
  // 如果不需要在Base上使用polyfill中的工具，则下面的可以注释
  ...polyfill,
  ...cryptoFn,
}

Vue.prototype.$axios = axios
Vue.prototype.Base = {
  ...indexUtils,
  ...containerMask.$mask,
  ...sendRequest,
}
Vue.prototype.$message = message
Vue.prototype.$info = modal.info
Vue.prototype.$success = modal.success
Vue.prototype.$error = modal.error
Vue.prototype.$warning = modal.warning
Vue.prototype.$confirm = modal.confirm
Vue.prototype.$notification = notification
window.message = message
window.notification = notification
window.Modal = modal
window.Spin = spin
window.Base = Vue.prototype.Base
window.indexTool = {
  gotoLogin: (options) => {
    const url = options?.url || 'login.html'
    window.location.href = url
  },
}
// 注册到window
window.TaUtils = {
  ...polyfill,
}
