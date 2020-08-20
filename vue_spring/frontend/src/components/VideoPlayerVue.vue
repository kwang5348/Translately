<template>
  <div class="player">
    <video id="video" controls class="col-12" width="500px" preload="metadata" crossorigin="anonymous">
      <source :src="objectUrl" type="video/mp4">
      <track label="korean" kind="subtitles" srclang="ko" :src="downloadUrl" default>
      <track label="english" kind="subtitles" srclang="en" :src="downloadUrl">
      <track label="english1" kind="subtitles" srclang="ko" :src=downloadUrl>
      <track label="english2" kind="subtitles" srclang="en" :src=downloadUrl>
    </video>
    <b-button style="background-color: #564892;"><a class="text-white" :href="downloadUrl"> 자막 다운로드 </a></b-button>
  </div>
</template>

<script>

export default {
  name: 'video-player-vue',
  props: {
    video: {},
    downloadUrl: {}
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