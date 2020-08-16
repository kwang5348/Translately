<template>
  <body>
    <Navbar1 :isLogin="isLogin" @logout="logout" />
    <div class="home">
      <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="../img/main-modal1.png" class="d-block w-100" alt="모달 이미지1">
            <div class="carousel-caption d-none d-md-block">
              <!-- <h5>First slide label</h5> -->
              <b-button class="start-button" href="/contents/translate" 
                style="background: transparent; border:2px solid white; border-radius: 0px; font-weight: bold; font-size: 24px;">
                  자막 변환 시작하기
                </b-button>
            </div>
          </div>
          <div class="carousel-item">
            <img src="../img/main-modal2.png" class="d-block w-100" alt="모달 이미지2" style="margin: 0px auto">
            <b-button variant="primary" class="start-button" href="/contents/translate" 
                style="font-weight: bold; font-size: 25px;">
                  자막 변환 시작하기
                </b-button>
          </div>
          <div class="carousel-item">
            <img src="../img/main-modal3.png" class="d-block w-100" alt="모달 이미지3">
            <b-button variant="primary" class="start-button" href="/contents/translate" 
                style="font-weight: bold; font-size: 25px;">
                  자막 변환 시작하기
                </b-button>
          </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
      <img src='../img/main-1.png' alt="메인 임시" style="width: 100%"/>
      <img src='../img/main-2.png' alt="메인 임시" style="width: 100%"/>
      <img src='../img/main-3.png' alt="메인 임시" style="width: 100%"/>
      <img src='../img/main-4.png' alt="메인 임시" style="width: 100%"/>
      <img src='../img/main-5.png' alt="메인 임시" style="width: 100%"/>
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

.start-button{
  display : block;
  margin : -100px auto 50px auto;
  width: 250px;
}

</style>
