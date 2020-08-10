<template>
  <div class="player">
    <video id="video" controls width="500px" preload="metadata">
      <track label="korean" kind="subtitles" srclang="ko" src="sftp://ubuntu@i3a511.p.ssafy.io/home/ubuntu/resources/vtt/vczvcxzfsda.vtt" default>
      <track label="english" kind="subtitles" srclang="en" src="sftp://ubuntu@i3a511.p.ssafy.io/home/ubuntu/resources/vtt/vczvcxzfsda.vtt">
      <source :src="objectUrl" type="video/mp4">
    </video>
    <button class="btn btn-primary" @click="fileDownload">다운로드</button>
  </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'

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
    fileDownload() {
      axios.get(`${SERVER_URL}/api/download`)
      .then((response) => {
        const fileURL = window.URL.createObjectURL(new Blob([response.data]))
        const fileLink = document.createElement('a')
        fileLink.href = fileURL
        fileLink.setAttribute('download', 'file.vtt')
        document.body.appendChild(fileLink)

        fileLink.click()
      })
    }
  },
  computed: {
    objectUrl() {
      return URL.createObjectURL(this.video)
    }
  }
}
</script>

<style>
</style>