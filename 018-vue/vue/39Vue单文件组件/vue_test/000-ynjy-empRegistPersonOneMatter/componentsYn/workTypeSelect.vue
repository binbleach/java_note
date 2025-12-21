<template>
    <div>
        <ta-select
                allowClear
                style="width: 100%"
                :value="value"
                @change="workChange"
                :open="false"
                :showArrow="false"
                :disabled="disabled"
                :placeholder="placeholder"
                @dropdownVisibleChange="workModal"
                @focus="$emit('onFocus', value)"
                @blur="$emit('onBlur', value)"
        >
            <ta-select-option v-for="item in CollectionData('aca111')" :key="item.value" :value="item.value">
                {{item.label}}
            </ta-select-option>
        </ta-select>

        <work-type-child :visible="visible" :defaultValue="defaultValue" :typeNumber='typeNumber' :getPopupPublic='getPopupPublic' @onClose="visible=false" @changeValue="changeValue" @changeValueDsc="changeValueDsc" @resetValue="resetValue" ></work-type-child>
    </div>

</template>

<script>
    import workTypeChild from "@projectCommon/componentsYn/part/workTypeChild.vue";
export default {
    name: 'workTypeSelect',
    components: {workTypeChild},
    model: {
        props: 'value',
        event: 'change'
    },
    props: {
        value: {    // 输入框值
            type: String,
        },
        placeholder: {    // 输入框提示文字
            type: String,
            default: () => {
                return ''
            }
        },
        typeNumber : {    // 分类查询工种
            type: String,
                default: () => {
                        return 'all'
            }
        },
        disabled: {  // 置灰
            type: Boolean,
            default: () => {
                return false
            }
        },
        getPopupPublic: {
            type: String,
            default: () => {
              return 'app'
            }
          },

    },
  data () {
    return {
        visible: false,
        defaultValue: ''

    }
  },



  methods: {
      workModal(){
          this.defaultValue = this.value
          this.visible = true
      },
      workModalClose(){
          this.visible = false
      },
        changeValueDsc(value) {
            this.$emit('getValueDsc', value)
        },
      changeValue(values){
          this.workChange(values)
          this.workModalClose()

      },

      resetValue(values){
          this.workChange(values)
      },

      workChange(value){
          this.$emit('change', value)
          this.$emit('onBind', value)
      }

  },


}
</script>

<style type="text/less" lang="less" scoped>

</style>
