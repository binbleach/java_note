<template>
  <div>
    <div class="common-title">
      <div class="icon-first" />
      <div class="icon-second" />
      <div class="title-main">
        技能培训意愿登记和培训信息推送
      </div>
    </div>
    <ta-form
        layout="horizontal"
        :form-layout="true"
        :auto-form-create="(form) => {this.fourthForm = form;}"
    >
      <ta-form-item
          label="培训意向工种"
          field-decorator-id="bdz003"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          :field-decorator-options="{
                          rules: [{ required: true, message: '意向培训工种项目不能为空' }],
                        }"
      >
        <ta-select show-search collection-type="bdz003"/>
      </ta-form-item>
      <ta-form-item
          label="意向培训等级"
          field-decorator-id="bdz009"
          :span="8"
          :label-col="{ span: 10 }"
          :wrapper-col="{ span: 14 }"
          :field-decorator-options="{
                          rules: [{ required: true, message: '意向培训等级不能为空' }],
                        }"
      >
        <ta-select show-search collection-type="bdz009"/>
      </ta-form-item>
    </ta-form>
  </div>
</template>

<script>

export default {
  name: 'fourthMatter',
  props: ['personData', 'fileList'],
  components: {},
  data () {
    return {
      aac001: '',
    }
  },
  methods: {
    saveData () {
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
