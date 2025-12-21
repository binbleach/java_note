<template>
  <div>
    <div class="common-title">
      <div class="icon-first" />
      <div class="icon-second" />
      <div class="title-main">
        就业服务（岗位推荐）
      </div>
    </div>
    <ta-form
      layout="horizontal"
      :form-layout="true"
      :auto-form-create="(form) => {this.thirdForm = form;}"
    >
      <div>
        <div class="r_title">
          <span>求职意向</span>
        </div>
        <div>
          <ta-card v-for="(item, index) in jobIntent" :key="index" class="top">
            <ta-row>
              <ta-col :span="1" >
                <img
                  :src="getRealImgUrl('img/userCenter/delete.png')"
                  alt=""
                  title="点击删除"
                  @click="deleteJobIntent(index)"
                />
              </ta-col>
              <ta-col :span="6" >意向职位：{{ item.aca112 }}</ta-col>
              <ta-col :span="6" >期望薪资：{{ CollectionLabel('ADE85F', item.acc034) }}</ta-col>
              <ta-col :span="9">期望工作区域：{{ item.acb202 ? item.acb202.replace(new RegExp('/', 'g'), '') : '未知' }}</ta-col>
            </ta-row>
          </ta-card>
          <ta-card v-show="jobIntent.length < 3">
              <ta-form-item label="求职意向主键" field-decorator-id="acc210" hidden="true">
                <ta-input />
              </ta-form-item>
              <ta-form-item
                  label="意向职位"
                  field-decorator-id="aca111"
                  :label-col="{span:10}"
                  :wrapper-col="{span:14}"
                  :span="6"
                  :field-decorator-options="{
                          rules: [{ required: true, message: '意向职位不能为空' }],
                        }"
              >
                <work-type-select @getValueDsc="setAca112"/>
              </ta-form-item>
              <ta-form-item label="意向职位" field-decorator-id="aca112" hidden="true">
                <ta-input />
              </ta-form-item>
              <ta-form-item
                  label="期望薪资"
                  field-decorator-id="acc034"
                  :label-col="{span:10}"
                  :wrapper-col="{span:14}"
                  :span="6"
                  :field-decorator-options="{
                          rules: [{ required: true, message: '期望薪资不能为空' }],
                        }"
              >
                <ta-select
                    allow-clear
                    placeholder="请选择期望薪资"
                    :options="acc034Options"
                    style="width: 100%"
                />
              </ta-form-item>

              <ta-form-item
                  label="期望工作区域"
                  field-decorator-id="areaid"
                  :label-col="{span:9}"
                  :wrapper-col="{span:15}"
                  :span="8"
                  :field-decorator-options="{
                          rules: [{ required: true, message: '期望工作区域不能为空' }],
                        }"
              >
                <areaCascaderModal @areaChange="getWorkArea"/>
              </ta-form-item>
              <ta-form-item label="工作区域" field-decorator-id="acb202" hidden="true">
                <ta-input />
              </ta-form-item>
              <ta-form-item
                  style="padding-left: 28px"
                  :span="3"
              >
                <ta-button type="primary" style="font-size: 20px" @click="addJobIntent"> 新增该条求职意向 </ta-button>
              </ta-form-item>
          </ta-card>
        </div>
      </div>
    </ta-form>
  </div>
</template>

<script>

import workTypeSelect from '@projectCommon/componentsYn/workTypeSelect.vue'
import areaCascaderModal from '@projectCommon/componentsYn/areaCascaderModal.vue'

export default {
  name: 'thirdMatter',
  components: { workTypeSelect, areaCascaderModal, },
  props: ['personData', 'fileList'],
  data () {
    return {
      jobIntent: [],
      acc034Options: [],
    }
  },
  mounted () {
    this.Base.asyncGetCodeData('ADE85F').then((codeList) => {
      codeList.sort((obj1, obj2) => obj1.sort - obj2.sort)
      this.acc034Options = codeList
    })
  },
  methods: {
    saveData () {
    },
    setAca112 (v) {
      this.thirdForm.setFieldsValue({
        aca112: v,
      })
    },
    // 新增求职意向
    addJobIntent () {
      const intentMesg = this.thirdForm.getFieldsValue([
        'acc210',
        'aca111',
        'aca112',
        'acc034',
        'areaid',
        'acb202'
      ])
      intentMesg.aab301 = intentMesg.areaid
      if (intentMesg.aca111 === undefined) {
        this.$message.warn('意向职位不能为空')
        return
      }
      if (intentMesg.acc034 === undefined) {
        this.$message.warn('期望薪资不能为空')
        return
      }
      if (intentMesg.aab301 === undefined) {
        this.$message.warn('期望工作区域不能为空')
        return
      }
      console.log("addJobIntent",intentMesg)
      this.jobIntent.push(intentMesg)
      this.thirdForm.resetFields(['acc210', 'aca111', 'aca112', 'acc034', 'areaid', 'acb202'])
      // this.saveResume()
    },
    // 获取期望工作区域
    getWorkArea (option) {
      // this.aab301 = option.areaid
      this.thirdForm.setFieldsValue({ acb202: option.namepath, })
    },
    // 求职意向-职位类型赋值
    getValueToIntent (obj) {
      // this.intentAca112 = obj.label;
      this.thirdForm.setFieldsValue({ aca112: obj.label.replace(/\(0\)$/, ''), aca111: obj.value, })
    },
    // 删除求职意向
    deleteJobIntent (index) {
      this.jobIntent.splice(index, 1)
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
  .r_title {
    font-size: 16px;
    font-weight: bold;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>
