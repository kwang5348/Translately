<template>
  <body>
    <!-- <h1>안녕! 여기는 메인</h1> -->
    <Navbar1 :isLogin="isLogin" @logout="logout" />
    <div class="home">
      <img src='../img/main.png' alt="메인 임시" style="width: 100%"/>
      <!-- <router-view @submit-social-login-data="socialLogin" @submit-login-data="login" @submit-signup-data="signup"/> -->
          <!-- <div class="img col-6"><div class="content mt-4">
              <h1 class="font-weight-bold">내가 원하는 영상을</h1><h1 class="font-weight-bold">원하는 언어로</h1>
              <hr style="border-color:transparent"><h5>영상을 나에게 필요한 자막으로 변환해보세요</h5>
              <div class="btn btn-warning text-white justify-content-center" style="margin-top: -15px; font-weight: bold; font-size: 20px;">
              <router-link to="/contents/tutorial">튜토리얼 보러가기</router-link></div>
            </div></div> -->
        </div>    
  </body>
</template>

<script>
import Navbar1 from "@/components/Navbar1.vue"


export default {
  name: 'Main',
  components: {
    Navbar1,
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
body {
/* background-color: #D9E5FF;
background: url("../img/main.png") no-repeat center center fixed;
-webkit-background-size: cover;
-moz-background-size: cover;
-o-background-size: cover;
background-size: cover;
height: 700px; */
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

</style>