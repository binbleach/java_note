<template>
  <div class="fit show-border">
    <div class="steps-title"></div>
    <div class="fit content">
      <ta-steps :current="currentPage" style="margin-top: 30px">
        <ta-step v-for="item in steps" :key="item.title" :title="item.title" />
      </ta-steps>
      <div class="steps-content">
        <div v-show="currentPage === 0">
          <guideComponent ref="guideRef"/>
        </div>
        <div v-show="currentPage === 1">
<!--          <recordComponent :checkedList="checkedList" @prev="prev" @fnDisabledPrev="fnDisabledPrev" :currentPage="currentPage" />-->
        </div>
      </div>
      <div class="steps-action">
        <ta-button v-if="currentPage < steps.length - 1" type="primary" @click="next" >下一步</ta-button>
        <ta-button v-if="currentPage>0" :disabled="disabledPrev" style="margin-left: 8px" @click="prev">上一步</ta-button>
      </div>
      <ta-modal
          title="个人承诺"
          v-model="commitmentModelV"
          @ok="commitmentOk"
          okText="确认"
          cancelText="取消"
      >
        <div>
          本人承诺填报的内容均真实、准确、有效，如与实际情况不一致，本人愿意承担相应责任，同时纳入人社信用记录。
        </div>
      </ta-modal>
    </div>
  </div>
</template>
<script>
import guideComponent from './components/guideComponent'
// import recordComponent from './components/recordComponent'
export default {
  name: 'empRegistPersonOneMatter',
  // components: { guideComponent, recordComponent },
  components: { guideComponent, },
  data () {
    return {
      currentPage: 0,
      steps: [{
        title: '联办须知',
      }, {
        title: '信息登记',
      }],
      checkedList: [],
      disabledPrev: false,
      commitmentModelV: false,
    }
  },
  methods: {
    next () {
      const checkedList = this.$refs.guideRef.checkedList
      if (checkedList.length < 1) {
        Modal.warning({
          title: '提示消息',
          content: '至少勾选一条需要办理的事项！',
          onOk () {
          },
        })
        return
      }
      this.checkedList = checkedList
      this.commitmentModelV = true
    },
    commitmentOk () {
      this.commitmentModelV = false
      this.currentPage++
    },
    prev () {
      this.currentPage = 0
    },
    fnDisabledPrev () {
      this.disabledPrev = true
    },
  },
}
</script>
<style scoped>
  .show-border {
    border: 0.875rem solid #F0F2F5;
  }
  .content{
    padding: 0 0 1rem 0;
    width: 75rem;
    margin: 0 auto;
  }
  .steps-content {
    /*border: 1px dashed #e9e9e9;*/
    border-radius: 6px;
    /*background-color: #fafafa;*/
    min-height: 200px;
    /*text-align: center;*/
    padding-top: 20px;
  }
  .steps-action {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 1.5rem;
    /*/deep/ .ant-btn {*/
    /*  margin-right: 20px;*/
    /*  &:last-child {*/
    /*     margin-right: 0;*/
    /*  }*/
    /*}*/
  }
  .steps-title {
    width: 100%;
    height: 135px;
    display: flex;
    margin-top: -25px;
    background: #fff url('./assets/ynyth/top.png') no-repeat center bottom;
  }
</style>
