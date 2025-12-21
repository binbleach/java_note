<template>
  <div>
    <div class="common-title">
      <div class="icon-first"/>
      <div class="icon-second"/>
      <div class="title-main">
        就业登记
      </div>
    </div>
    <ta-form
        layout="horizontal"
        :form-layout="true"
        :auto-form-create="(form) => {this.sixthForm = form;}"
    >
      <ta-form-item v-show="false" field-decorator-id="acc030" label="就业登记编号">
        <ta-input />
      </ta-form-item>
      <ta-form-item
          :require="{ message: '请选择就业方式!' }"
          field-decorator-id="aac318"
          init-value="022"
          label="就业方式"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
      >
        <ta-select
            allow-clear
            show-search
            collection-type="aac318"
            style="width: 100%"
            collection-filter="021,022"
            :reverse-filter="true"
            @select="changeAcc318"
        />
      </ta-form-item>

      <div v-show="selectPersonalEmp">
        <ta-form-item v-show="false" field-decorator-id="aab001" label="就业单位编号">
          <ta-input />
        </ta-form-item>
        <ta-form-item
            field-decorator-id="aab004"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            label="就业单位"
        >
          <ta-input />
        </ta-form-item>
        <ta-form-item
            field-decorator-id="aab998"
            label="统一社会信用代码"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            :field-decorator-options="{ rules: [{ validator: code, message: '请输入正确的统一社会信用代码' }] }"
        >
          <ta-input :max-length="20" />
        </ta-form-item>
        <ta-form-item
            label="创业标识"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc979"
            :field-decorator-options="{rules: [{ required:selectPersonalEmp, message: '请选择创业标识' }],}"
        >
          <ta-select collection-type="yesorno" @select="selectAcc979" />
        </ta-form-item>
        <ta-form-item
            v-show="showAcc321"
            label="创业带动就业人数"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc321"
        >
          <ta-input-number
              :precision="0"
              :min="0"
              :span="12"
              style="width: 100%"
          />
        </ta-form-item>
        <ta-form-item
            label="经济类型"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc029"
            :field-decorator-options="{rules: [{ required:selectPersonalEmp, message: '请选择经济类型' }],}"
        >
          <ta-select collection-type="acc029" collection-filter="4,5" :reverse-filter="true" />
        </ta-form-item>
      </div>

      <ta-form-item
          :require="{ message: '请选择就业地点!' }"
          :span="16"
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 19 }"
          field-decorator-id="acc335"
          label="就业地点"
      >
        <area-cascader-modal ref="areaCascaderModal3" @areaChange="areaChange3" />
      </ta-form-item>

      <ta-form-item
          label="就业地点详细地址"
          :span="16"
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 19 }"
          field-decorator-id="acc337"
      >
        <ta-input />
      </ta-form-item>

      <div v-show="!selectPersonalEmp">
        <ta-form-item
            label="居住地详细地址"
            :span="16"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 19 }"
            field-decorator-id="acc042"
        >
          <ta-input />
        </ta-form-item>
        <ta-form-item
            label="居住证到期时间"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc043"
        >
          <ta-date-picker style="width: 100%" />
        </ta-form-item>
        <ta-form-item
            label="个人月收入"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc328"
        >
          <ta-input-number :precision="0" style="width: 100%" :max="999999" :min="0" />
        </ta-form-item>
        <ta-form-item
            label="灵活就业类型"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc998"
            :field-decorator-options="{rules: [{ required:!selectPersonalEmp, message: '请选择灵活就业类型!' }],}"
        >
          <ta-select collection-type="acc998" />
        </ta-form-item>
        <ta-form-item
            label="灵活就业开始时间"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="acc03h"
            :field-decorator-options="{rules: [{ required:!selectPersonalEmp, message: '请选择灵活就业开始时间!' }],}"
        >
          <ta-date-picker  :disabledDate="disabledDate" style="width: 100%" @change="changeAcc03h" />
        </ta-form-item>
        <ta-form-item
            label="所属行业"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="aca112"
            :field-decorator-options="{rules: [{ required:!selectPersonalEmp, message: '请选择所属行业!' }],}"

        >
          <ta-select collection-type="AAB022" :collection-filter="aab022Str" :reverse-filter="true" />
        </ta-form-item>
        <ta-form-item
            label="职业资格工种"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="aca111"
        >
          <work-type-select />
        </ta-form-item>
        <ta-form-item
            label="灵活就业工作内容"
            :span="8"
            :label-col="{ span: 10 }"
            :wrapper-col="{ span: 14 }"
            field-decorator-id="bcc452"
            :field-decorator-options="{rules: [{ required:!selectPersonalEmp, message: '请选择灵活就业工作内容!' }],}"
        >
          <ta-select collection-type="bcc452"></ta-select>
        </ta-form-item>
      </div>

      <ta-form-item
          :require="{ message: '请选择就业登记日期!' }"
          field-decorator-id="aae043"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          label="就业登记日期"
          :disabled="!selectPersonalEmp"
      >
        <ta-date-picker valid-now-time="right" format="YYYY-MM-DD" style="width: 100%" />
      </ta-form-item>
    </ta-form>
  </div>
