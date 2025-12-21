export default {

  email (rule, value, callback) { // 邮箱校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value)
      if (result == false) {
        callback('您输入的不符合email格式要求')
      } else {
        callback()
      }
    }
  },

  mobile (rule, value, callback) { // 手机校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^1[3456789]\d{9}$/.test(value)
      if (result == false) {
        callback('您输入的手机号码格式不正确')
      } else {
        callback()
      }
    }
  },
  mobiledw (rule, value, callback) { // 手机校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^1[3456789]\d{9}$/.test(value)
      if (result == false) {
        callback('您输入的手机号码格式不正确')
      } else {
        callback()
      }
    }
  },

  tel (rule, value, callback) { // 电话校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(value)
      if (result == false) {
        callback('您输入的座机号码格式不正确')
      } else {
        callback()
      }
    }
  },

  idcard (rule, value, callback) { // 身份证校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = value
      if (result.length == 15) {
        if (!/^\d{14}(\d|x)$/i.test(value)) {
          callback('你输入的身份证长度或格式错误')
        } else {
          callback()
        }
      }
      if (!/^\d{17}(\d|x)$/i.test(result)) {
        callback('你输入的身份证长度或格式错误')
      } else {
        callback()
      }
    }
  },

  code (rule, value, callback) { // 社会统一信用代码校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}$/g.test(value)
      if (result == false) {
        callback('您输入的统一社会信用代码格式不正确')
      } else {
        callback()
      }
    }
  },
  zipCode (rule, value, callback) { // 邮政编码
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^\d{6}$/g.test(value)
      if (result == false) {
        callback('您输入的邮政编码格式不正确')
      } else {
        callback()
      }
    }
  },
  number (rule, value, callback) { // 数字校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^\d+(\.\d+)?$/g.test(value)
      if (result == false) {
        callback('您输入不是有效的数字')
      } else {
        callback()
      }
    }
  },
  bankcode (rule, value, callback) { // 银行卡号校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^([1-9]{1})(\d{14}|\d{18})$/.test(value)
      if (result == false) {
        callback('您输入的银行卡号格式不正确')
      } else {
        callback()
      }
    }
  },
  bankcodedw (rule, value, callback) { // 银行卡号校验
    if (value == undefined || value == '' || value == null) {
      callback()
    } else {
      let result = /^([1-9]{1})(\d{11,21})$/.test(value)
      if (result == false) {
        callback('您输入的银行卡号格式不正确')
      } else {
        callback()
      }
    }
  },
   errormsg(message){
    Modal.error({
      title: '错误信息',
      // 如果需要弹窗显示的仅为一个字符串文本，则可以直接传入字符串
      content: message,
      closable: true,
      footer: true
    })
  },
  warnmsg(message){
    Modal.warn({
      title: '警告信息',
      // 如果需要弹窗显示的仅为一个字符串文本，则可以直接传入字符串
      content: message,
      closable: true,
      footer: true
    })
  },
  successmsg(message){
    Modal.success({
      title: '成功信息',
      // 如果需要弹窗显示的仅为一个字符串文本，则可以直接传入字符串
      content: message,
      closable: true,
      footer: true
    })
  },
  // 判断字符是否为空的方法
  isEmpty (obj) {
    // eslint-disable-next-line eqeqeq
    if (typeof obj == 'undefined' || obj == null || obj == '') {
      return true
    } else {
      return false
    }
  },
}
