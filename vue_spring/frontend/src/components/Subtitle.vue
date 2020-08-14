<template>
  <!-- <div class="wrapper"> -->
  
      <div class="row sub-box">
        <div class="col-1">
          <!-- <img src="..\img\img-thumbnail.png" alt="썸네일" style="height: 50px; margin-left: 10px;"> -->
        </div>
        <div class="col-9" style="padding: 0px;">
          <p style="color: grey; font-size: 13px; margin:0px">{{ subtitle.startsub }}</p>
          <p style="color: black; font-weight: bold; font-size: 13px;">{{ subtitle.targetsub }}</p>
        </div>
        <div class="col-2" style="padding: 0px;">
          <!-- <p style="font-size: 13px; margin:0px">{{ subtitle.startTime }} ~</p>
          <p style="font-size: 13px; margin:0px">{{ subtitle.endTime }}</p> -->
          <p style="font-size: 13px; margin:0px">{{ start }} ~</p>
          <p style="font-size: 13px; margin:0px">{{ end }}</p>
        </div>
      </div>
  <!-- </div> -->
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

</style>