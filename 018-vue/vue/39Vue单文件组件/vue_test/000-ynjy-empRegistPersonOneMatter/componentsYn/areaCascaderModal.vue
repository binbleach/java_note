<template>
  <!--行政区划弹框组件-->
  <div class="areaCascaderModal">
    <ta-input
      style="width: 100%;cursor: pointer"
      :value="nameValue"
      :disabled="disabled"
      :placeholder="placeholder"
      :read-only="true"
      allowClear
      @click="handleClick"/>
    <ta-select
      v-show="false"
      :value="value"
    />
    <ta-modal
      :title="modalTitle"
      :visible="visible"
      :destroy-on-close="false"
      width="85%"
      height="500px"
      :mask-closable="false"
      :centered="true"
      wrap-class-name="publicStyle"
      @cancel="handleCancel"
    >
      <ta-border-layout
        :layout="{header:'80px',footer: '60px'}"
        :header-cfg="{layoutConStyle:{padding:0}, showBorder: false}"
        :footer-cfg="{layoutConStyle:{padding:0}, showBorder: false}"
        :center-cfg="{layoutConStyle:{padding:0},showBorder:false}"
        :show-border="false"
      >
        <div class="form-box" slot="header">
          <ta-form layout="horizontal" :col="2"
                   :autoFormCreate="(form) => {this.topForm = form;}" :form-layout="true" id="topForm">
            <ta-form-item label="已选择行政区划" disabled fieldDecoratorId="selected">
              <ta-input/>
            </ta-form-item>
            <ta-form-item label="输入名称检索" fieldDecoratorId="selectedSearch">
              <ta-select
                showSearch
                optionFilterProp="children"
                :value="value"
                placeholder="输入名称或拼音"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="handleChange"
                :notFoundContent="fetching ? undefined : null"
              >
                <ta-spin v-if="fetching" slot="notFoundContent" size="small"/>
                <ta-select-option v-for="d in dataSearch" :key="d.areaid">{{
                    d.namepath
                  }}
                </ta-select-option>
              </ta-select>
            </ta-form-item>
          </ta-form>
        </div>
        <div class="area-box">
          <ta-card class="fit area-li">
            <div id="level1">
              <div :class="{'area-label': true,'area-label-active': item.active}"
                   v-for="item in dataArea.level1" :key="item.areaid"
                   @click="handleArea(item,1)">{{ item.areaname }}
              </div>
            </div>
          </ta-card>
          <ta-card class="fit area-li">
            <div id="level2">
              <div :class="{'area-label': true,'area-label-active': item.active}"
                   v-for="item in dataArea.level2" :key="item.areaid"
                   @click="handleArea(item,2)">{{ item.areaname }}
              </div>
            </div>
          </ta-card>
          <ta-card class="fit area-li">
            <div id="level3">
              <div :class="{'area-label': true,'area-label-active': item.active}"
                   v-for="item in dataArea.level3" :key="item.areaid"
                   @click="handleArea(item,3)">{{ item.areaname }}
              </div>
            </div>
          </ta-card>
          <ta-card class="fit area-li">
            <div id="level4">
              <div :class="{'area-label': true,'area-label-active': item.active}"
                   v-for="item in dataArea.level4" :key="item.areaid"
                   @click="handleArea(item,4)">{{ item.areaname }}
              </div>
            </div>
          </ta-card>
          <ta-card class="fit area-li">
            <div id="level5">
              <div :class="{'area-label': true,'area-label-active': item.active}"
                   v-for="item in dataArea.level5" :key="item.areaid"
                   @click="handleArea(item,5)">{{ item.areaname }}
              </div>
            </div>
          </ta-card>
        </div>
      </ta-border-layout>
      <template slot="footer">
        <ta-button type="primary" @click="handleReset">重置</ta-button>
        <ta-button type="default" @click="handleCancel">取消</ta-button>
        <ta-button type="primary" @click="handleConfirm">确定</ta-button>
      </template>
    </ta-modal>
  </div>
