<template>
  <div class="player">
    <video id="video" controls class="col-12" width="500px" preload="metadata" crossorigin="anonymous">
      <source :src="objectUrl" type="video/mp4">
      <track label="default" kind="subtitles" srclang="ko" :src="downloadUrl" default>
      <track label="final_sub" kind="subtitles" srclang="en" :src="downloadUrl1">
    </video>
    <b-button style="border-color: transparent; background-color: #564892;"><a class="text-white" :href="downloadUrl"> 자막 다운로드 </a></b-button>
  </div>
</template>

<script>

export default {
  name: 'video-player-vue',
  props: {
    video: {},
    downloadUrl: {},
    downloadUrl1: {}
  },
  data() {
    return {
    }
  },
  methods:{

  },
  computed: {
    objectUrl() {
      if (typeof this.video === 'string') {
        return "http://i3a511.p.ssafy.io:8399/api/mp4/download?fileLink=" + this.video
      } else {
        return URL.createObjectURL(this.video)
      }
    },
  }
}
</script>

<style>
</style>