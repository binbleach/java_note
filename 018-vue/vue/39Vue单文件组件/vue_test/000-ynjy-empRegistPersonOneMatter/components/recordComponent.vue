<template>
  <div class="fit" :class="{ 'no-show-border': isDetails }">
    <div class="fit recordComponent" :class="{ 'details-window': isDetails }">
      <div v-if="!isDetails">
        <div style="text-align: center">
          <h2 style="font-weight: bold">
            登记失业人员就业帮扶一件事
          </h2>
        </div>
      </div>
      <div class="add-person-box">
        <div class="common-title">
          <div class="icon-first"/>
          <div class="icon-second"/>
          <div class="title-main">
            申请人基本信息
          </div>
        </div>
        <ta-form
            layout="horizontal"
            :form-layout="true"
            :auto-form-create="(form) => {
            this.personForm = form;
          }
          "
        >
          <ta-row>
            <ta-form-item
                v-show="false"
                label="aae642"
                field-decorator-id="aae642"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item
                v-show="false"
                label="人员编号"
                disabled
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                field-decorator-id="aac001"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item
                label="证件类型"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                disabled
                field-decorator-id="aac058"
                :field-decorator-options="{ rules: [{ required: true, message: '请选择证件类型' }] }"
            >
              <ta-select collection-type="AAC058"/>
            </ta-form-item>
            <ta-form-item
                label="证件号码"
                disabled
                field-decorator-id="aac147"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                :field-decorator-options="{ rules: [{ required: true, message: '请输入身份证号!' }] }"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item
                label="证件号码"
                disabled
                field-decorator-id="aac002"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                hidden="true"
                :field-decorator-options="{ rules: [{ required: true, message: '请输入身份证号!' }] }"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item
                label="姓名"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                disabled
                field-decorator-id="aac003"
                :field-decorator-options="{ rules: [{ required: true, message: '请输入姓名!' }] }"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item
                label="出生日期"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                disabled
                field-decorator-id="aac006"
                :field-decorator-options="{ rules: [{ required: true, message: '请选择出生日期' }] }"
            >
              <ta-date-picker style="width: 100%;"/>
            </ta-form-item>
            <ta-form-item
                label="性别"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                disabled
                field-decorator-id="aac004"
                :field-decorator-options="{ rules: [{ required: true, message: '请选择性别!' }] }"
            >
              <ta-select collection-type="AAC004"/>
            </ta-form-item>
            <ta-form-item
                label="民族"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                disabled
                field-decorator-id="aac005"
                :field-decorator-options="{ rules: [{ required: true, message: '请输入民族' }] }"
            >
              <ta-select collection-type="AAC005"/>
            </ta-form-item>
            <ta-form-item
                label="联系电话"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
                field-decorator-id="aac067"
                :field-decorator-options="{ rules: [{ required: true, message: '请输入联系电话' }] }"
            >
              <ta-input/>
            </ta-form-item>
            <ta-form-item :span="8" :label-col="{span:10}" :wrapper-col="{span:14}" label="学历" field-decorator-id="aac011" :field-decorator-options="{rules: [{required: true, message: '请输入学历' }]}">
              <ta-select collectionType="AAC011" allow-clear @change="fnAac011Change"/>
            </ta-form-item>
            <ta-form-item
                label="户籍所在地"
                field-decorator-id="aab301"
                :require="{ message: '请选择户籍地！' }"
                :span="16"
                :label-col="{ span: 5 }"
                :wrapper-col="{ span: 19 }"
            >
              <areaCascaderModal ref="areaCascaderModal1" @areaChange="areaChange" limit-floor="3"/>
            </ta-form-item>
            <ta-form-item
                label="户籍地详细地址"
                field-decorator-id="aac018"
                :require="{ message: '请输入户籍地详细地址！' }"
                :span="16"
                :label-col="{ span: 5 }"
                :wrapper-col="{ span: 19 }"
            >
              <ta-input placeholder="请输入门牌号信息，例如: XX组XX号。"/>
            </ta-form-item>
            <ta-form-item
                label="常住地"
                field-decorator-id="aac303"
                :require="{ message: '请选择常驻地！' }"
                :span="16"
                :label-col="{ span: 5 }"
                :wrapper-col="{ span: 19 }"
            >
              <areaCascaderModal ref="areaCascaderModal2" @areaChange="areaChange2" limit-floor="3"/>
            </ta-form-item>
            <ta-form-item
                label="常住地详细地址"
                field-decorator-id="aac041"
                :require="{ message: '请输入常住地详细地址！' }"
                :span="16"
                :label-col="{ span: 5 }"
                :wrapper-col="{ span: 19 }"
            >
              <ta-input placeholder="请输入门牌号信息，例如: XX组XX号。"/>
            </ta-form-item>
            <ta-form-item
                disabled
                v-show ="false"
                field-decorator-id="aae017"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
            >
              <ta-input/>
            </ta-form-item>
          </ta-row>
          <ta-row>
            <ta-form-item
                label="申请办理地点"
                field-decorator-id="bcc593"
                :require="{ message: '请选择申请办理地点！' }"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
            >
              <ta-radio-group name="radioGroup" @change="selectBcc593">
                <ta-radio value="1">
                  户籍地
                </ta-radio>
                <ta-radio value="2">
                  常住地
                </ta-radio>
              </ta-radio-group>
            </ta-form-item>
            <ta-form-item
                label="申报地行政区划"
                :disabled="true"
                field-decorator-id="bae217"
                :require="{ message: '选择申请办理地点自动生成！' }"
                :span="16"
                :label-col="{ span: 5 }"
                :wrapper-col="{ span: 19 }"
            >
              <areaCascaderModal placeholder="选择申请办理地点自动生成"/>
            </ta-form-item>
            <ta-form-item
                v-show="false"
                label="申报地址"
                field-decorator-id="bae218"
                :span="8"
                :label-col="{ span: 10 }"
                :wrapper-col="{ span: 14 }"
            >
              <ta-input/>
            </ta-form-item>
          </ta-row>
          <ta-row>
            <ta-form-item
                label="是否是高校毕业生"
                init-value="0"
                field-decorator-id="acc021"
                :span="8"
                :require="{message: '不能为空！'}"
                :label-col="{span:10}"
                :wrapper-col="{span:14}">
              <ta-radio-group name="radioGroup" @change="fnHighSchlStuChange">
                <ta-radio value="1">
                  是
                </ta-radio>
                <ta-radio value="0">
                  否
                </ta-radio>
              </ta-radio-group>
            </ta-form-item>
            <ta-form-item v-if="isHighSchlStu" :span="8" :label-col="{span:10}" :wrapper-col="{span:14}" label="学历编号证书" field-decorator-id="aac048" :field-decorator-options="{rules: [{required: true, message: '请输入学历编号证书' }]}">
              <ta-input @change="fnAac048Change"/>
            </ta-form-item>
            <ta-form-item v-if="isHighSchlStu" :span="8" :label-col="{span:10}" :wrapper-col="{span:14}" label="毕业院校" field-decorator-id="aac180" :field-decorator-options="{rules: [{required: true, message: '请输入毕业院校' }]}">
              <ta-input @change="fnAac180Change"/>
            </ta-form-item>
            <ta-form-item v-if="isHighSchlStu" :span="8" :label-col="{span:10}" :wrapper-col="{span:14}" label="毕业日期" field-decorator-id="aac046" :field-decorator-options="{rules: [{required: true, message: '请输入毕业日期' }]}">
              <ta-date-picker style="width: 100%" :disabled-date="fnDisabledDate" @change="fnAac046Change"/>
            </ta-form-item>
          </ta-row>
        </ta-form>
      </div>
      <div class="add-person-box" v-if="checkedList.includes('1')">
        <firstMatter ref="firstRef" :personData="personData" :fileList="fileList" @fnPrev="fnPrev"/>
      </div>
      <div class="add-person-box" v-if="checkedList.includes('2')">
      </div>
      <div class="add-person-box" v-if="checkedList.includes('3')">
        <thirdMatter ref="thirdRef" :personData="personData" :fileList="fileList" />
      </div>
      <div class="add-person-box" v-if="checkedList.includes('4')">
        <fourthMatter ref="fourthRef" :personData="personData" :fileList="fileList" />
      </div>
      <div class="add-person-box" v-if="checkedList.includes('5')">
        <fifthMatter ref="fifthRef" :personData="personData" :fileList="fileList" @fnPrev="fnPrev"/>
      </div>
      <div class="add-person-box" v-if="checkedList.includes('6')">
        <sixthMatter ref="sixthRef" :personData="personData" :fileList="fileList" @fnPrev="fnPrev"/>
      </div>
      <div class="add-person-box" v-if="checkedList.includes('7')">
        <seventhMatter ref="seventhRef" :personData="personData" :fileList="fileList" @fnPrev="fnPrev"/>
      </div>
      <div class="add-person-box" v-if="checkedList.includes('8')">
        <eighthMatter ref="eighthRef" :personData="personData" :fileList="fileList" @fnPrev="fnPrev"/>
      </div>
      <div v-if="!isDetails" style="text-align: center;margin-top: 1rem;">
