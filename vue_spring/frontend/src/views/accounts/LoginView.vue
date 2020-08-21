<template>
  <div class="container whole" style="width: 100%; margin: auto;">
    <div>
      <b-card
        tag="article"
        style="max-width: 500px; margin-top: 50px;"
        class="mx-auto"
      >
        <div style="width: 400px;" class="row mx-auto">
          <div class="col-3"></div>
          <div class="col-6">
              <a href="/" class="d-flex justify-content-center"><img src='../../../src/img/logo-letter2.png' href="/" class="mt-4" alt="로고" height="40"/></a>
          </div>
          <div class="d-flex justify-content-right col-3 pr-0 mt-1">
            <b-button class="login_button mt-4 mr-0 ml-auto" size="sm" variant="primary" @click="goBack"><b>뒤로</b></b-button>
          </div>
        </div>

        <h3><b>로그인</b></h3>
        <br>
        <div style="width: 400px;" class="mx-auto">
          <div class="form-group text-left">
            <label for="Input1">이메일</label><br>
            <input v-model="loginData.email" type="text" class="form-control" id="Input1" aria-describedby="emailHelp">
            <small class="form-text text-muted">이메일을 작성해주세요</small>
          </div>
          <div class="form-group text-left">
            <label for="Password1">비밀번호</label>
            <input v-model="loginData.password" type="password" class="form-control" id="Password1">
            <small class="form-text text-muted">비밀번호를 작성해주세요</small>
          </div>
          <div class="d-flex justify-content-right">
            <button type="submit" class="login_button btn btn-lg btn-block btn-primary" @click="login"><b>로그인</b></button>
          </div>
          <!-- <div class="d-flex justify-content-between mt-3">
            <h4 class="mt-2"><b>SNS로 로그인</b></h4>
            <button v-google-signin-button="clientId" class="google-signin-button"></button>
          </div> -->
          <hr>
          <div class="d-flex justify-content-between">
            <p class="signintext mt-3 font" style="font-size: 15px">&#128505; 회원가입으로 더 다양한 혜택을 즐겨보세요.</p>
            <b-col><b-button class="text-white" style="border-color: transparent; background-color:grey;" size="sm" squared variant="dark" href="/accounts/signup"><b>회원가입</b></b-button></b-col>
          </div>
          <div>
            <!-- <b-col class="p-0"><b-button class="login_button" size="sm" variant="primary" @click="goBack"><b>뒤로</b></b-button></b-col> -->
          </div>
        </div>
      </b-card>
    </div>
  </div>


</template>

<script>
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";

export default {
  name: 'LoginView',
  components: {
    // GoogleLogin
  },
  created() {
    this.component = this;
  },
  data() {
    return {
      loginData: {
        email: null,
        password: null
      },
      clientId: '382938302924-8nrvj4ljcins34ne37d1fqg5q5rp85ro.apps.googleusercontent.com',
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    login() {
      this.$emit('submit-login-data', this.loginData)
    },
    // OnGoogleAuthSuccess (idToken) {
    //   this.$emit('submit-social-login-data')
    //   // Receive the idToken and make your magic with the backend
    // },
    // OnGoogleAuthFail (error) {
    // }
  }
}
</script>

<style scoped>

.google-signin-button {
  background: url( "../../img/btn_google_signin.png") no-repeat;
  background-size: 100%;
  width: 190px;
  height: 50px;
  outline: 0;
  border: 0;
  border-color: white;
}

.signintext {
  margin: 0;
}

.login_button {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 6px;
}


.font {
  font-family: 'InfinitySans-RegularA1'
}

</style>