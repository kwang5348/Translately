<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <div class="container-fluid m-0 p-0" style="height:100vh;">
    <div class="row">
      <div class="col-2" style="background-color:#564892;">
        <b-list-group style="height:100vh; border-style:none;">
          <b-list-group-item href="/" class="text-white pt-3 mb-2" style="background-color:#564892; height:150px;">
            <img src='../../img/logo-letter-w.png' class="mt-5" alt="서비스 로고" width="180"/>
          </b-list-group-item>
          <b-list-group-item href="/contents/tutorial" class="text-white " style="background-color:#564892; height:70px; ">튜토리얼</b-list-group-item>
          <b-list-group-item href="/contents/translate" class="text-white " style="background-color:#564892; height:70px; ">자막 변환</b-list-group-item>
          <b-list-group-item href="/contents/myproject" class="text-white " style="background-color:#564892; height:70px; ">내 프로젝트</b-list-group-item>
          <b-list-group-item href="/contents/community" class="text-white " style="background-color:#564892; height:70px; ">커뮤니티</b-list-group-item>
          <b-list-group-item href="/contents/mypage" class="text-white " style="background-color:#564892; height:70px;">마이 페이지</b-list-group-item>
          <!-- <b-list-group-item style="background-color:#564892; height:70px;">
            <a href="https://github.com/eppy7819/projects/files/5048206/Translately.Tutorial.pdf"
            class="list-group-item2download">튜토리얼 다운로드</a>
          </b-list-group-item> -->
          <b-list-group-item><a href="/" style="height:70px;"><b-button class="list-group-item2 mt-2" variant="light">Back To HOME</b-button></a></b-list-group-item>
          <b-list-group-item style="background-color:#564892; margin: auto;">
            <a href="#" @click="logout"><span><i class="fa fa-power-off text-white"></i></span>
              <span class="text-white ">로그아웃</span>
            </a>
          </b-list-group-item>
        </b-list-group>
      </div>
      <div class="col-10" style="margin: 0;">
        <Navbar2/>
        <!-- Page Content  -->
        <div class="container-fluid mt-5">
            <div class="row justify-content-around" style="font-size: 25px; color: black;">
                <div class="col-5">
                  <p class="font-weight-bolder" style="font-size: 25px; color: black;">영상 미리보기</p>
                </div>
                <div class="col-6">
                  <p class="font-weight-bolder" style="font-size: 25px; color: black;">자막 편집 창</p>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row">
                <div class="col-5" style="border:1px solid transparent;">
                    <!-- <p>영상</p> -->
                    <video-player-vue :video=video ></video-player-vue>
                </div>
                <div class="col-7" style="border:1px solid transparent; padding-right: 10px">
                  <!-- <p>자막</p> -->
                  <b-alert show dismissible fade variant="warning" style="margin-top:2px; padding-left: 20px; padding-bottom: 0px;">
                    <div class="row">
                      <div class="col-11" style="padding: 0px; padding-left: 20px">
                        <p style="color: gray; font-size: 13px; margin:0px">원래 음성의 자막이 표시됩니다.</p>
                        <p style="color: black; font-weight: bold; margin:0px; font-size: 13px;">이 곳에서 자막을 수정할 수 있습니다.</p>
                      </div>
                      <div class="col-1" style="padding: 0px; padding-left: 10px;">
                        <p style="font-size: 13px; margin-bottom: 0px; color: black;">시작 ~</p>
                        <p style="font-size: 13px; margin-bottom: 0px; color: black;">종료</p>
                      </div>
                    </div>
                  </b-alert>
                  <div :class="[isVisible ? 'bg-info' : 'bg-light', 'border', 'p-2', 'text-center']" style="height: 550px; overflow-y: scroll;">
                    <subtitle-vue v-for="subtitle in subtitles" :key="subtitle.startTime" :subtitle="subtitle"/>  
                  </div>
                </div>
              </b-alert>
              <div :class="[isVisible ? 'bg-info' : 'bg-light', 'border', 'p-2', 'text-center']" style="height: 550px; overflow-y: scroll;">
                <b-overlay
                  :show="translateBusy"
                  rounded
                  opacity="0.6"
                  spinner-small
                  spinner-variant="primary"
                  class="d-inline-block"
                  @hidden="onHidden"
                >
                  <subtitle-vue v-for="subtitle in subtitles" :key="subtitle.startTime" :subtitle="subtitle" />
                </b-overlay>
              </div>
            </div>
          </div>



      </div>
    </div>
  </div>
    

</template>

<script>
  import VideoPlayerVue from "./../../components/VideoPlayerVue"
  import axios from 'axios'
  import subtitleVue from './../../components/Subtitle.vue'
  import Navbar2 from "@/components/Navbar2.vue"

  const SERVER_URL = 'http://i3a511.p.ssafy.io/'

  export default {
    name: 'CreateCaption',
    components: {
      subtitleVue,
      VideoPlayerVue,
      Navbar2,
    },
    data() {
      return {
        timeout: null
      }
    },
    beforeDestroy() {
      this.clearTimeout()
    },
    props: {
      subtitles: {
        type: Array,
      },
      video: { 
      },
      translateBusy: {
        type: Boolean
      }
    },
    methods: {
      getUser() {
        axios.get(SERVER_URL + '/createcaption')
      },
      clearTimeout() {
        if (this.timeout) {
          clearTimeout(this.timeout)
          this.timeout = null
        }
      },
      setTimeout(callback) {
        this.clearTimeout()
        this.timeout = setTimeout(() => {
          this.clearTimeout()
          callback()
        }, 5000)
      },
      onHidden() {
        // Return focus to the button once hidden
        this.$refs.button.focus()
      },
    },
  }

</script>

<style scoped>
.list-group-item:first-child{
  border-top-left-radius:0rem;
  border-top-right-radius:0rem;
  }
.list-group-item:last-child{
  border-bottom-right-radius:0rem;
  border-bottom-left-radius:0rem;
  }

.list-group-item {
  font-size: 20px;
  font-weight: bold;
  background-color:#564892;
  padding: 0px;
  border-color: transparent;
}

.list-group-item2 {
  font-weight: bold;
  /* background-color: black; */
}
</style>