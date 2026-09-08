
<template>
  <div class="app">
    <Count/>
    <hr/>
    <Person/>
  </div>
</template>

<script>
import Count from "./components/Count";
import Person from "./components/Person";
export default {
  name: "App",
  components: {
    Count,
    Person
  },
  data(){
    return{

    }
  },
}
</script>

<style scoped>

</style>