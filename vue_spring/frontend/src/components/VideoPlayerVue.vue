<template>
  <div class="player">
    <video id="video" controls width="500px" preload="metadata">
      <source :src="objectUrl" type="video/mp4">
    </video>
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
  computed: {
    objectUrl() {
      return URL.createObjectURL(this.video)
    }
  }
}
</script>

<style>
</style>