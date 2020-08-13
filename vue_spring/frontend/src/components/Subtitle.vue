<template>
  <div>  
  <div class="row sub-box">
    <div class="col-1"></div>
    <div class="col-9" style="padding: 0px;">
      <p class="start-subtitle" style="color: grey; font-size: 13px; margin:0px">{{ subtitle.eng }}</p>
      <p class="target-subtitle" style="color: black; font-weight: bold; font-size: 13px;">{{ subtitle.kor }}</p>
    </div>
    <div class="col-2" style="padding: 0px;">
      <!-- <p style="font-size: 13px; margin:0px">{{ subtitle.startTime }} ~</p>
      <p style="font-size: 13px; margin:0px">{{ subtitle.endTime }}</p> -->
      <p style="font-size: 13px; margin:0px">{{ start }} ~</p>
      <p style="font-size: 13px; margin:0px">{{ end }}</p>
    </div>
  </div>
  <hr class="division-line">
  </div>
      
</template>

<script>
export default {
  name: "subtitle-vue",
  props: {
    subtitle: {
      type: Object
    }
  },
  computed: {
    start() {
      return this.subtitle.startTime.toFixed(2)
    },
    end() {
      return this.subtitle.endTime.toFixed(2)
    }
  }


}
</script>

<style>
.sub-box {
  background-color: transparent;
  border-color: black;
  padding: 0px;
}

.target-subtitle {
  margin-bottom: 0px;
}

.division-line {
  margin: 10px 20px;
}

</style>