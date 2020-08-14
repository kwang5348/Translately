<template>
  <div class="player">
    <video id="video" controls class="col-12" width="500px" preload="metadata" crossorigin="anonymous">
      <source src="https://www.youtube.com/watch?v=0-bZLYFNA7k" type="video/mp4">
      <track label="korean" kind="subtitles" srclang="ko" :src="downloadUrl" default>
      <track label="english" kind="subtitles" srclang="en" :src="downloadUrl">
      <track label="english1" kind="subtitles" srclang="ko" :src=downloadUrl>
      <track label="english2" kind="subtitles" srclang="en" :src=downloadUrl>
    </video>
    <b-button variant="primary"><a :href="downloadUrl"> 자막 다운로드 </a></b-button>
  </div>
</template>

<script>

export default {
  name: 'video-player-vue',
  props: {
    video: {}
  },
  data() {
    return {
    }
  },
  methods:{

  },
  computed: {
    objectUrl() {
      return URL.createObjectURL(this.video)
    },
    downloadUrl() {
      const name = this.video.name.replace(".mp4", "")
      const url = "http://i3a511.p.ssafy.io/api/vtt/download?fileLink=" + name
      return url
    }
  }
}
</script>

<style>
</style>