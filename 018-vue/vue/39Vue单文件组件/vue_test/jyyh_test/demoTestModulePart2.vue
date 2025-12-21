<template>
  <div style="height: 100%">
    <div class="fit" style="margin:20px;">
      <ta-row>
        <ta-col :span="15"><ta-button @click="showModal">新增</ta-button></ta-col>
        <ta-col :span="2">
          <ta-radio-group @change="showList" defaultValue="1" buttonStyle="solid">
            <ta-radio-button value="1">
              列表
            </ta-radio-button>
            <ta-radio-button value="2">
              卡片
            </ta-radio-button>
          </ta-radio-group>
        </ta-col>
        <ta-col :span="2">
          <ta-search-panel :form="searchForm" id='form1' @search="onSearch" :height="400" placement="bottom" :visible="visibleSearch">
            <ta-button slot="target" @click="visibleSearch = true">打开高级搜索</ta-button>
            <div slot="formPanel">
              <ta-form :autoFormCreate="createForm">
                <template v-if="searchForm">
                  <ta-form-model-item label="性别" fieldDecoratorId="sex">
                    <ta-radio-group defaultValue="1">
                      <ta-radio value="1">
                        男
                      </ta-radio>
                      <ta-radio value="2">
                        女
                      </ta-radio>
                    </ta-radio-group>
                  </ta-form-model-item>
                  <ta-form-model-item
                      label='西医病种'
                      fieldDecoratorId="diseaseType"
                  >
                    <ta-input placeholder='请输入西医病种' />
                  </ta-form-model-item>
                  <ta-form-model-item label="治疗状态" fieldDecoratorId="status" >
                    <ta-radio-group defaultValue="1" buttonStyle="solid">
                      <ta-radio-button value="1">
                        治疗中
                      </ta-radio-button>
                      <ta-radio-button value="2">
                        未治疗
                      </ta-radio-button>
                    </ta-radio-group>
                  </ta-form-model-item>
                  <ta-form-model-item :labelCol="{span: 8, offset: 0}" label="最近就诊日期" fieldDecoratorId="visitDate" >
                    <ta-range-picker :placeholder="['年-月-日','年-月-日']"  />
                  </ta-form-model-item>
                  <ta-form-model-item label="建档日期" fieldDecoratorId="createDate" >
                    <ta-range-picker :placeholder="['年-月-日','年-月-日']"  />
                  </ta-form-model-item>
                </template>
              </ta-form>
            </div>
          </ta-search-panel>
        </ta-col>
        <ta-col :span="4">
          <ta-input-search placeholder="输入患者姓名或身份证号查询" v-model="searchKey" class="search-box" @search="fnSearch" enterButton="查询"/>
        </ta-col>
        <ta-col :span="1"><ta-button @click="fnReset">重置</ta-button></ta-col>
      </ta-row>
      <div style="margin-bottom: 20px"></div>
      <ta-big-table
          border
          stripe
          resizable
          highlight-hover-row
          height="auto"
          ref="xTable"
          v-show="visitList"
          :export-config="{}"
          :import-config="{}"
          :data="tableData">
