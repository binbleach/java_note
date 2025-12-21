<template>
  <div>
    <div class="common-title">
      <div class="icon-first" />
      <div class="icon-second" />
      <div class="title-main">
        就业创业扶持政策申请和受理（就业困难人员灵活就业社会保险补贴申领）
      </div>
    </div>
    <ta-form
      layout="horizontal"
      :form-layout="true"
      :auto-form-create="(form) => {this.seventhForm = form;}"
    >
      <ta-form-item
        label="开户银行"
        :label-col="{span:10}"
        :wrapper-col="{span:14}"
        :span="8"
        field-decorator-id="aaf200"
        :require="{ message: '请输入开户行！' }"
        :disabled="disabledAaf200"
      >
        <ta-select
          :disabled="true"
          allow-clear
          show-search
          collection-type="aaf200"
          option-label-prop="label"
        />
      </ta-form-item>
      <ta-form-item
        label="银行户名"
        :label-col="{span:10}"
        :wrapper-col="{span:14}"
        :span="8"
        field-decorator-id="aae009"
        :require="{ message: '请输入户名！' }"
        :disabled="disabledAae009"
      >
        <ta-input :disabled="true" />
      </ta-form-item>

      <ta-form-item
        label="银行账号"
        :label-col="{span:10}"
        :wrapper-col="{span:14}"
        :span="8"
        field-decorator-id="aae010"
        :require="{ message: '请输入账号！' }"
        :disabled="disabledAae010"
      >
        <ta-input :max-length="25" :disabled="true" />
      </ta-form-item>
      <ta-form-item
          label="灵活就业登记时间"
          :require="{message: '不能为空！'}"
          field-decorator-id="ycc039"
          :label-col="{span:10}"
          :wrapper-col="{span:14}"
          :span="8"
      >
        <ta-date-picker style="width: 100%" placeholder="" allow-clear disabled format="YYYY-MM-DD"/>
      </ta-form-item>

      <ta-form-item
          label="灵活就业时间"
          v-show="false"
          field-decorator-id="acc114"
          :label-col="{span:10}"
          :wrapper-col="{span:14}"
          :span="8"
      >
        <ta-date-picker style="width: 100%" placeholder="" allow-clear disabled format="YYYY-MM-DD"/>
      </ta-form-item>
      <ta-form-item
          label="灵活就业注销时间"
          v-show="false"
          field-decorator-id="bae431"
          :label-col="{span:10}"
          :wrapper-col="{span:14}"
          :span="8"
      >
        <ta-date-picker style="width: 100%" placeholder="" allow-clear disabled format="YYYY-MM-DD"/>
      </ta-form-item>
    </ta-form>
    <div style="display:flex;height:200px;width: 100%">
      <div :bordered="false" style="width: 50%;height:250px; position: relative">
        <span slot="title" style="margin-left: 10px">
          <span class="icon-first" />
          <span class="icon-second" />
          <span>灵活就业信息</span>
        </span>
        <ta-card :bordered="false">
          <ta-big-table
            ref="empTable"
            size="mini"
            :data="empTableData"
            align="center"
            border
            height="200"
            @radio-change="selectRadio"
          >
            <ta-big-table-column type="seq" width="60" title="序号" />
            <ta-big-table-column type="radio" width="40" />
            <ta-big-table-column
              field="aac318"
              title="就业形式"
              width="120"
              collection-type="aac318"
            />
            <ta-big-table-column field="acc114" width="100" title="就业日期" />
            <ta-big-table-column field="bae431" width="100" title="注销时间" />
            <ta-big-table-column
              field="aae100"
              title="是否有效"
              width="90"
              collection-type="aae100"
            />
            <ta-big-table-column field="bae437" title="经办机构" />
            <ta-big-table-column
              field="acc030"
              title="就业登记主键"
              :visible="false"
            />
          </ta-big-table>
        </ta-card>
      </div>
      <div :bordered="false" style="width: 50%;height:250px; position: relative">
        <span slot="title" style="margin-left: 10px">
          <span class="icon-first" />
          <span class="icon-second" />
          <span>就业困难信息</span>
        </span>
        <ta-card :bordered="false">
          <ta-big-table
            ref="difficultFindJobTable"
            size="mini"
            :data="difficultFindJobVOS"
            align="center"
            border
            height="200"
          >
            <ta-big-table-column type="seq" width="80" title="序号" />
            <ta-big-table-column
              field="adc310"
              title="人员类别"
              collection-type="adc310"
            />
            <ta-big-table-column field="acc361" title="认定时间" sortable />
            <ta-big-table-column
              field="acc359"
              title="状态"
              collection-type="acc359"
              width="80"
            />
            <ta-big-table-column
              field="aae017desc"
              title="认定机构"
            />
            <ta-big-table-column field="aac0c4" width="120" title="退出时间" />
            <ta-big-table-column
              field="acc130"
              title="就业登记主键"
              :visible="false"
            />
          </ta-big-table>
        </ta-card>
      </div>
    </div>
    <div style="text-align: center; margin-top: 80px">
      <ta-button type="primary" @click="showAtModal">
        养老缴费
      </ta-button>
      <ta-button type="primary" @click="showMdModal">
        医疗缴费
      </ta-button>
    </div>
    <ta-card :bordered="false">
      <ta-tabs>
        <ta-tab-pane tab="人员补贴信息">
          <ta-big-table
            ref="sbTotlTableRef"
            border
            size="mini"
            align="center"
            resizable
            height="200"
            :data="sbTotlTable"
            show-overflow
          >
            <ta-big-table-column type="seq" width="100" title="序号" fixed="left" />
            <ta-big-table-column fixed="left" field="operate" title="操作" width="200">
              <template #default="rowInfo">
                <ta-table-operate :operate-menu="operateMenu" :row-info="rowInfo" />
              </template>
            </ta-big-table-column>

            <ta-big-table-column
              field="aac001"
              title="个人编号"
              width="140"
              :visible="false"
            />

            <ta-big-table-column field="aac003" width="140" title="姓名" />
            <ta-big-table-column
              field="aac147"
              title="身份证号码"
            />
            <ta-big-table-column
              field="aae140"
              title="险种"
              collection-type="aae140"
            />
            <ta-big-table-column
              field="bdc521"
              title="补贴月份"
            />
          </ta-big-table>
        </ta-tab-pane>
      </ta-tabs>
    </ta-card>
    <ta-modal v-model="atvisible" title="养老保险" height="700px" width="1200px" @ok="handleAtOk">
      <ta-alert message="补贴月份应大于等于所选择的灵活就业信息的灵活就业时间，小于等于所选择的灵活就业信息的就业注销日期" type="info" show-icon />
      <ta-form
        ref="xzform"
        layout="horizontal"
        :form-layout="true"
        label-width="100px"
        :auto-form-create="
          (form) => {
            this.xzform = form;
          }
        "
      >
        <ta-card :bordered="false" class="fit">
          <ta-form-item
            label="缴费开始年月"
            field-decorator-id="aae030"
            label-width="130px"
            :span="8"
            :required="true"
          >
            <ta-month-picker
              :allow-input="true"
              :require="true"
              placeholder="日期选择可以输入"
              :disabled-date="disabledDate"
              @change="changeAae030"
            />
          </ta-form-item>
          <ta-form-item
            label="缴费结束年月"
            field-decorator-id="aae031"
            label-width="130px"
            :span="8"
            :required="true"
          >
            <ta-month-picker :allow-input="true" :disabled-date="disabledDate" placeholder="日期选择可以输入" @change="changeAae031" />
          </ta-form-item>
          <ta-form-item
            label="补贴月数"
            field-decorator-id="bdc505"
            label-width="130px"
            :span="8"
            disabled
            :required="true"
          >
            <ta-input />
          </ta-form-item>
          <ta-form-item
            label=""
            :span="11"
          />
          <ta-button type="primary" @click="addylaobt()">
            新增补贴
          </ta-button>
          <ta-button type="primary" @click="resetYanglaoData()">
            重置
          </ta-button>
        </ta-card>
      </ta-form>
    </ta-modal>

    <ta-modal v-model="mdvisible" title="医疗保险" height="700px" width="1200px" @ok="handleMdOk">
      <ta-alert message="补贴月份应大于等于所选择的灵活就业信息的灵活就业时间，小于等于所选择的灵活就业信息的就业注销日期" type="info" show-icon />
      <ta-form
        ref="tcylform"
        layout="horizontal"
        :form-layout="true"
        label-width="100px"
        :auto-form-create="
          (form) => {
            this.tcylform = form;
          }
        "
      >
        <ta-card :bordered="false" class="fit">
          <ta-form-item
            label="缴费开始年月"
            label-width="130px"
            :span="8"
            field-decorator-id="aae030_yl"
            :required="true"
          >
            <ta-month-picker
              :allow-input="true"
              :require="true"
              placeholder="日期选择可以输入"
              :disabled-date="disabledDate"
              @change="changeAae030yl"
            />
          </ta-form-item>
          <ta-form-item
            label="缴费结束年月"
            label-width="130px"
            :span="8"
            field-decorator-id="aae031_yl"
            :required="true"
          >
            <ta-month-picker :allow-input="true" :disabled-date="disabledDate" placeholder="日期选择可以输入" @change="changeAae031yl" />
          </ta-form-item>
          <ta-form-item
            label="补贴月数"
            field-decorator-id="bdc505_yl"
            label-width="130px"
            :span="8"
            disabled
            :required="true"
          >
            <ta-input />
          </ta-form-item>
          <ta-form-item
            label=""
            :span="11"
          />

          <ta-button type="primary" @click="addyliaobt">
            新增补贴
          </ta-button>
          <ta-button type="primary" @click="resetYiliaoData">
            重置
          </ta-button>
        </ta-card>
      </ta-form>
    </ta-modal>
  </div>
