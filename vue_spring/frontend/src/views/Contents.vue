<template>
  <div class="container-fluid m-0 p-0" style="height:100vh;">
    <div class="row">
      <div class="col-2" style="background-color:#564892;">
        <b-list-group style="height:100vh; border-style:none;">
          <b-list-group-item href="/" class="text-white pt-3" style="background-color:#564892; height:150px;">
            <img src='../img/logo-letter-w.png' class="mt-5" alt="서비스 로고" width="160"/>
          </b-list-group-item>
          <b-list-group-item href="/contents/tutorial" class="text-white " style="background-color:#564892; height:70px; ">튜토리얼</b-list-group-item>
          <b-list-group-item href="/contents/translate" class="text-white " style="background-color:#564892; height:70px; ">자막 변환</b-list-group-item>
          <!-- <b-list-group-item href="/contents/myproject" class="text-white " style="background-color:#564892; height:70px; ">내 프로젝트</b-list-group-item> -->
          <b-list-group-item href="/contents/community" class="text-white " style="background-color:#564892; height:70px; ">커뮤니티</b-list-group-item>
          <b-list-group-item href="/contents/mypage" class="text-white " style="background-color:#564892; height:70px;">마이 페이지</b-list-group-item>
          <!-- <b-list-group-item style="background-color:#564892; height:70px;">
            <a href="https://github.com/eppy7819/projects/files/5048206/Translately.Tutorial.pdf"
            class="list-group-item2download">튜토리얼 다운로드</a>
          </b-list-group-item> -->
          <b-list-group-item><a href="/" style="height:70px;"><b-button class="list-group-item2 mt-2" style="background-color: rgb(162, 151, 204); border-color: transparent; border-radius: 0px">
            메인으로 돌아가기
          </b-button></a></b-list-group-item>
          <b-list-group-item style="background-color:#564892; margin: auto;">
            <a href="#" @click="logout"><span><i class="fa fa-power-off text-white"></i></span>
              <span class="text-white "> 로그아웃</span>
            </a>
          </b-list-group-item>
        </b-list-group>
      </div>
      <div class="col-10" style="margin: 0; padding: 0;">
        <Navbar2 :remainTime="remainTime"/>
        <router-view 
          @upload-file="uploadFile" 
          @submit-upload-option="uploadOption"
          @destroy-create-caption="destroyCreateCaption"
          @submit-complete-translate="submitCompleteTranslate"
          @create-navbar="createNavbar"
          :video="video"
          :isLogin="isLogin"
          :subtitles="subtitles"
          :translateBusy="translateBusy"
          :translateProgress="translateProgress"
          :downloadUrl="downloadUrl"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Navbar2 from "@/components/Navbar2.vue"

export default {
  name : 'Contents',
  components : {
    Navbar2,
  },
  props: {
    remainTime: {
      type: Number
    },
    isLogin: {
      type: Boolean
    },
    video: {},
    subtitles: {
      type: Array
    },
    translateBusy: {
      type: Boolean
    },
    translateProgress: {
      type: Number
    },
    downloadUrl: {
      type: String
    },
  },
  methods: {
    logout() {
      this.$emit('logout')
    },
    uploadFile(video) {
      this.$emit('upload-file', video)
    },
    uploadOption(uploadData) {
      this.$emit('submit-upload-option', uploadData)
    },
    destroyCreateCaption() {
      this.$emit('destroy-create-caption')
    },
    submitCompleteTranslate(subtitles) {
      this.$emit('submit-complete-translate', subtitles)
    },
    createNavbar() {
      this.$emit('create-navbar')
    }
  },
}

</script>

<style scoped>
/* .list-group-item {
  boder-style: none;
} */
.list-group-item:first-child{
  border-top-left-radius:0rem;
  border-top-right-radius:0rem;
  }
.list-group-item:last-child{
  border-bottom-right-radius:0rem;
  border-bottom-left-radius:0rem;
  }

.list-group-item {
  font-size: 18px;
  font-weight: bold;
  background-color:#564892;
  padding: 0px;
  padding-top: 25px; 
  border-color: transparent;
}

.list-group-item2 {
  font-weight: bold;
  /* background-color: black; */
}
</style>