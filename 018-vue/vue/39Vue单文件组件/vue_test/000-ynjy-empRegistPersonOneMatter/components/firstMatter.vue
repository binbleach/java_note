<template>
  <div>
    <div class="common-title">
      <div class="icon-first"/>
      <div class="icon-second"/>
      <div class="title-main">
        失业登记信息
      </div>
    </div>
    <ta-form
        layout="horizontal"
        :form-layout="true"
        :auto-form-create="(form) => {this.firstForm = form;}"
    >
      <ta-form-item v-show="false" field-decorator-id="acc020" label="失业登记编号">
        <ta-input />
      </ta-form-item>
      <ta-form-item
          :span="8"
          init-value="1"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="ada302"
          label="失业登记类型"
          require
      >
        <ta-select show-search allow-clear collection-type="ada302" @select="fnSelectAda302" />
      </ta-form-item>
      <ta-form-item
          label="失业原因"
          field-decorator-id="ajc093"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          :disabled="disableAjc093"
          :require="{ message: '请选择失业原因!' }"
      >
        <ta-select
            allow-clear
            show-search
            collection-type="ajc093"
            option-label-prop="label"
            collection-filter="20,21,22,23,24,40,90"
            @select="selectAjc093"
        />
      </ta-form-item>
      <ta-form-item
          label="失业日期"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="ajc090"
          :require="{ message: '请选择失业日期!' }"
      >
        <ta-date-picker
            format="YYYY-MM-DD"
            style="width: 100%"
            :disabled-date="disabledDate"
            @change="changeAjc090"
        />
      </ta-form-item>
      <ta-form-item
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          v-show="false"
          label="原工作单位编号"
          field-decorator-id="aab001">
        <ta-input />
      </ta-form-item>
      <ta-form-item
          v-if="visibleAab004"
          label="原工作单位"
          field-decorator-id="aab004"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          :field-decorator-options="{
                rules: [{ required: true, message: '请选择原工作单位' }],
              }"
      >
        <ta-input />
      </ta-form-item>
      <ta-form-item
          label="有无求职意愿"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="acc978"
          :disabled="readOnlyAcc978"
          :field-decorator-options="{
                rules: [{ required: true, message: '请选择有无求职意愿' }],
              }"
      >
        <ta-select collection-type="YESORNO" />
      </ta-form-item>
      <ta-form-item
          label="是否有创业意愿"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="acc311"
          :field-decorator-options="{
                rules: [{ required: true, message: '请选择是否有创业意愿' }],
              }"
      >
        <ta-select collection-type="YESORNO" />
      </ta-form-item>
      <ta-form-item
          label="是否有培训意愿"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="acc312"
          :disabled="readOnlyAcc978"
          :field-decorator-options="{
                rules: [{ required: true, message: '请选择是否有培训意愿' }],
              }"
      >
        <ta-select collection-type="YESORNO" />
      </ta-form-item>
    </ta-form>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: "firstMatter",
  props: ['personData', 'fileList', 'fnPrev'],
  data () {
    return {
      selectPersonalEmp: false, // 选择个体经营
      selectOtherEmp: true, // 选择灵活就业
      disableAjc093: false,
      visibleAab004: false,
      count: 0,
      readOnlyAcc978: false,
    }
  },
  methods: {
    saveData () {
      const firstData = this.firstForm.getFieldsValue()
      this.fnAddPsnBaseInfo(firstData, 'save')
    },
    fnAddPsnBaseInfo (firstData, type) {
      const ae3bObj = {}
      const personData = this.personData
      const _fileList = []
      this.fileList.forEach((item, index) => {
        const _item = { ...item, }
        if (_item.aae535 !== '1') {
          _item.aae535 = ''
          _item.ada120 = 'JY1031'
          if (_item.bae481 === '身份证的正反面复印件') {
            _item.bae480 = '344890516903857155'
          }
          _fileList.push(_item)
        }
      })
      ae3bObj.aae070 = this.personData.aae070 // 失业登记帮扶一件事主键
      ae3bObj.aae071 = '1'
      ae3bObj.aae642 = ''
      ae3bObj.aab301 = personData.bae217
      // ae3bObj.aab301 = formData.applyFlag === '1' ? formData.aab301 : formData.aac303
      // if (ae3bObj.aab301.substring(0, 2) != '53') {
      //   this.$message.warn('申报地请选择云南省地区!')
      //   return
      // }
      ae3bObj.aae549 = 'JY1031' // 审核类型
      ae3bObj.aae557 = '1' // 申报对象（1：个人 2：单位 3: 机构）
      ae3bObj.aae558 = personData.aac001
      ae3bObj.aae559 = personData.aac003
      ae3bObj.aae903 = personData.aac147
      ae3bObj.aae643 = '1' // 是否存在经办审核（0否,1是）
      ae3bObj.bae027 = personData.aac003 + ' 人员失业登记（一件事）！' // 申报摘要信息 无效
      ae3bObj.baz004 = '1' // 是否批量提交单个审核
      ae3bObj.baz005 = '1' // 申报类型（暂存数据，提交数据）;
      ae3bObj.aae100 = '1' // 数据有效标志(0无效1有
      // ae3bObj.bae065 = '' // 申报业务数据串
      ae3bObj.aae400 = '44' // 申报业务数据串
      const ae3bA1Obj = {}
      ae3bA1Obj.aae549 = 'JY1031' // 审核类型
      ae3bA1Obj.aae557 = '1' // 申报对象（1：个人 2：单位 3: 机构）
      ae3bA1Obj.aae558 = personData.aac001 // 申报对象编号（aac001、aab001）
      ae3bA1Obj.aae559 = personData.aac003 // 申报对象名称（aac003、aab004）
      ae3bA1Obj.aae903 = personData.aac147 // 申报对象名称证件号码
      ae3bA1Obj.bae027 = personData.aac003 + ' 申报人员失业登记（一件事）！' // 申报摘要信息
      ae3bA1Obj.aae013 = personData.aac003 + ' 申报人员失业登记（一件事）！' // 备注
      ae3bA1Obj.aae400 = '44' // 数据来源
      ae3bA1Obj.aae100 = '1' // 数据有效标志(0无效1有效)
      ae3bA1Obj.bae065 = { ...firstData, ...personData, } // 待审核业务数据串
      const pbae065 = {}
      pbae065.netHallSaveDeailInfos = ae3bA1Obj
      pbae065.unit = { ...firstData, ...personData, }
      pbae065.fileList = _fileList
      ae3bObj.bae065 = JSON.stringify(pbae065)
      ae3bObj.handleItemCode = 'JY1031' // JSON.stringify(this.serviceData.handleItemCode)
      ae3bObj.fileListStr = JSON.stringify(_fileList)
      ae3bObj.netHallSaveDeailInfoStr = JSON.stringify(ae3bA1Obj)
      ae3bObj.apiCode = 'jy1031Api_emp02'
      ae3bObj.disSenseForWt = JSON.stringify([
        { key: 'aac002', type: '1', },
        { key: 'aac003', type: '2', },
        { key: 'aac147', type: '1', }
      ])
      if (type === 'pueseSave') {
        ae3bObj.baz005 = '0'
      } else {
        ae3bObj.baz005 = '1'
      }
      this.Base.commonSave(ae3bObj, (res) => {
        const result = {}
        result.name = '失业登记申请'
        if (res.resultData.errMsg !== '') {
          result.errorMessage = res.resultData.errMsg
          console.log('jy1031Api_emp02',result)
        }
        // this.$emit("saveDataAfter",result);
      })
    },
    fnInitAlreadyHandleBizCheck () {
      const param = {
        aac001: this.personData.aac001,
        apiCode: 'jy1031Api_emp01',
      }
      this.Base.businessQuery(param, (res) => {
        this.count = res.count
        const that = this
        if (res.code !== '1') {
          Modal.warning({
            title: '提示消息',
            content: res.message,
            onOk () {
              that.$emit('fnPrev')
            },
          })
        }
      })
    },
    disabledDate (startValue) {
      let endValue = this.personData.aac046
      endValue = endValue && moment(moment(endValue).format('YYYY-MM-DD'), 'YYYY-MM-DD')
      startValue = startValue && moment(moment(startValue).format('YYYY-MM-DD'), 'YYYY-MM-DD')
      const ada302 = this.firstForm.getFieldValue('ada302')
      if (!endValue || !startValue) {
        return startValue && startValue > moment().endOf('day')
      } else if (ada302 === '1') {
        // 失业登记，小于当前大于毕业日期
        return startValue > moment().endOf('day') || startValue <= moment(endValue)
      } else if (ada302 === '2') {
        // 求职登记，小于当前业日期
        return startValue > moment().endOf('day')
      }
    },
    // 别删父组件用到
    getFormData () {
      const firstValues = this.firstForm.getFieldsValue()
      firstValues.ajc090 = firstValues.ajc090 && moment(firstValues.ajc090).format('YYYY-MM-DD')
      return firstValues
    },
    changeAjc090 (val) {
      const ada302 = this.firstForm.getFieldValue('ada302')
      if (!ada302) {
        this.$message.error('请先选择失业登记类型！')
        this.firstForm.setFieldsMomentValue({ ajc090: null, })
        return
      }
      if (val) {
        val = val.toDate()
        const now = new Date()
        const valTime = val.getTime()
        const nowTime = now.getTime()
        const diffInMilliseconds = Math.abs(nowTime - valTime)
        const diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24)
        if (diffInDays > 183) {
          this.$message.error(`失业日期距离当前${parseInt(diffInDays)}天，超过183天，无法登记，请重新选择失业日期！`, 5)
          this.firstForm.setFieldsMomentValue({ ajc090: null, })
        }
      }
    },
    fnSelectAda302 (value) {
      if (value === '2') {
        // 必须是高校毕业生才能选择失业登记为求职登记
        const acc021 = this.personData.acc021
        if (acc021 !== '1') {
          this.$message.error('【是否高校毕业生】类型不符，【是否高校毕业生】须选择是')
          this.firstForm.resetFields(['ada302'])
          return
        }
        this.selectAjc093('10')
        this.firstForm.setFieldsValue({ ajc093: '10', })
        this.disableAjc093 = true
        // 失业类型是求职登记，校验毕业时间在半年内
        let aac046 = this.personData.aac046
        if (aac046) {
          aac046 = aac046.toDate()
          aac046.setDate(aac046.getDate() + 1)
          const date = new Date()
          date.setMonth(date.getMonth() - 6)
          if (date > aac046) {
            this.$message.error('失业类型选择求职登记，毕业时间必须在半年内', 5)
            this.firstForm.resetFields(['ada302'])
          }
        }
      } else {
        let aac046 = this.personData.aac046
        let ajc090 = this.firstForm.getFieldValue('ajc090')
        aac046 = aac046 && moment(aac046).format('YYYY-MM-DD')
        ajc090 = ajc090 && moment(ajc090).format('YYYY-MM-DD')
        if (ajc090 <= aac046) {
          this.$message.error('失业登记类型为失业登记时，失业时间必须大于毕业时间！')
          this.firstForm.resetFields(['ajc090'])
        }
        this.firstForm.resetFields(['ajc093'])
        this.disableAjc093 = false
      }
    },
    selectAjc093 (value) {
      // 存在就失业登记不能选择年满16周岁，从学校毕业、肄业的失业原因
      if (value === '10') {
        const aac001 = this.personData.aac001
        if (aac001) {
          const yab139 = this.personData.bae217
          if (yab139.substring(0, 4) === '5301' && this.count >= 1) {
            this.$message.error('非首次办理就失业登记，不能选择该失业原因！', 5)
            this.firstForm.resetFields(['ajc093'])
            return
          }
        }
        this.visibleAab004 = false
        this.firstForm.resetFields(['aab004'])
      }
      // 私营企业业主停业、破产停止经营、退出灵活就业
      if (value === '31' || value === '32') {
        this.visibleAab004 = true
        // this.onSerial.aae535 = '43'
      } else {
        this.visibleAab004 = false
        this.firstForm.resetFields(['aab004'])
      }
    },
  },
}
</script>

<style scoped type="text/less">
  .common-title {
    font-size: 1.13rem;
    font-family: Source Han Sans CN;
    display: flex;
    align-items: center;
    padding-bottom: 0.69rem;
    margin-bottom: 1.5rem;
    border-bottom: 0.06rem solid #eeeeee;

    .icon-first {
      width: 0.25rem;
      height: 1.13rem;
      background: #0365f1;
      border-radius: 0.06rem;
      margin-right: 0.25rem;
    }

    .icon-second {
      width: 0.13rem;
      height: 1.13rem;
      background: #ffae20;
      border-radius: 0.06rem;
      margin-right: 0.5rem;
    }

    .title-main {
      font-weight: 500;
      color: #333333;
      margin-right: 0.5rem;
    }

    .title-tips {
      font-weight: 400;
      color: #999999;
    }
  }
</style>