</template>

<script>
export default {
  name: "areaCascaderModal",
  model: {
    prop: 'value',
    event: 'change',
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    // 脱敏显示 ，查询接口回显
    sensitiveTxt: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    },
    modalTitle: {
      type: String,
      default: '行政区划'
    },
    // 限制省份
    limitDefault: {
      type: [String, Number],
      default: '',
    },
    // 限制选择层级
    limitFloor: {
      type: [String, Number],
      default: 2,
    }
  },
  data() {
    return {
      nameValue: '',
      visible: false,
      fetching: false,
      dataArea: {
        level1: [],
        level2: [],
        level3: [],
        level4: [],
        level5: [],
      }, // 行政区划数据
      currentObj: {}, // 当前选中 最后一级
      dataSearch: [], // 检索数据
    }
  },
  watch: {
    value(val) {
      // 监听value变化，为空时清空表单
      if (!val) {
        this.nameValue = ''
      }
      // 查询对应行政区划的描述 当val为true且与当前组件选中相同时 不查询
      if (!(val == this.currentObj.areaid && this.nameValue == this.currentObj.namepath) && val) {
        let formData = {
          areaid: val
        }
        this.Base.sendRequest("api/business/invoke", {
          apiCode: 'jy007_com02', // url
          ...formData, // 表单数据
        }, data => {
          let dataAea = data.taarea
          if (dataAea) {
            // 脱敏存在
            if(this.sensitiveTxt){
              dataAea.namepath = this.sensitiveTxt
            }
            this.nameValue = dataAea.namepath
            this.currentObj = dataAea
            this.$emit('areaChange', this.currentObj)
          }
        })
      }
    },
    visible(val) {
      if (val) {
        this.setModalForm()
        if(this.dataArea.level1.length === 0){
          this.handleArea()
        }
      }
    }
  },
  mounted() {

  },
  methods: {
    // 重置
    handleReset() {
      this.topForm.setFieldsValue({selectedSearch: '', selected: ''})
      this.currentObj = {}
      this.dataSearch = []
      this.nameValue = ''
      this.$emit('change', '')
      this.$emit('areaChange', {})
      this.clearData(0)
      this.handleArea()
    },
    // 检索
    handleSearch(value) {
      if (value.length < 2) {
        return
      }
      // 传入长度适配
      let topAreaid = (this.limitDefault.length != 12 ? this.limitDefault.substr(0, 2) + '0000000000' : this.limitDefault)
      let formData = {
        areaname: value,
        topAreaid: topAreaid, // 限制检索范围
      }
      this.Base.sendRequest("api/business/invoke",{
        apiCode: 'jy007_com03', // url
        ...formData, // 表单数据
      }, data => {
        let dataAea = data.taareaList
        // console.log(dataAea, '====')
        this.dataSearch = dataAea
      })
    },
    // 检索框改变并回显到下方
    handleChange(value) {
      this.clearData(0)
      let idpath = ''
      this.dataSearch.forEach(item => {
        if (item.areaid == value) {
          this.currentObj = item
          idpath = item.idpath.split('/')
        }
      })
      // console.log(idpath, '选中')
      let formData = {
        areaid: value
      }
      this.Base.sendRequest("api/business/invoke",{
        apiCode: 'jy007_com04', // url
        ...formData, // 表单数据
      }, data => {
        let dataAea = data.areaMapping
        // 设置各级数据
        for (let key in this.dataArea) {
          this.dataArea[key] = dataAea[key + 'List']
        }
        // 筛选
        if (this.limitDefault && this.limitDefault.length > 1) {
          this.dataArea['level1'] = this.dataArea['level1'].filter(item => {
            return item.areaid.substr(0, 2) == this.limitDefault.substr(0, 2)
          })
        }
        // 设置选中
        this.$nextTick(() => {
          this.setActive(idpath)
        })
      })
    },
    // 检索回显设置选中1
    setActive(idpath) {
      idpath.forEach((_item, _index) => {
        this.dataArea['level' + (_index + 1)].forEach((item, index) => {
          item['active'] = false
          if (item.areaid == _item) {
            item['active'] = true
            // 滚动位置
            document.getElementById('level' + (_index + 1)).scrollTop = 33 * index
          }
        })
      })
      this.$forceUpdate()
    },
    // 行政区划点击搜索
    handleArea(_item = {}, _levelType = 0) {
      if (_item.areaid) {
        this.currentObj = _item // 赋值选中数据--选中最后一级
        // 设置选中
        this.dataArea['level' + _levelType].forEach(item => {
          item['active'] = false
          if (item.areaid == _item.areaid) {
            item['active'] = true
          }
        })
        // 清空选中
        this.clearData(_levelType)
        this.$forceUpdate() // 强制刷新
      }
      if (_levelType == 5) {
        return
      }
      // 搜索
      let formData = {
        parentid: _item.areaid
      }
      this.Base.sendRequest("api/business/invoke",{
        apiCode: 'jy007_com01', // url
        ...formData
      }, data => {
        let dataAea = data.taareaList
        // 限制省级行政 限制传入2位及以上
        if (this.limitDefault && this.limitDefault.length > 1 && _levelType < 1) {
          dataAea = dataAea.filter(item => {
            return item.areaid.substr(0, 2) == this.limitDefault.substr(0, 2)
          })
        }
        this.dataArea['level' + (_levelType ? (_levelType + 1) : 1)] = dataAea
      })
    },
    // 清空下级选中
    clearData(_levelType) {
      for (let i = 1; i <= 5; i++) {
        if (_levelType < i) {
          this.dataArea['level' + i] = []
        }
      }
    },
    handleClick() {
      this.visible = true
    },
    handleConfirm() {
      if (!this.currentObj.areaid) {
        this.$message.warning('请选择！')
        return
      }
      if (this.limitFloor) {
        let areaid = this.currentObj.areaid
        if (!this.handleLimitFloor(areaid)) {
          return;
        }
      }
      this.nameValue = this.currentObj.namepath
      this.setModalForm()
      this.$emit('change', this.currentObj.areaid)
      this.$emit('areaChange', this.currentObj)
      this.visible = false
    },
    // 限制☑️层级
    handleLimitFloor(areaid) {
      if (this.limitFloor == 2) {
        if (areaid.substr(2, 2) == '00') {
          this.$message.warning('至少选择到地市级！')
          return false
        }
      } else if (this.limitFloor == 3) {
        if (areaid.substr(4, 2) == '00') {
          this.$message.warning('至少选择到区、县级！')
          return false
        }
      } else if (this.limitFloor == 4) {
        if (areaid.substr(6, 3) == '000') {
          this.$message.warning('至少选择到乡、镇级！')
          return false
        }
      } else if (this.limitFloor == 5) {
        if (areaid.substr(9, 3) == '000') {
          this.$message.warning('至少选择到社区、街道级！')
          return false
        }
      }
      return true
    },
    // 设置选中区划表单值
    setModalForm() {
      this.$nextTick(() => {
        this.topForm.setFieldsValue({selected: this.nameValue});
      });
    },
    // 获取当前行政区划描述
    getCurrentDetails() {
      return this.nameValue
    },
    handleCancel() {
      this.visible = false
    }
  }
}
</script>

<style scoped lang="less">
.area-box {
  display: flex;
  height: 100%;
}

.area-li {
  flex: 1;
  margin-right: 24px;
  overflow: auto;
}

.area-li:last-child {
  margin: 0;
}

#level1, #level2, #level3, #level4, #level5 {
  height: 100%;
  overflow: scroll;
}

//::v-deep .ant-card-body{
//    padding: 0;
//}
.area-label {
  padding: 6px;
  display: inline-block;
  width: 100%;
  vertical-align: middle;
  text-align: left;
  cursor: pointer;
}

.area-label-active {
  color: white;
  background: rgba(131, 189, 253, .8);
}
</style>
