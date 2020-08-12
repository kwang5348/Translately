<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <nav id="sidebar">
      <div class="sidebar-header">
        <button type="button" id="sidebarCollapse" class="btn">
          <i class="fa fa-bars fa-inverse"></i>
        </button>
      </div>

      <ul class="list-unstyled components">
        <li class="active">
          <a href="/tutorial">
          <!-- <a href="/tutorial" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"> -->
            <i class="fas fa-home"></i>
            튜토리얼
          </a>
        </li>
        <li>
          <a href="/myproject">
            <i class="fas fa-briefcase"></i>
            내 프로젝트
          </a>
        </li>
        <li>
          <a href="/articles">
            <i class="fas fa-image"></i>
            커뮤니티
          </a>
        </li>
        <li>
          <a href="/mypage">
            <i class="fas fa-question"></i>
            마이 페이지
          </a>
        </li>
      </ul>

      <ul class="list-unstyled CTAs">
        <li>
          <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download Tutorial PDF</a>
        </li>
        <li>
          <a href="/" class="article">Back To HOME</a>
        </li>
      </ul>
      <ul class="list-unstyled components">
        <li>
          <a href="#">
            <span><i class="fa fa-power-off"></i></span>
            <span><router-link @click.native="logout" to="/">로그아웃</router-link></span>
          </a>
        </li>
      </ul>
    </nav>
    <!-- Page Content  -->
    <div class="container-fluid mt-5">
        <div class="row justify-content-around" style="font-size: 25px; color: black;">
            <div class="col-5">
              <p class="font-weight-bolder" style="font-size: 25px; color: black;">영상 미리보기</p>
            </div>
            <div class="col-7">
              <p class="font-weight-bolder" style="font-size: 25px; color: black;">자막 편집 창</p>
            </div>
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
                  <div class="col-1"><img src="..\..\img\img-thumbnail.png" alt="썸네일" style="height: 50px;"></div>
                  <div class="col-9" style="padding: 0px; padding-left: 20px">
                    <p style="color: gray; font-size: 13px; margin:0px">원래 음성의 자막이 표시됩니다.</p>
                    <p style="color: black; font-weight: bold; margin:0px; font-size: 13px;">이 곳에서 자막을 수정할 수 있습니다.</p>
                  </div>
                  <div class="col-2" style="padding: 0px; padding-left: 10px;">
                    <p style="font-size: 13px; margin-bottom: 0px;">시작 ~</p>
                    <p style="font-size: 13px; margin-bottom: 0px;">종료</p>
                  </div>
                </div>
              </b-alert>
              <div :class="[isVisible ? 'bg-info' : 'bg-light', 'border', 'p-2', 'text-center']" style="height: 550px; overflow-y: scroll;">
                <subtitle-vue v-for="subtitle in subtitles" :key="subtitle.startTime" :subtitle="subtitle"/>  
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

  const SERVER_URL = 'http://i3a511.p.ssafy.io/'

  export default {
    name: 'CreateCaption',
    components: {
      subtitleVue,
      VideoPlayerVue
    },
    props: {
      subtitles: {
        type: Array,
      },
      video: { 
      }
    },
    methods: {
      getUser() {
        axios.get(SERVER_URL + '/createcaption')
      },
    },
  }

</script>

<style scoped>

</style>