<!--        <template #topBar>-->
<!--          <ta-big-table-toolbar>-->
<!--            <div slot="buttons">-->
<!--              <ta-button @click="showModal">新增</ta-button>-->
<!--            </div>-->
<!--            <div slot="tools">-->
<!--              <ta-button >列表</ta-button>-->
<!--              <ta-button >卡片</ta-button>-->
<!--            </div>-->
<!--          </ta-big-table-toolbar>-->
<!--        </template>-->
<!--        <ta-big-table-column type="seq" width="60"></ta-big-table-column>-->
        <ta-big-table-column field="id" title="ID" width="60"></ta-big-table-column>
        <ta-big-table-column field="idCard" title="身份证" width="100"></ta-big-table-column>
        <ta-big-table-column field="name" title="姓名" width="100"></ta-big-table-column>
        <ta-big-table-column field="sex" title="性别" width="60" collection-type="SEX"></ta-big-table-column>
        <ta-big-table-column field="age" title="年龄" width="60"></ta-big-table-column>
        <ta-big-table-column field="tel" title="联系电话" ></ta-big-table-column>
        <ta-big-table-column field="diseaseType" title="西医病种" show-overflow></ta-big-table-column>
        <ta-big-table-column field="status" title="状态" show-overflow></ta-big-table-column>
        <ta-big-table-column field="visitDate" title="最近就诊日期" show-overflow></ta-big-table-column>
        <ta-big-table-column field="createDate" title="建卡日期" show-overflow></ta-big-table-column>
        <ta-big-table-column type='operate' :operate='{operateMenu: operateMenu,}' fixed='right' width='200'></ta-big-table-column>
      </ta-big-table>
      <template>
        <ta-row v-show="visitCard">
          <v-for v-for="(item,i) in tableData">
            <ta-col :span="4">
              <ta-card
                  hoverable
                  style="width: 250px;"
              >
                <img
                    alt="example"
                    src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                    slot="cover"
                />
                <div style="padding-left: 30px">
                  <p style="size: A5">ID:    {{item.id}}</p>
                  <p style="size: A5">联系电话:    {{item.tel}}</p>
                  <p style="size: A5">西医就诊:    {{item.diseaseType}}</p>
                  <p style="size: A5">就诊状态:    {{item.status}}</p>
                  <p style="size: A5">最近就诊:    {{item.visitDate}}</p>
                  <p style="size: A5">创建档案:    {{item.createDate}}</p>
                </div>
              </ta-card>
            </ta-col>
          </v-for>
        </ta-row>
      </template>
    </div>
    <ta-modal
        :title="title"
        v-model="visible"
        height="450px"
        @ok="handleOk"
    >
      <div>
      <ta-form-model
          ref='form'
          :model='formModel'
          :rules='rules'
          layout='horizontal'
          :label-col='labelCol'
          :wrapper-col='wrapperCol'
      >
        <ta-form-model-item
            label='身份证'
            prop='idCard'
        >
          <ta-input v-model='formModel.idCard' placeholder='请输入身份证号码' />
        </ta-form-model-item>
        <ta-form-model-item label='姓名'>
          <ta-input v-model='formModel.name' placeholder='请输入姓名' />
        </ta-form-model-item>
        <ta-form-model-item label="性别">
          <ta-radio-group v-model="formModel.sex">
            <ta-radio value="1">
              男
            </ta-radio>
            <ta-radio value="2">
              女
            </ta-radio>
          </ta-radio-group>
        </ta-form-model-item>
        <ta-form-model-item
            label='出生日期'
        >
          <ta-date-picker v-model='formModel.visitDate' :defaultValue="formModel.visitDate" />
        </ta-form-model-item>
        <ta-form-model-item
            label='年龄'
            prop='age'
        >
          <ta-input type="number" v-model='formModel.age' placeholder='请输入年龄' />
        </ta-form-model-item>
        <ta-form-model-item
            label='联系方式'
            prop='tel'
        >
          <ta-input placeholder='请输入联系方式' v-model='formModel.tel' />
        </ta-form-model-item>
        <ta-form-model-item
            label='西医病种'
        >
          <ta-input v-model='formModel.diseaseType' placeholder='请输入西医病种' />
        </ta-form-model-item>
      </ta-form-model>
      </div>
    </ta-modal>
  </div>