</template>

<script>
import moment from 'moment'
import areaCascaderModal from '../componentsYn/areaCascaderModal.vue'
import workTypeSelect from '../componentsYn/workTypeSelect.vue'
import $rules from '../js/publicMethods.js'
export default {
  name: "sixthMatter",
  components: { areaCascaderModal, workTypeSelect, },
  props: ['personData', 'fileList'],
  data () {
    return {
      selectPersonalEmp: false, // 选择个体经营
      aab022Str:
          '0100,0200,0300,0400,0500,0600,0700,0800,0900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,9900',
      showAcc321: false, // 显示创业带动就业人数
    }
  },
  methods: {
    saveData () {
      const sixthData = this.sixthForm.getFieldsValue()
      console.log("sixthMatter in",this.personData)
      this.fnAddPsnBaseInfo(sixthData,'save')
    },
    fnAddPsnBaseInfo (sixthData, type) {
      const ae3bObj = {}
      const personData = this.personData
      const _fileList = []
      this.fileList.forEach((item, index) => {
        const _item = { ...item, }
        // 选择容缺材料时会返回一条aae707为空的数据（编辑也会返回） aae558与bae480用于确定对象删除
        if (_item.aae535 === '') {
          _item.ada120 = 'JY1011'
          _fileList.push(_item)
        }
      })
      ae3bObj.aae070 = this.personData.aae070 // 失业登记帮扶一件事主键
      ae3bObj.aae071 = '6'
      ae3bObj.aae642 = this.aae642
      ae3bObj.aab301 = personData.bae217
      // ae3bObj.aab301 = formData.applyFlag === '1' ? formData.aab301 : formData.aac303
      // if (ae3bObj.aab301.substring(0, 2) != '53') {
      //   this.$message.warn('申报地请选择云南省地区!')
      //   return
      // }
      ae3bObj.aae549 = 'JY1011' // 审核类型
      ae3bObj.aae557 = '1' // 申报对象（1：个人 2：单位 3: 机构）
      ae3bObj.aae558 = personData.aac001
      ae3bObj.aae559 = personData.aac003
      ae3bObj.aae903 = personData.aac147
      ae3bObj.aae643 = '1' // 是否存在经办审核（0否,1是）
      ae3bObj.bae027 = personData.aac003 + ' 人员就业登记-一件事！' // 申报摘要信息 无效
      ae3bObj.baz004 = '1' // 是否批量提交单个审核
      ae3bObj.baz005 = '1' // 申报类型（暂存数据，提交数据）;
      ae3bObj.aae100 = '1' // 数据有效标志(0无效1有
      ae3bObj.bae065 = '' // 申报业务数据串
      ae3bObj.aae400 = '44' // 申报业务数据串
      const ae3bA1Obj = {}
      ae3bA1Obj.aae549 = 'JY1011' // 审核类型
      ae3bA1Obj.aae557 = '1' // 申报对象（1：个人 2：单位 3: 机构）
      ae3bA1Obj.aae558 = personData.aac001 // 申报对象编号（aac001、aab001）
      ae3bA1Obj.aae559 = personData.aac003 // 申报对象名称（aac003、aab004）
      ae3bA1Obj.aae903 = personData.aac147 // 申报对象名称证件号码
      ae3bA1Obj.bae027 = personData.aac003 + ' 申报人员就业登记（一件事）！' // 申报摘要信息
      ae3bA1Obj.aae013 = personData.aac003 + ' 申报人员就业登记（一件事）！' // 备注
      ae3bA1Obj.aae400 = '44' // 数据来源
      ae3bA1Obj.aae100 = '1' // 数据有效标志(0无效1有效)
      ae3bA1Obj.bae065 = { ...sixthData, ...personData, } // 待审核业务数据串
      const pbae065 = {}
      pbae065.netHallSaveDeailInfos = ae3bA1Obj
      pbae065.unit = { ...sixthData, ...personData, }
      pbae065.fileList = _fileList
      ae3bObj.bae065 = JSON.stringify(pbae065)
      ae3bObj.handleItemCode = 'JY1011'
      ae3bObj.fileListStr = JSON.stringify(_fileList)
      ae3bObj.netHallSaveDeailInfoStr = JSON.stringify(ae3bA1Obj)
      ae3bObj.apiCode = 'jy1011Api_emp02'
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
        result.name = '就业登记申请'
        if (res.resultData.errMsg !== '') {
          result.errorMessage = res.resultData.errMsg
          console.log('jy1011Api_emp02',result)
        }
        this.$emit("saveDataAfter",result);
      })
    },
    // 别删父组件用到
    getFormData () {
      const sixthValues = this.sixthForm.getFieldsValue()
      sixthValues.aae043 = sixthValues.aae043 && moment(sixthValues.aae043).format('YYYY-MM-DD') // 就业登记日期
      sixthValues.aac046 = sixthValues.aac046 && moment(sixthValues.aac046).format('YYYY-MM-DD') // 毕业日期
      sixthValues.acc043 = sixthValues.acc043 && moment(sixthValues.acc043).format('YYYY-MM-DD') // 居住证到期时间
      sixthValues.acc03h = sixthValues.acc03h && moment(sixthValues.acc03h).format('YYYY-MM-DD') // 灵活就业开始时间
      return sixthValues
    },
    areaChange3 (obj) {
      if (obj.areacode && obj.areacode.substr(0, 2) === '53' && obj.areacode.splice(-3) === '000') {
        this.$refs.areaCascaderModal3.handleReset()
        this.$message.warning('所在地在云南省，需要选择到社区级！！！！')
      }
    },
    fnInitAlreadyHandleBizCheck () {
      const currentUser = this.Base.getUserInfo()
      const param = {
        apiCode: 'jy1011Api_emp01',
        aac147: currentUser.aac147,
        aac001: currentUser.aac001,
        disSenseForWt: JSON.stringify([{ key: 'aac147', type: '1', }]),
      }
      this.Base.businessQuery(param, (res) => {
        const that = this
        if (res.code !== '1') {
          console.log('aaaa===========',res.message)
          if (res.message.includes('个人失业登记')) {
            res.message = res.message.split('，')[0] + '，需失业登记审核完成后才能办理就业登记！'
          }
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
    disabledDate (current) {
      return current > (moment().endOf('day'))
    },
    changeAcc03h (date) {
      if (date) {
        this.sixthForm.setFieldsValue({ aae043: date, })
      } else {
        this.sixthForm.resetFields(['aae043'])
      }
    },
    // 就业方式/就业形式---change
    changeAcc318 (val) {
      if (val === '021') {
        // 个体经营
        this.selectPersonalEmp = true
        this.$nextTick(() => this.resetOther())
      } else if (val === '022') {
        // 灵活就业
        this.selectPersonalEmp = false
        this.showAcc321 = false
        this.$nextTick(() => this.resetPersonal())
      }
    },
    resetPersonal () {
      this.sixthForm.resetFields(['aab001', 'aab004', 'aab998', 'acc979', 'acc321', 'acc029'])
    },
    resetOther () {
      this.sixthForm.resetFields(['acc042', 'acc043', 'acc328', 'acc998', 'acc03h', 'aca112', 'aca111', 'bcc452'])
    },
    // 社会统一信用代码校验
    code (rule, value, callback) {
      $rules.code(rule, value, callback)
    },
    // 选择就业标识
    selectAcc979 (val) {
      this.showAcc321 = val === '1'
    },
  },
}
</script>

<style scoped type="text/less" >
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