<!--        <ta-button type="primary" :disabled="btnDisabled" @click="download">-->
<!--          下载申请表-->
<!--        </ta-button>-->
        <ta-button type="primary" :disabled="btnDisabled" @click="uploadDemo">
          上传材料
        </ta-button>
        <ta-button type="primary" :disabled="btnDisabled" @click="saveData('save')">
          提交
        </ta-button>
      </div>
      <!--材料上传组件-->
      <materials-upload
          ref="materials"
          :visible="listVisible"
          :on-serial="onSerial"
          :default-file-list="fileList"
          @onClose="listVisible = false"
          @change="fileChange"
      />
      <ta-modal
          title="保存成功"
          v-model="saveResultModelV"
          @ok="saveResultModelV = false"
          okText="确认"
          cancelText="取消"
      >
          <div v-for="(p,index) in saveResultArr" :key="index">
            <p v-if="p.errorMessage !== '' && p.errorMessage !== undefined">
              <span style="color:dodgerblue">{{ p.name }} </span>
              <span style="color:red">提交失败！</span>
              <span style="color:#767e87">原因：{{ p.errorMessage }}</span>
            </p>
          </div>
      </ta-modal>
      <!--评价-->
      <evaluateCom ref="evaluateCom" :visible.sync="evaVisible" :evaluate-data="evaluateData" />
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import areaCascaderModal from '@projectCommon/componentsYn/areaCascaderModal.vue'
import materialsUpload from '@projectCommon/componentsYn/materialsUpload.vue'
import firstMatter from './firstMatter'
import thirdMatter from './thirdMatter'
import fourthMatter from './fourthMatter'
import fifthMatter from './fifthMatter'
import sixthMatter from './sixthMatter'
import seventhMatter from './seventhMatter'
import eighthMatter from './eighthMatter'

