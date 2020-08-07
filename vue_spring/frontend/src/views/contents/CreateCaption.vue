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
            <div class="row justify-content-around">
                <div class="col-6">
                    <p class="font-weight-bolder" style="font-size: 30px;">영상 미리보기</p>
                </div>
                <div class="col-6">
                    <p class="font-weight-bolder" style="font-size: 30px;">자막 편집 창</p>
                    <table class="table">
                      <thead>
                        <!-- <tr>
                          <th scope="col">자막</th>
                        </tr> -->
                      </thead>
                      <tbody>
                        
                      </tbody>
                    </table>
                    <!-- <img src="../../img/img-subedit.png" alt="자막 편집 이미지" class="subedit" height="600"> -->
                </div>
            </div>
            <div class="row" style="height:400px;">
                <div class="col-6" style="border:1px solid transparent;">
                    <!-- <p>영상</p> -->
                    <video-player-vue :video=video ></video-player-vue>
                </div>
                <div class="col-6" style="border:1px solid transparent;">
                    <!-- <p>자막</p> -->
                    <subtitle-vue v-for="subtitle in subtitles" :key="subtitle.startTime" :subtitle="subtitle"/>  
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import VideoPlayerVue from "./../../components/VideoPlayerVue"
  import axios from 'axios'
  import subtitleVue from './../../components/Subtitle.vue'

const SERVER_URL = 'http://127.0.0.1:8000'

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