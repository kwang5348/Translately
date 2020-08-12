<template>
  <body>
    <Navbar1 :isLogin="isLogin" @logout="logout" />
    <div class="home">
      <!-- <router-view @submit-social-login-data="socialLogin" @submit-login-data="login" @submit-signup-data="signup"/> -->
          <img src='../img/main.png' alt="메인 임시" style="width: 100%"/>
          <div class="btn btn-primary text-white justify-content-center" style="margin-top: -400px; margin-left: 840px; font-weight: bold; font-size: 30px;">
            <router-link to="/contents/translate">자막 변환 시작하기</router-link>
          </div>
          <img src='../img/main-2.png' alt="메인 임시" style="width: 100%"/>
          <img src='../img/main-3.png' alt="메인 임시" style="width: 100%"/>
          <!-- <router-view @submit-social-login-data="socialLogin" @submit-login-data="login" @submit-signup-data="signup"/> -->
          <!-- 메인 멘트 -->
          <!-- <div class="img col-6"><div class="content mt-4">
              <h1 class="font-weight-bold">내가 원하는 영상을</h1><h1 class="font-weight-bold">원하는 언어로</h1>
              <hr style="border-color:transparent"><h5>영상을 나에게 필요한 자막으로 변환해보세요</h5>
              <div class="btn btn-warning text-white justify-content-center" style="margin-top: -15px; font-weight: bold; font-size: 20px;">
                <router-link to="/contents/tutorial">튜토리얼 보러가기</router-link>
              </div>
          </div></div> -->
        </div>
      <Footer />    
  </body>
</template>

<script>
// @ is an alias to /src
// import UploadFiles from "./../components/UploadFiles"
import Navbar1 from "@/components/Navbar1.vue"
import Footer from "@/components/Footer.vue"

export default {
  name: 'Home',
  components: {
    // UploadFiles,
    Navbar1,
    Footer
  },
  data() {
    return {
      ud: {}
    }
  },
  props: {
    isLogin: {
      type: Boolean
    }
  },
  methods: {
    uploadFile(video) {
      this.$emit('upload-file', video)
    },
    uploadOption(uploadOption) {
      this.$emit('submit-upload-option', uploadOption)
    },
    logout() {
      this.$emit('logout')
    }
  },
}
</script>


<style scoped>
.row {
  float: right;
  position: relative;
  left: -35%;
  top: -30%;

}

.img .content {
  position: absolute;
  top:50%;
  left:20%;
  transform: translate(-50%, -50%);                                                                   
  font-size: 10rem;
  color: black;
  z-index: 2;
  text-align: left;
}

.img {
  margin-top: 300px;
  margin-left: 700px;
}
</style>