export default {
  name: 'recordComponent',
  components: {
    areaCascaderModal,
    materialsUpload,
    firstMatter,
    thirdMatter,
    fourthMatter,
    fifthMatter,
    sixthMatter,
    seventhMatter,
    eighthMatter,
  },
  props: {
    fnDisabledPrev: {},
    currentPage: {},
    prev: {
      type: Function,
      require: false,
    },
    checkedList: {},
    isDetails: {
      type: Boolean,
      require: false,
      default: false,
    },
    rowData: {
      type: Object,
      require: false,
      default: () => {
        return {}
      },
    },
  },
  data () {
    return {
      disableAjc093: false,
      isHighSchlStu: false,
      savaDate: '',
      vShowAcc462: false,
      fieldAcc462: {},
      userInfo: {},
      tableData: [],
      dataSource: [],
      personData: {},
      hasSave: false,
      pauseSaveData: {},
      routerInfo: [
        {
          router: 'psnServMat',
          title: '个人服务',
        },
        {
          router: 'empDiffExit',
          title: '就业困难人员退出',
        }
      ],
      allFileInfo: [],
      fileType: '1',
      btnDisabled: false,
      showIdInput: false,
      showSchool: false,
      vShowBae096: false,
      fieldBae096: {},
      IdInputLabel: '',
      ycc136: '',
      yap523: '-1',
      loossWorkData: [],
      noLossWorkData: false,
      listVisible: false,
      onSerial: {
        ada120: 'JY801', // 事项编码
        aae400: '44', // 适用服务渠道
        aae535: '', // 服务场景
        aae557: '1', // 业务提交对象类型 1个人，2单位，3机构
      },
      fileList: [],
      saveResultArr: [
        {
          name: '就业登记',
          errorMessage: '',
        },
        {
          name: '失业登记',
          errorMessage: '提交过了所以失败了',
        }
      ],
      saveResultModelV: false,
      evaVisible: false,
      evaluateData: {
        aaa121: 'JY801',
        yap404: '0', // 类型 0个人 1单位
        orgtarget: '1',
        aae535: '',
        aae017: '9999',
        aab301: '',
        businessId: '',
      },
    }
  },
  mounted () {
    this.userInfo = this.Base.getUserInfo()
    this.getUserBase()
    this.evaluateData.aab301 = this.userInfo.aab301
  },
  watch: {
    currentPage (newValue) {
      if (newValue !== 1) {
        return
      }
      this.$nextTick(() => {
        setTimeout(() => {
          document.getElementById('aac067').focus()
        },1)
        if (this.checkedList.includes('1')) {
          // this.fnInitAlreadyHandleBizCheck('JY1031') // 在途校验
          this.$refs.firstRef.fnInitAlreadyHandleBizCheck(); // 在途校验包含其他速度较慢
          this.onSerial.aae535 = '2'
          this.$refs.firstRef.readOnlyAcc978 = false
          if (this.checkedList.includes('3') || this.checkedList.includes('4')) {
            this.$refs.firstRef.firstForm.setFieldsValue({ acc978: '1', acc312: '1', })
            this.$refs.firstRef.readOnlyAcc978 = true
          }
        }
        if (this.checkedList.includes('2')) {
          this.fnInitAlreadyHandleBizCheck('JY801_2') // 在途校验
        }
        if (this.checkedList.includes('3')) {
          this.fnInitAlreadyHandleBizCheck('JY1033') // 在途校验
        }
        if (this.checkedList.includes('4')) {
          this.fnInitAlreadyHandleBizCheck('JY801_4') // 在途校验
        }
        if (this.checkedList.includes('5')) {
          if (!this.checkedList.includes('1')) {
            this.onSerial.aae535 = '1'
            this.$refs.fifthRef.searchInformation() // 在途校验（包含是否失业登记校验）
          } else {
            this.onSerial.aae535 = '3'
            this.fnInitAlreadyHandleBizCheck('JY122') // 在途校验（不包含是否失业登记校验）
          }
        }
        if (this.checkedList.includes('6')) {
          this.$refs.sixthRef.fnInitAlreadyHandleBizCheck() // 在途校验
        }
        if (this.checkedList.includes('7')) {
          this.$refs.seventhRef.searchInformation() // 校验灵活就业登记信息
          this.$refs.seventhRef.getPersonInfo() // 校验灵活就业登记信息
          this.$refs.seventhRef.fnRefreshTable() // 刷新表格，固定样式
        }
        if (this.checkedList.includes('8')) {
          this.fnInitAlreadyHandleBizCheck('JY1032') // 在途校验
          this.$refs.eighthRef.getLossWorkInfo() // 校验失业登记信息
        }
        this.btnDisabled = false
      })
    },
  },
  methods: {
    fnPrev () {
      this.$emit("prev");
    },
    // 日期禁用
    fnDisabledDate (current) {
      // if (this.checkedList.includes('1')) {
      //   const firstValues = this.$refs.firstRef.getFormData()
      //   const ajc090 = firstValues.ajc090
      //   if (ajc090) {
      //     const aac046 = current && moment(current).format('YYYY-MM-DD')
      //     if (ajc090 <= aac046) {
      //       this.$message.error('毕业时间必须小于失业时间！')
      //       this.personForm.resetFields(['aac046'])
      //     }
      //   }
      // }
      // let endValue = this.personData.aac046
      // endValue = endValue && moment(moment(endValue).format('YYYY-MM-DD'), 'YYYY-MM-DD')
      // startValue = startValue && moment(moment(startValue).format('YYYY-MM-DD'), 'YYYY-MM-DD')
      // const ada302 = this.firstForm.getFieldValue('ada302')
      // if (!endValue || !startValue) {
      //   return startValue && startValue > moment().endOf('day')
      // } else if (ada302 === '1') {
      //   // 失业登记，小于当前大于毕业日期
      //   return startValue > moment().endOf('day') || startValue <= moment(endValue)
      // } else if (ada302 === '2') {
      //   // 求职登记，小于当前业日期
      //   return startValue > moment().endOf('day')
      // }
      if (current) {
        return current.format('YYYYMMDD') > (moment(new Date()).format('YYYYMMDD'))
      }
      return false
    },
    download () {
      const fileName = '云南省登记失业人员就业帮扶”一件事“申请表.docx'
      const params = {
        apiCode: 'templateDownload',
        fileName: fileName,
      }
      this.Base.businessQuery(params, (res) => {
        try {
          if (res.code === '-1') {
            this.$error({ content: res.message ? res.message : '网络错误，请联系管理员!', })
            return false
          } else {
            const base64 = res.base64File
            this.downloadBase64PDF(base64,fileName)
          }
        } catch (e) {
          this.$error({ content: '网络错误，请联系管理员!', })
        }
      })
    },
    downloadBase64PDF (base64Data, fileName) {
      const link = document.createElement('a')
      link.download = fileName
      link.href = `data:application/octet-stream;base64,${base64Data}`
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      this.$message.success('下载成功!')
    },
    // 是否高校毕业生
    fnHighSchlStuChange (e) {
      this.personData.acc021 = e.target.value
      if (e.target.value === '1') {
        this.isHighSchlStu = true
      } else if (e.target.value === '0') {
        this.personForm.resetFields(['aac046'])
        this.isHighSchlStu = false
      }
    },
    fnAac048Change (e) {
      this.personData.aac048 = e.target.value
    },
    fnAac180Change (e) {
      this.personData.aac180 = e.target.value
    },
    fnAac011Change (value) {
      this.personData.aac011 = value
    },
    fnAac046Change (value) {
      this.personData.aac046 = value
    },
    // 是否进城入镇
    fnGoIntoTownChange (value) {
      if (value === '1') {
        this.isGoIntoTown = true
      } else if (value === '0') {
        this.isGoIntoTown = false
      }
    },
    getUserBase () {
      const param = {
        apiCode: 'jy001QueryApi', // WT0000018
        aac001: this.userInfo.aac001,
      }
      this.Base.businessQuery(param, (res) => {
        console.log("jy001QueryApi===========",res.resultData.result)
        console.log("userInfo===========",this.userInfo)
        if (res.resultData.code === '-1') {
          this.personForm.resetFields()
          this.$message.error(res.data.msg)
          return false
        }
        // ac01人员基础信息
        this.personData = { ...res.resultData.result, }
        // 设置电话
        this.personData.aac067 = ''
        // 设置学历信息- 自选
        // if (this.personData.aac011) {
        //   this.personData.acc021 = '1'
        //   this.isHighSchlStu = true
        // }
        this.$nextTick(() => {
          // 设置表单
          this.personForm.setFieldsValue(this.personData)
          console.log("personData================",this.personData)
        });
      })
    },
    fnInitAlreadyHandleBizCheck (aae549) {
      const param1 = {}
      param1.apiCode = 'jy801_cancelUnempCheck'
      param1.aac001 = this.personData.aac001
      param1.aae549 = aae549
      this.Base.businessQuery(param1, (res) => {
        if (res.code === '-1') {
          const that = this
          Modal.warning({
            title: '提示消息',
            content: res.message,
            onOk () {
              that.$emit('prev')
            },
          })
        }
      })
    },
    areaChange (obj) {
      this.personForm.setFieldsValue({ aac018: obj.namepath.replace(/\//g, ''), })
      const areacode = obj.areacode
      if (this.currentPage !== 1 || areacode === undefined) { // 初始化赋值和handleReset组件重置不去校验
        return
      }
      if (areacode.substr(0, 2) === '53' && areacode.slice(-3) === '000') {
        this.personForm.resetFields('aac018')
        this.$refs.areaCascaderModal1.handleReset()
        this.$message.warning('户籍所在地为云南省，需要选择到社区级！！！！')
        return false
      }
    },
    areaChange2 (obj) {
      this.personForm.setFieldsValue({ aac041: obj.namepath.replace(/\//g, ''), })
      const areacode = obj.areacode
      if (this.currentPage !== 1 || areacode === undefined) { // 初始化赋值和handleReset组件重置不去校验
        return
      }
      if (areacode.substr(0, 2) === '53' && areacode.slice(-3) === '000') {
        this.personForm.resetFields('aac041')
        this.$refs.areaCascaderModal2.handleReset()
        this.$message.warning('常住地为云南省，需要选择到社区级！！！！')
        return false
      }
    },
    uploadDemo () {
      const userInfo = this.Base.getUserInfo()
      this.listVisible = true
      // 根据具体需求修改对象编号 对象类型 服务场景等
      this.onSerial.aae557 = '1'
      this.onSerial.aae558 = userInfo.aac001
    },
    // 材料上传返回处理 _obj材料对象 _type操作类型（add, delete）
    fileChange (_obj, _type) {
      // 删除
      if (_type === 'delete') {
        this.fileList.forEach((item, index) => {
          // 选择容缺材料时会返回一条aae707为空的数据（编辑也会返回） aae558与bae480用于确定对象删除
          if (item.aae707 === _obj.aae707 && item.aae558 === _obj.aae558 && item.bae480 === _obj.bae480) {
            this.fileList.splice(index, 1)
          }
        })
        return
      }
      // 新增
      this.fileList.push(_obj)
    },
    saveData (type) {
      // this.saveResultModelV = true
      // this.jy801_hcp11000()
      // this.evaVisible = true;
      // if (type === 'save') {
      //   return
      // }
      this.saveResultArr = []
      this.saveResultModelV = false
      const allMatterFormData = this.checkAllMatterForm()
      if (!allMatterFormData) {
        return;
      }
      const params = {
        apiCode: 'jy1031Api_emp06',
        handleItemCode: 'JY801',
        ...allMatterFormData,
        fileList: this.fileList,
      }
      const fileUploadList = this.$refs.materials.dataSource;
      // 处理没有aae535的情况
      fileUploadList.forEach(item => {
        if (!item.aae535) {
          const matchingFiles = this.fileList.filter(f => f.bae480 === item.bae480);
          matchingFiles.forEach(f => f.aae535 = '');
        }
      });
      // 检查必传项是否已上传
      const hasMissingRequired = fileUploadList.some(item =>
        item.aae536 === '1' && !this.fileList.some(f => f.bae480 === item.bae480)
      );
      if (hasMissingRequired || fileUploadList.length === 0) {
        this.$message.warn('请上传必传材料!')
        return;
      }
      params.fileListStr = JSON.stringify(this.fileList)
      params.aae071 = this.checkedList.join(",") // 事项编号
      params.aac010 = params.aac018 // 户籍地
      params.aae006 = params.aac041 // 常住地
      params.aac045 = params.aac180 // 毕业院校
      params.aac046 = params.aac046 ? moment(params.aac046).format('YYYY-MM-DD') : undefined // 毕业事件格式化
      // 事项推送
      this.Base.commonSave(params, (res) => {
        console.log("aaa==============",res)
        if (res.resultData.errMsg !== '') { // 测试
          this.$message.error(res.resultData.errMsg)
        } else {
          this.evaluateData.businessId = res.resultData.aae070 // 评价记录
          this.personData.aae070 = res.resultData.aae070 // 其他业务推送
          this.jy801_hcp11000()
          this.$message.success('保存成功！')
          this.btnDisabled = true
          this.$emit("fnDisabledPrev");
        }
      })
    },
    checkAllMatterForm () {
      const params = this.personForm.getFieldsValue()
      // 验证事项表单
      this.personForm.validateFields()
      const personError = this.personForm.getFieldsError()
      let firstError = {}
      let eighthError = {}
      let fourthError = {}
      let sixthError = {}
      let fifthError = {}
      let seventhError = {}
      if (this.checkedList.includes('1')) {
        this.$refs.firstRef.firstForm.validateFields()
        firstError = this.$refs.firstRef.firstForm.getFieldsError()
        const firstValues = this.$refs.firstRef.getFormData()
        params.ajc090 = firstValues.ajc090 // ae70保存需要
        params.firstMatterInfoStr = JSON.stringify(firstValues)
      }
      if (this.checkedList.includes('3')) {
        const jobIntent = this.$refs.thirdRef.jobIntent
        if (jobIntent.length === 0) {
          this.$message.warn('请添加求职意向!')
          return false
        }
        params.jobIntentStr = JSON.stringify(jobIntent)
      }
      if (this.checkedList.includes('4')) {
        this.$refs.fourthRef.fourthForm.validateFields()
        fourthError = this.$refs.fourthRef.fourthForm.getFieldsError()
        const fourthValues = this.$refs.fourthRef.fourthForm.getFieldsValue()
        Object.assign(params,fourthValues)
      }
      if (this.checkedList.includes('5')) {
        this.$refs.fifthRef.fifthForm.validateFields()
        fifthError = this.$refs.fifthRef.fifthForm.getFieldsError()
        const fifthValues = this.$refs.fifthRef.getFormData()
        params.adc310 = fifthValues.adc310 // ae70保存需要
        params.fifthMatterInfoStr = JSON.stringify(fifthValues)
      }
      if (this.checkedList.includes('6')) {
        this.$refs.sixthRef.sixthForm.validateFields()
        sixthError = this.$refs.sixthRef.sixthForm.getFieldsError()
        const sixthValues = this.$refs.sixthRef.getFormData()
        params.sixthMatterInfoStr = JSON.stringify(sixthValues)
        params.aac318 = sixthValues.aac318 // ae70保存需要
        params.aab004 = sixthValues.aab004
        params.aab998 = sixthValues.aab998
        params.acc335 = sixthValues.acc335
        params.bcc452 = sixthValues.bcc452
      }
      if (this.checkedList.includes('7')) {
        this.$refs.seventhRef.seventhForm.validateFields()
        seventhError = this.$refs.seventhRef.seventhForm.getFieldsError()
        const seventhValues = this.$refs.seventhRef.getFormData()
        if (!seventhValues) {
          return false
        }
        params.seventhMatterInfoStr = JSON.stringify(seventhValues)
      }
      if (this.checkedList.includes('8')) {
        this.$refs.eighthRef.eighthForm.validateFields()
        eighthError = this.$refs.eighthRef.eighthForm.getFieldsError()
        const eighthValues = this.$refs.eighthRef.eighthForm.getFieldsValue()
        Object.assign(params,eighthValues)
      }
      // 表单校验
      const allError = { ...personError, ...firstError, ...eighthError, ...sixthError, ...fifthError, ...seventhError, ...fourthError, }
      const isError = Object.keys(allError).some((key) => {
        if (allError[key] !== undefined) {
          this.$message.warn('必填未填!' + allError[key])
          document.getElementById(key).focus()
          return true
        }
        return false
      })
      if (isError) {
        return false
      }
      if (params.aab301 && params.aab301.substr(0, 2) === '53' && params.aab301.slice(-3) === '000') {
        this.$message.warning('户籍所在地为云南省，需要选择到社区级！！！！')
        return false
      }
      if (params.aac303 && params.aac303.substr(0, 2) === '53' && params.aac303.slice(-3) === '000') {
        this.$message.warning('常住地为云南省，需要选择到社区级！！！！')
        return false
      }
      return params;
    },
    jy801_hcp11000 () {
      const params = {
        aae070: this.personData.aae070,
        apiCode: 'jy801_hcp11000',
        handleItemCode: 'JY108',
      }
      this.Base.commonSave(params, (res) => {
        if (res.code === '-1') {
          this.$error({ content: res.message ? res.message : '网络错误，请联系管理员!', })
        } else {
          this.evaVisible = true;
        }
      })
    },
    selectBcc593 (val) {
      if (val.target.value === '1') {
        // 申报地为户籍地
        const aab301 = this.personForm.getFieldValue('aab301')
        const aac018 = this.personForm.getFieldValue('aac018')
        this.changeBae217(val,aab301,aac018)
      } else if (val.target.value === '2') {
        // 申报地为常住地
        const aac303 = this.personForm.getFieldValue('aac303')
        const aac041 = this.personForm.getFieldValue('aac041')
        this.changeBae217(val,aac303,aac041)
      }
    },
    changeBae217 (val,bae217,bae218) {
      // 申报地为户籍地
      if (!bae217) {
        this.personForm.resetFields(['bcc593','bae217','bae218'])
        val.target.value = ''
        this.$message.warning('请先填写户籍地行政区划!')
        return
      }
      if (!bae218) {
        this.personForm.resetFields(['bcc593','bae217','bae218'])
        val.target.value = ''
        this.$message.warning('请先填写户籍地址!')
        return
      }
      // 申报地
      if (bae217.substring(0, 2) !== '53') {
        this.personForm.resetFields(['bcc593','bae217','bae218'])
        val.target.value = ''
        this.$message.warn('申报地请选择云南省地区!')
        return
      }
      this.personForm.setFieldsValue({ bae217, bae218, })
      this.personData.bae217 = bae217
      this.personData.bae218 = bae218
      this.personData.bcc593 = val.target.value
    },
  },
}
</script>

<style scoped type="text/less" >

  .no-show-border {
    border: none;
  }

  .recordComponent {
    padding: 0.875rem;
    width: 75rem;
    margin: 0 auto;
  }

  .details-window {
    width: 100%;
  }

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

  ::v-deep .ant-input[disabled] {
    color: rgb(0 0 0);
  }
  ::v-deep .ant-select-selection--single .ant-select-selection__rendered {
    color: rgb(0 0 0);
  }
</style>
