<template>
  <div>
    <!-- <div class="login"> -->
      <!-- <h1>Login</h1>
      username : <input type="text" v-model="loginData.username"><br>
      password : <input type="password" v-model="loginData.password"><br> -->
      <!-- <button @click="login">Login</button> -->
    <!-- </div> -->
    <div>
      <b-card
        tag="article"
        style="max-width: 500px;"
        class="mt-5 mx-auto"
      >
        <h1>Member Login</h1>
        <br>
        <form style="width: 400px;" class="mx-auto">
          <div class="form-group text-left">
            <label for="Input1">아이디</label><br>
            <input v-model="loginData.username" type="text" class="form-control" id="Input1" aria-describedby="emailHelp">
            <small class="form-text text-muted">아이디를 작성해주세요</small>
          </div>
          <div class="form-group text-left">
            <label for="Password1">비밀번호</label>
            <input v-model="loginData.password" type="password" class="form-control" id="Password1">
            <small class="form-text text-muted">비밀번호를 작성해주세요</small>
          </div>
          <div class="d-flex justify-content-right">
            <button type="submit" class="btn btn-lg btn-block btn-primary" id="login" @click="login">로그인</button>
          </div>
          <hr>
          <div class="exptext d-flex justify-content-between">
            <h4>SNS로 로그인</h4>
            <button v-google-signin-button="clientId" class="google-signin-button">Continue with Google</button>
          </div>
        </form>
        <!-- <h5> 회원가입을 하시면 더 다양하고 특별한 혜택을 즐기실 수 있어요.</h5> -->
        <hr>
        <a><router-link to="/accounts/signup">회원가입</router-link></a> 하러 가기
        <!-- <b-button href="#" variant="primary">Go somewhere</b-button> -->
        <!-- <div class="loginView">
          <h1>로그인</h1>
          <form style="width: 720px;" class="mx-auto">
            <div class="form-group text-left">
              <label for="Input1">아이디</label><br>
              <input v-model="loginData.username" type="text" class="form-control" id="Input1" aria-describedby="emailHelp">
              <small class="form-text text-muted">아이디를 작성해주세요</small>
            </div>
            <div class="form-group text-left">
              <label for="Password1">비밀번호</label>
              <input v-model="loginData.password" type="password" class="form-control" id="Password1">
              <small class="form-text text-muted">비밀번호를 작성해주세요</small>
            </div>
            <div class="d-flex justify-content-right">
              <button type="submit" class="btn btn-primary" @click="login">확인</button>
            </div>
          </form> -->
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
        username: null,
        password: null
      },
      clientId: '382938302924-8nrvj4ljcins34ne37d1fqg5q5rp85ro.apps.googleusercontent.com',
    }
  },
  methods: {
    login() {
      this.$emit('submit-login-data', this.loginData)
    },
    OnGoogleAuthSuccess (idToken) {
      this.$emit('submit-social-login-data')
      console.log(idToken)
      // Receive the idToken and make your magic with the backend
    },
    OnGoogleAuthFail (error) {
      console.log(error)
    }
  }
}
</script>

<style scoped>
/* .loginView {
    text-align: left;
    margin: 50px;
} */

.google-signin-button {
  color: white;
  background-color: rgba(66, 133, 244)
  ;
  height: 50px;
  font-size: 16px;
  border-radius: 2px;
  padding: 5px 10px 5px 10px;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.19);
}

#login {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 5px;
}

</style>