</template>

<script>

import moment from 'moment'
import { isNull, } from '@/corePage/empDiffFlxempeSiSubsAppy/datautil'

export default {
  name: 'seventhMatter',
  props: ['personData', 'fileList', 'fnPrev'],
  data () {
    return {
      aac001: '',
      aac003: '',
      aac147: '',
      empTableData: [], // 灵活就业信息
      sbTotlTable: [], // 人员补录
      difficultFindJobVOS: [], // 就业困难信息
      mdaae140: '',
      disabledAaf200: true,
      disabledAae009: true,
      disabledAae010: true,
      atvisible: false,
      mdvisible: false,
      sbTable: [],
      operateMenu: [
        {
          name: '删除',
          icon: 'delete',
          type: 'confirm',
          confirmTitle: '确认删除该信息？',
          onOk: (record, index) => {
            const deleteArr = []
            this.sbTable.forEach((item, index) => {
              if (record.aac001 === item.aac001) {
                deleteArr.push(item)
              }
            })
            deleteArr.forEach((item, index) => {
              this.sbTable.forEach((item, index) => {
                if (record.aac001 === item.aac001) {
                  this.sbTable.splice(index, 1)
                }
              })
            })
            this.sbTotlTable.splice(index, 1)
          },
        }
      ],
    }
  },
  methods: {
    saveData () {
      const _fileList = []
      this.fileList.forEach((item, index) => {
        const _item = { ...item, }
        // 选择容缺材料时会返回一条aae707为空的数据（编辑也会返回） aae558与bae480用于确定对象删除
        if (_item.aae535 === '') {
          _item.ada120 = 'JY123'
          _fileList.push(_item)
        }
      })
      let bae065 = {}
      const personData = this.personData
      const seventhForm = this.seventhForm.getFieldsValue()
      personData.aaf200 = seventhForm.aaf200
      personData.aae009 = seventhForm.aae009
      personData.aae010 = seventhForm.aae010
      const totleList = this.sbTotlTable
      const deailList = this.sbTable
      const empList = this.empTableData
      const difficutList = this.difficultFindJobVOS
      personData.aac046 = personData.aac046 && moment(personData.aac046).format('YYYY-MM-DD')
      if (totleList.length <= 0) {
        this.$message.warn('没有可以保存的数据！')
        return false
      }
      this.checkSubmitBefor(personData)
      bae065 = personData
      bae065.deailList = deailList
      bae065.bankInfo = seventhForm
      bae065.totleList = totleList
      bae065.empList = empList
      bae065.difficutList = difficutList
      bae065.fileList = _fileList
      if (this.submitBeforMark) {
        return false
      }
      const params = {
        apiCode: 'jy123_emp02',
        aae557: '1',
        aae558: personData.aac001,
        aae559: personData.aac003,
        aae903: personData.aac147,
        aae549: 'JY123',
        aac001: personData.aac001,
        aac147: personData.aac147,
        aac003: personData.aac003,
        aaf200: personData.aaf200,
        aae009: personData.aae009,
        aae010: personData.aae010,
        aab301: personData.bae217, // 原aab301
        bdc450: '1',
        aae642: personData.aae642,
        aae643: '1', // 是否存在经办审核
        aae400: '44',
        adc450: '025',
        handleItemCode: 'JY123',
        bae065: JSON.stringify(bae065),
        fileList: JSON.stringify(_fileList),
        baz005: '1',
        aae070: personData.aae070, // 失业登记帮扶一件事主键
        aae071: '7',
      }
      this.Base.commonSave(params, (res) => {
        console.log("res==============",res)
        const result = {}
        result.name = '就业创业扶持政策申请和受理'
        if (res.resultData.code === '-1') {
          result.errorMessage = res.resultData.message
          if (result.errorMessage === undefined || result.errorMessage === '') {
            result.errorMessage = '-1'
          }
          console.log('jy123_emp02',result)
        }
        this.$emit("saveDataAfter",result);
      })
    },
    // 别删父组件用到
    getFormData () {
      const totleList = this.sbTotlTable
      if (totleList.length <= 0) {
        this.$message.warn('请录入养老缴费或医疗缴费信息！')
        return false
      }
      const seventhValues = this.seventhForm.getFieldsValue()
      seventhValues.deailList = this.sbTable
      const bankInfo = {
        aaf200: seventhValues.aaf200,
        aae009: seventhValues.aae009,
        aae010: seventhValues.aae010,
      }
      seventhValues.bankInfo = bankInfo
      seventhValues.totleList = totleList
      seventhValues.empList = this.empTableData
      seventhValues.difficutList = this.difficultFindJobVOS
      return seventhValues
    },
    fnRefreshTable () {
      if (this.empTableData.length === 0) {
        this.empTableData = [{ aac318: '', }]
        this.empTableData = []
      }
      if (this.difficultFindJobVOS.length === 0) {
        this.difficultFindJobVOS = [{ adc310: '', }]
        this.difficultFindJobVOS = []
      }
      if (this.sbTotlTable.length === 0) {
        this.sbTotlTable = [{ aac001: '', }]
        this.sbTotlTable = []
      }
    },
    getPersonInfo () {
      const userInfo = this.Base.getUserInfo()
      // 查询个人信息
      const param = {}
      param.apiCode = 'jy123_emp01'
      param.aac001 = userInfo.aac001
      this.Base.businessQuery(param, (res) => {
        if (res.resultData.code === '1' && res.resultData.result) {
          const baseData = { ...res.resultData.result.ac01PO, }
          const bankInfo = { ...res.resultData.result.bankInfo, }
          baseData.aae005 = baseData.aac067
          if (!bankInfo.aaf200) {
            // this.$message.error('单位银行信息不能为空，请先进行银行信息维护！');
            this.disabledAaf200 = false
            this.disabledAae009 = false
            this.disabledAae010 = false
          }
          // 学历
          // if (baseData.aac011 === '' || bankInfo.aac011 === null) {
          //   this.disabledAac011 = false
          // }
          // this.enjoyObj = { ...res.resultData.result.enjoyInfoVO, }
          this.empTableData = res.resultData.result.empRegVos
          this.difficultFindJobVOS = res.resultData.result.difficultFindJobVOS

          // this.currAreaIdPath = res.resultData.result.areaIdPath
          // baseData.aab301 = res.resultData.result.aab301
          // this.seventhForm.setFieldsValue(baseData)
          this.aac001 = baseData.aac001
          this.aac147 = baseData.aac147
          this.aac003 = baseData.aac003
          this.seventhForm.setFieldsValue(bankInfo)
          this.$refs.empTable.setRadioRow(this.empTableData[0])
          this.selectRadio({ row: this.empTableData[0], })
          if (baseData.aae005) {
            this.disabledAae005 = true
          }
        } else {
          const that = this
          Modal.warning({
            title: '提示消息',
            content: res.resultData.message,
            onOk () {
              that.$emit('fnPrev')
              // that.$router.push('empRegistPersonOneMatter')
            },
          })
        }
      })
    },
    searchInformation () {
      // 查询暂存信息
      const param1 = {}
      param1.apiCode = 'jy123_emp03'
      param1.aac001 = this.personData.aac001
      this.Base.businessQuery(param1, (res) => {
        if (res.resultData.code === '-1') {
          const that = this
          Modal.warning({
            title: '提示消息',
            content: res.resultData.message,
            onOk () {
              that.$emit('fnPrev')
            },
          })
        }
      })
    },
    checkSubmitBefor (fmdata) {
      this.submitBeforMark = false
      if (isNull(fmdata.aaf200)) {
        this.$message.warn('开户银行不能为空！')
        this.submitBeforMark = true
      }
      if (isNull(fmdata.aae009)) {
        this.$message.warn('户名！')
        this.submitBeforMark = true
      }
      if (isNull(fmdata.aae010)) {
        this.$message.warn('银行账号！')
        this.submitBeforMark = true
      }
    },
    showAtModal () {
      if (this.empTableData.length === 0) {
        this.$message.info('没有获取到就业登记信息！')
        return false
      }
      this.atvisible = true
      this.mdaae140 = '110'
    },
    showMdModal () {
      if (this.empTableData.length === 0) {
        this.$message.info('没有获取到就业登记信息！')
        return false
      }
      this.mdvisible = true
      this.mdaae140 = '310'
    },
    handleAtOk (e) {
      this.atvisible = false
    },
    handleMdOk () {
      this.mdvisible = false
    },
    resetYiliaoData () {
      this.tcylform.resetFields()
    },
    resetYanglaoData () {
      this.xzform.resetFields()
    },
    addylaobt () {
      const aae030 = this.xzform.getFieldValue('aae030')
      const aae031 = this.xzform.getFieldValue('aae031')
      if (isNull(aae030)) {
        this.$message.info('开始时间不能为空！')
        return false
      }
      if (isNull(aae031)) {
        this.$message.info('结束时间不能为空！')
        return false
      }
      const bdc521s = this.getAllMonthsBetween(aae030, aae031)
      for (let i = 0; i < bdc521s.length; i++) {
        const sbTotl = {}
        let flag = 0
        sbTotl.aac001 = this.aac001
        sbTotl.aac003 = this.aac003
        sbTotl.aac147 = this.aac147
        sbTotl.aae140 = this.mdaae140
        // sbTotl.aae031 = this.tcylform.getFieldValue('aae031_yl')
        // sbTotl.aae030 = this.tcylform.getFieldValue('aae030_yl')
        sbTotl.bdc521 = bdc521s[i]
        for (const i in this.sbTotlTable) {
          if (this.sbTotlTable[i].aae140 === sbTotl.aae140 && this.sbTotlTable[i].bdc521 === sbTotl.bdc521) {
            console.log(this.sbTotlTable[i])
            flag = 1
          }
        }
        if (flag === 0) {
          this.sbTotlTable.push(sbTotl)
        }
      }
      this.xzform.resetFields()
      this.$message.success('新增成功！')
      this.atvisible = false
    },
    addyliaobt () {
      const aae030 = this.tcylform.getFieldValue('aae030_yl')
      const aae031 = this.tcylform.getFieldValue('aae031_yl')
      if (isNull(aae030)) {
        this.$message.info('开始时间不能为空！')
        return false
      }
      if (isNull(aae031)) {
        this.$message.info('结束时间不能为空！')
        return false
      }
      const bdc521s = this.getAllMonthsBetween(aae030, aae031)
      for (let i = 0; i < bdc521s.length; i++) {
        const sbTotl = {}
        let flag = 0
        sbTotl.aac001 = this.aac001
        sbTotl.aac003 = this.aac003
        sbTotl.aac147 = this.aac147
        sbTotl.aae140 = this.mdaae140
        sbTotl.bdc521 = bdc521s[i]
        for (const i in this.sbTotlTable) {
          if (this.sbTotlTable[i].aae140 === sbTotl.aae140 && this.sbTotlTable[i].bdc521 === sbTotl.bdc521) {
            console.log(this.sbTotlTable[i])
            flag = 1
          }
        }
        if (flag === 0) {
          this.sbTotlTable.push(sbTotl)
        }
      }
      this.tcylform.resetFields()
      this.$message.success('新增成功！')
      this.mdvisible = false
    },
    getAllMonthsBetween (startDate, endDate) {
      const months = []
      const currentDate = new Date(startDate)
      let start = new Date(moment(currentDate).format('yyyy-MM') + '-01')
      console.log(moment(currentDate).format('yyyy-MM') + '-01')
      const endDateObj = new Date(endDate)
      console.log(moment(endDateObj).format('yyyy-MM') + '-01')
      const end = new Date(moment(endDateObj).format('yyyy-MM') + '-01')
      while (start <= end) {
        months.push(moment(start).format('yyyyMM'))
        start.setMonth(start.getMonth() + 1)
        let startNew = start;
        start = startNew;
      }
      return months
    },
    selectRadio ({ row, }) {
      console.log("hahahai")
      console.log(row)
      const ycc039 = row.aae043
      const acc114 = row.acc114
      const bae431 = row.bae431
      this.seventhForm.setFieldsValue({
        ycc039: ycc039,
        acc114: acc114,
        bae431: bae431,
      })
      this.$message.info('就业登记信息变更后，请重新录入补贴信息')
      this.sbTotlTable = []
    },
    changeAae030 (value) {
      let aae031 = this.xzform.getFieldValue('aae031')
      if (aae031 && value) {
        aae031 = aae031.toDate()
        value = value.toDate()
        if (aae031 < value) {
          this.$message.error('缴费开始年月不能大于缴费结束年月！')
          this.xzform.setFieldsMomentValue({ aae030: null, })
          return
        }
        this.xzform.setFieldsValue({ bdc505: parseInt((aae031.getFullYear() - value.getFullYear()) * 12 + (aae031.getMonth() - value.getMonth()) + 1), })
      }
    },
    changeAae031 (value) {
      let aae030 = this.xzform.getFieldValue('aae030')
      if (aae030 && value) {
        aae030 = aae030.toDate()
        value = value.toDate()
        if (aae030 > value) {
          this.$message.error('缴费开始年月不能大于缴费结束年月！')
          this.xzform.setFieldsMomentValue({ aae031: null, })
          return
        }
        this.xzform.setFieldsValue({ bdc505: parseInt((value.getFullYear() - aae030.getFullYear()) * 12 + (value.getMonth() - aae030.getMonth()) + 1), })
      }
    },
    changeAae030yl (value) {
      let aae031 = this.tcylform.getFieldValue('aae031_yl')
      console.log((aae031 && value))
      if (aae031 && value) {
        aae031 = aae031.toDate()
        value = value.toDate()
        if (aae031 < value) {
          this.$message.error('缴费开始年月不能大于缴费结束年月！')
          this.tcylform.setFieldsMomentValue({ aae030_yl: null, })
          return
        }
        this.tcylform.setFieldsValue({ bdc505_yl: parseInt((aae031.getFullYear() - value.getFullYear()) * 12 + (aae031.getMonth() - value.getMonth()) + 1), })
      }
    },
    changeAae031yl (value) {
      let aae030 = this.tcylform.getFieldValue('aae030_yl')
      if (aae030 && value) {
        aae030 = aae030.toDate()
        value = value.toDate()
        if (aae030 > value) {
          this.$message.error('缴费开始年月不能大于缴费结束年月！')
          this.tcylform.setFieldsMomentValue({ aae031_yl: null, })
          return
        }
        this.tcylform.setFieldsValue({ bdc505_yl: parseInt((value.getFullYear() - aae030.getFullYear()) * 12 + (value.getMonth() - aae030.getMonth()) + 1), })
      }
    },
    disabledDate (current) {
      // 不能选择今天以后的日期
      let acc114 = this.seventhForm.getFieldValue('acc114')
      const bae431 = this.seventhForm.getFieldValue('bae431')
      if (acc114) {
        acc114 = moment(acc114).startOf('month').toDate()
        acc114 = moment(acc114).subtract(1, 'days').toDate()
        if (current < moment(acc114).startOf('moth')) {
          return true
        }
      }
      if (bae431) {
        if (current > moment(bae431).startOf('moth')) {
          return true
        }
      }
      return false
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