</template>
<script>
import moment from 'moment';
export default {
  name:'demoTestModulePart2',
  data () {
    return {
      loading: false,
      visible: false,
      visibleSearch: false,
      title: '',
      visitList: true,
      visitCard: false,
      searchKey: '',
      tableDataOriginal : [],
      searchForm: null,
      tableData:  [
        { id: 1,idCard: '430900198306141771', name: '陈平安', tel: '18889599321', sex: '1', age: 28, diseaseType: '灰指甲', status:"1", visitDate:'2024-06-07', createDate:'2022-06-28'},
        { id: 2,idCard: '430900198306141772', name: '徐凤年', tel: '18889599321', sex: '2', age: 22, diseaseType: '腰间盘突出', status:"2", visitDate:'2024-07-07', createDate:'2022-07-28' },
        { id: 3,idCard: '430900198306141773', name: '高木', tel: '18889599321', sex: '1', age: 32, diseaseType: '甲状腺肥大', status:"2", visitDate:'2024-03-07', createDate:'2023-08-28'},
        { id: 4,idCard: '430900198306141774', name: '杨超美', tel: '18889599321', sex: '2', age: 23, diseaseType: '骨质增生', status:"2", visitDate:'2024-02-07', createDate:'2024-09-28'},
        { id: 5,idCard: '430900198306141775', name: '西片', tel: '18889599321', sex: '1', age: 30, diseaseType: '灰指甲', status:"2", visitDate:'2024-01-07', createDate:'2024-01-28'},
        { id: 6,idCard: '430900198306141776', name: '范闲', tel: '18889599321', sex: '2', age: 21, diseaseType: '灰指甲', status:"2", visitDate:'2024-02-07', createDate:'2024-02-28'},
        { id: 7,idCard: '430900198306141777', name: '杨贵妃', tel: '18889599321', sex: '1', age: 29, diseaseType: '灰指甲', status:"2" , visitDate:'2024-03-07', createDate:'2024-03-28'},
        { id: 8,idCard: '430900198306141778', name: '刘能', tel: '18889599321', sex: '2', age: 35, diseaseType: '灰指甲', status:"2", visitDate:'2024-04-07', createDate:'2024-04-28'}
      ],
      operateMenu: [
        {
          name: this.title,
          icon: 'edit',
          onClick: (record, index) => {
            console.log("id=============",index)
            this.title = '编辑患者'
            this.formModel = record
            this.visible = true
          },
        },
        // {
        //   name: '删除',
        //   icon: 'delete',
        //   type: 'confirm',
        //   confirmTitle: '确认删除该信息？',
        //   onOk: (record, index) => {
        //     this.tableData = this.tableData.filter(item => item.id !== index);
        //     message.info('删除成功')
        //   },
        // },
      ],
      labelCol: { span: 8, },
      wrapperCol: { span: 16, },
      formModel: {
        idCard: '',
        name: '',
        sex: '',
        diseaseType: '',
        visitDate: '',
        tel: '',
        age: '',
      },
      rules: {
        mustInput: [
          { required: true, message: '这是一个必输项目!!!', trigger: 'change', }
        ],
        idCard: [{ required: true, message: '身份证号不能为空', }, { idCard: '2', message: '输入的身份证号码不合法', }],
        tel: [{ required: true, message: '输入的手机号码不能为空', }, { phone: 'mobile',message: '请输入正确的手机号!' }],
        age: [{ required: true, message: '输入的年龄不能为空', }, { phone: /^(0|[1-9]\d?|1[0-4]\d|150)$/,message: '年龄在0-150之间!' }],
      },
    }
  },
  methods: {
    successCallback (editColumnEnd) {
      // 返回最终的列情况，visible为是否显示
      console.log('点击确定的回调，确定后的列情况', editColumnEnd)
    },
    showModal() {
      this.visible = true
      this.title = '新增患者'
      this.formModel={
        idCard: '',
        name: '',
        sex: '',
        diseaseType: '',
        visitDate: '',
        tel: '',
        age: '',
      }
    },
    fnSearch(){
      if(this.searchKey === "" || this.searchKey == undefined){
        this.fnReset()
      }else {
        this.tableData = this.tableData.filter(item => item.idCard == this.searchKey.trim() || item.name === this.searchKey.trim())
      }
    },
    fnReset(){
      this.searchKey = ''
      this.tableData = [...this.tableDataOriginal]
    },
    autoFormCreate(form){
      this.form = form
    },
    showList(e){
      if(e.target.value === '1'){
        this.visitList=true;
        this.visitCard=false;
      }else {
        this.visitCard=true;
        this.visitList=false
      }
    },
    // 提交
    handleOk () {
      let formModel = {... this.formModel}
      let tableData = this.tableData
      console.log("我去",formModel)
      this.$refs['form'].validate(valid => {
        if (valid) {
          debugger
          if(formModel.id === '' || formModel.id == undefined){
            formModel.id = tableData.length
            formModel.status = '1'
            formModel.createDate = moment(new Date()).format('YYYY-MM-DD')
            formModel.visitDate=moment(formModel.visitDate._d).format('YYYY-MM-DD');
            this.tableData.push(formModel)
            message.success('保存成功!');
          }else {
            let index = tableData.findIndex(item => item.id === formModel.id);
            if (index !== -1) {
              tableData[index] = formModel; // 直接替换
            }
            message.success('修改成功!');
          }
          this.visible = false
        } else {
          return false;
        }
      });
    },
    createForm(form) {
      this.searchForm = form
    },
    onSearch(val) {
      this.tableData = [...this.tableDataOriginal]
      if(val.sex != '' && val.sex != undefined){
        this.tableData = this.tableData.filter(item => item.sex === val.sex)
      }
      if(val.diseaseType != '' && val.diseaseType != undefined){
        this.tableData = this.tableData.filter(item => item.diseaseType === val.diseaseType.trim())
      }
      if(val.status != '' && val.status != undefined){
        this.tableData = this.tableData.filter(item => item.status === val.status)
      }
      if(val.visitDate != '' && val.visitDate != undefined){
        this.tableData = this.tableData.filter(item =>
            moment(item.visitDate, 'YYYY-MM-DD').isBetween(val.visitDate[0]._d,val.visitDate[1]._d, 'day', '[]'))
      }
      if(val.createDate != '' && val.createDate != undefined){
        this.tableData = this.tableData.filter(item =>
            moment(item.createDate, 'YYYY-MM-DD').isBetween(val.createDate[0]._d,val.createDate[1]._d, 'day', '[]'))
      }
      this.$notification.open({
        message: '搜索结果',
        description: JSON.stringify(val)
      });
    },
  },
  mounted(){
    this.tableDataOriginal = [...this.tableData]
  }
}
</script>
