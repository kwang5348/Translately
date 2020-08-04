<template class="js">
  <body>
    <Navbar1/>
    <div class="home">
      <!-- <router-view @submit-social-login-data="socialLogin" @submit-login-data="login" @submit-signup-data="signup"/> -->
      <!-- <div class="bg">
        <img src="..\img\main-bg-md2.jpg" alt="메인 배경사진">
      </div> -->
      
        <div class="row">
          <!-- <router-view :uploadData="uploadData" @submit-upload-data="upload"/> -->
          <div class="img col-6">
            <div class="content mt-4">
              <h1 class="font-weight-bold">내가 원하는 영상을</h1>
              <h1 class="font-weight-bold">원하는 언어로</h1>
              <hr style="border-color:transparent">
              <hr style="border-color:transparent">
              <h5>영상을 나에게 필요한 자막으로 변환해보세요</h5>
              <div class="btn btn-warning text-white justify-content-center" style="margin-top: -15px; font-weight: bold; font-size: 20px;">
              <router-link to="/contents/tutorial">튜토리얼 보러가기</router-link>
              </div>
            </div>
          </div>
          <div class="col-6">
            <div class="container" style="width:600px position: absolute;">
              <img src="..\img\upload-box.png" alt="업로드 박스" class="upload-box">
            </div>
            <div style="width:600px position: absolute;">
              <upload-files @upload-file="uploadFile" @submit-upload-data="upload"></upload-files>
            </div>
          </div>
        </div>    
      </div>
    <Footer class="fixed-bottom"/>
  </body>
</template>

<script>
// @ is an alias to /src
import UploadFiles from "./../components/UploadFiles"
import Navbar1 from "@/components/Navbar1.vue"
import Footer from "@/components/Footer.vue"

export default {
  name: 'Home',
  components: {
    UploadFiles,
    Navbar1,
    Footer
  },
  data() {
    return {
      ud: {}
    }
  },
  methods: {
    uploadFile(video) {
      this.$emit('upload-file', video)
    },
    upload(uploadData) {
      this.ud = uploadData
      this.$emit('submit-u-d', this.ud)
    },
  },
}
</script>


<style scoped>
body {
background-color: #D9E5FF;
background: url("../img/main-bg.jpg") no-repeat center center fixed;
-webkit-background-size: cover;
-moz-background-size: cover;
-o-background-size: cover;
background-size: cover;
height: 680px;
}

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

.upload-box {
  margin-top: -210px;
  margin-left: 1200px;
  width: 600px;
}


</style>
