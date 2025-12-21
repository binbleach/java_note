<template>
  <div>
    <div class="common-title">
      <div class="icon-first"/>
      <div class="icon-second"/>
      <div class="title-main">
        就业援助（就业困难人员认定）
      </div>
    </div>
    <ta-form
        layout="horizontal"
        :form-layout="true"
        :auto-form-create="(form) => {this.fifthForm = form;}"
    >
      <ta-form-item
          label="就业困难类型"
          :span="16"
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 19 }"
          field-decorator-id="adc310"
          :field-decorator-options="{ rules: [{ required: true, message: '请选择就业困难类型!' }] }"
      >
        <ta-select
            collection-type="adc310"
            collection-filter="001,002,004,005,041,093,094"
            :reverse-filter="true"
            @change="typeChange"
        />
      </ta-form-item>
      <ta-form-item
          v-show="vShowBae096"
          label="是否残疾军人"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          field-decorator-id="bae096"
          :field-decorator-options="fieldBae096"
      >
        <ta-select allow-clear show-search collection-type="yesorno"/>
      </ta-form-item>
    </ta-form>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: "fifthMatter",
  components: {},
  props: ['personData', 'fileList', 'fnPrev'],
  data () {
    return {
      vShowAcc462: false,
      fieldAcc462: {},
      fieldBae096: {},
      vShowBae096: false,
      dataSource: [],
    }
  },
  methods: {
    saveData () {
      const personData = this.personData
      const fifthData = this.fifthForm.getFieldsValue()
      const _fileList = []
      this.fileList.forEach((item, index) => {
        const _item = { ...item, }
        // 选择容缺材料时会返回一条aae707为空的数据（编辑也会返回） aae558与bae480用于确定对象删除
        if (_item.aae535 !== '2') {
          _item.aae535 = ''
          _item.ada120 = 'JY122'
          _fileList.push(_item)
        }
      })
      const bae065 = {
        ...personData,
        ...fifthData,
      }
      console.log('bae065', bae065)
      bae065.cc02List = JSON.stringify(this.dataSource)
      bae065.fileList = JSON.stringify(_fileList)
      const params = {
        apiCode: 'jy122Api_emp03',
        aae557: '1',
        aae558: personData.aac001,
        aae559: personData.aac003,
        aae903: personData.aac147,
        disSenseForWt: JSON.stringify([{ key: 'aae903',type: '1', }, { key: 'aae559',type: '2', }]),
        aae642: bae065.aae642,
        aab301: bae065.bae217,
        handleItemCode: 'JY122',
        bae065: JSON.stringify(bae065),
        fileList: JSON.stringify(_fileList),
        // tableDataStr: JSON.stringify(this.tableData),
        baz005: '1',
        aae535: parseInt(bae065.adc310, 10),
        aae400: '44',
        aae070: personData.aae070, // 失业登记帮扶一件事主键
        aae071: '5',
      }
      this.Base.commonSave(params, (res) => {
        const result = {}
        result.name = '就业援助（就业困难人员认定）'
        if (res.resultData.code === '-1') {
          result.errorMessage = res.resultData.message
          if (result.errorMessage === undefined || result.errorMessage === ''){
            result.errorMessage = '-1'
          }
          console.log('jy122Api_emp03',result)
        }
        this.$emit("saveDataAfter",result);
      })
    },
    // 别删父组件用到
    getFormData () {
      const fifthValues = this.fifthForm.getFieldsValue()
      fifthValues.cc02List = this.dataSource
      return fifthValues
    },
    searchInformation () {
      const param1 = {
        apiCode: 'jy122Api_emp01', // WT0000065
        aac001: this.personData.aac001,
        aae549: 'JY122',
      }
      this.Base.businessQuery(param1, (res) => {
        if (res.code === '-1') {
          const that = this
          Modal.warning({
            title: '提示消息',
            content: res.message,
            onOk () {
              that.$emit('fnPrev')
            },
          })
        } else {
          this.dataSource = res.cc02List
        }
      })
    },
    typeChange (e) {
      if (e === '001') {
        const aac004 = this.personData.aac004
        const aac006 = moment(this.personData.aac006).format('yyyy-MM-DD')
        const age = this.getAge(aac006)
        if (aac004 === '1' && age < 50) {
          this.lossWorktip('您的年龄未超过50岁！无法选择此类型!')
          return
        }
        if (aac004 === '2' && age < 40) {
          this.lossWorktip('您的年龄未超过40岁！无法选择此类型!')
          return
        }
      }

      // this.onSerial.aae535 = parseInt(e, 10)
      if (e === '002') {
        this.vShowBae096 = true
        this.fieldBae096 = {
          rules: [{
            required: true,
            message: '请选择是否退役军人',
          }],
        }
      } else {
        this.vShowBae096 = false
        this.fieldBae096 = {}
        this.fifthForm.setFieldsValue({ bae096: null, })
      }
    },
    getAge (vl) {
      if (vl) {
        const birthdays = new Date(vl.replace(/-/g, '/'))
        const d = new Date()
        const age =
            d.getFullYear() -
            birthdays.getFullYear() -
            (d.getMonth() < birthdays.getMonth() ||
            (d.getMonth() == birthdays.getMonth() &&
                d.getDate() < birthdays.getDate())
              ? 1
              : 0)
        return age
      }
    },
    lossWorktip (msg) {
      Modal.warning({
        title: '提示',
        content: msg,
      })
      setTimeout(() => {
        this.fifthForm.resetFields(['adc310'])
      })
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
