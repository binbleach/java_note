<template>
  <div>
    <div class="add-person-box" style="margin-bottom: 20px">
    </div>
    <div class="common-title">
      <div class="icon-first"/>
      <div class="icon-second"/>
      <div class="title-main">
        失业登记注销
      </div>
    </div>
    <ta-tabs>
      <ta-tab-pane tab="失业登记信息">
        <ta-big-table
          ref="eighthTable"
          border
          resizable
          show-overflow
          highlight-hover-row
          :data="unEmpRegistHistory"
          row-id="aac020_1"
          align="center"
      >
        <!--        <ta-big-table-column field="aac147" title="证件号码" width="400"/>-->
        <!--        <ta-big-table-column field="aac003" title="姓名" width="120"/>-->
        <ta-big-table-column field="ajc093" title="失业登记原因" collection-type="ajc093"/>
        <ta-big-table-column field="aab004" title="原工作单位" width="400"/>
        <ta-big-table-column field="adc210" title="失业登记时间" width="150" format="yyyy-MM-dd"/>
        <ta-big-table-column field="aae100" title="有效状态" width="120" collection-type="aae100"/>
      </ta-big-table>
      </ta-tab-pane>
    </ta-tabs>
    <ta-form
        layout="horizontal"
        :form-layout="true"
        :auto-form-create="(form) => {this.eighthForm = form;}"
    >
      <ta-form-item v-show="false" field-decorator-id="acc020" label="失业登记编号">
        <ta-input />
      </ta-form-item>
      <ta-form-item
          label="失业登记注销原因"
          field-decorator-id="ajc00a"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          :require="{ message: '请选择失业登记注销原因' }"
      >
        <ta-select  collection-type="AJC00A"/>
      </ta-form-item>
    </ta-form>
  </div>
</template>

<script>
export default {
  name: "eighthMatter",
  props: ['personData', 'fileList', 'fnPrev'],
  data () {
    return {
      selectPersonalEmp: false, // 选择个体经营
      selectOtherEmp: true, // 选择灵活就业
      disableAjc093: false,
      unEmpRegistHistory: [],
    }
  },
  mounted () {
  },
  methods: {
    getLossWorkInfo () {
      this.unEmpRegistHistory.length = 0
      const userInfo = this.Base.getUserInfo()
      const params = {
        apiCode: 'jy1031Query',
        aac002: userInfo.aac147,
        disSenseForWt: JSON.stringify([{ key: 'aac002', type: '1', }]),
      }
      this.Base.businessQuery(params, (res) => {
        if (res.resultData.code === '-1') {
          this.$message.error(res.resultData.message ? res.resultData.message : '查询出错，请联系管理员！')
          return false
        }
        res.resultData.result.forEach((item, index) => {
          if (item.aae100 === '1') {
            this.unEmpRegistHistory.push(item)
          }
        })
        if (this.unEmpRegistHistory.length === 0) {
          const that = this
          Modal.warning({
            title: '提示消息',
            content: '未查询到有效的失业登记信息,无法办理失业登记注销！',
            onOk () {
              that.$emit('fnPrev')
            },
          })
        }
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
