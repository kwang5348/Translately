<template>
  <div class="container whole">
    <div>
      <b-card tag="article" style="max-width: 500px; margin-top: 7px;" 
      class="mx-auto">
      <div style="width: 400px;" class="row mx-auto">
        <div class="col-3"></div>
        <div class="col-6">
            <a href="/" class="d-flex justify-content-center"><img src='../../../src/img/logo-letter2.png' href="/" class="mt-4" alt="로고" height="40"/></a>
        </div>
        <div class="d-flex justify-content-right col-3 pr-0 mt-1">
          <b-button class="login_button mt-4 mr-0 ml-auto" size="sm" variant="primary" @click="goBack"><b>뒤로</b></b-button>
        </div>
      </div>
      <h4 class="mt-1" style="color: black;"><b>회원가입</b></h4>
      <p style="color: black; font-size: 12px;">회원가입하세요. 가입하면 게시판을 열람할 수 있습니다.</p>
        <div class="loginView">
          <div class="form-group">
            <label for="exampleInputEmail1">E-mail</label>
            <input v-model="signupData.email" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            <small id="emailHelp" class="form-text text-muted">아이디를 작성해주세요.</small>
          </div>
          <div class="form-group">
            <label for="exampleInputEmail1">nickname</label>
            <input v-model="signupData.name" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            <small id="emailHelp" class="form-text text-muted">아이디를 작성해주세요.</small>
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">비밀번호</label>
            <input v-model="signupData.password1" type="password" class="form-control" id="exampleInputPassword1">
            <small id="emailHelp" class="form-text text-muted">비밀번호를 작성해주세요.</small>
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">비밀번호 확인</label>
            <input v-model="signupData.password2" type="password" class="form-control" id="exampleInputPassword1">
            <small id="emailHelp" class="form-text text-muted">비밀번호를 다시 한번 입력해주세요.</small>
          </div>
          <!-- 약관 추가 -->
          <div class="d-flex justify-content-between">
            <label>
              <input v-model="signupData.isTerm" type="checkbox" id="term"/>
              <span> 약관에 동의합니다</span>
            </label>
            <b-button squared variant="primary" size="sm" v-b-modal.modal-scrollable class="login_button go-term"><b>약관 보기</b></b-button>
            <b-modal id="modal-scrollable" scrollable ok-only>
              <p class="my-2">
              <b>Translately 회원가입 약관</b><br>
              제1조 (목적) 이 약관은 Translately를 이용함에 있어 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.
              
              </p>
            </b-modal>
          </div>
          <button @click="signup" class="login_button mt-2 btn btn-primary btn-lg btn-block"><b>가입하기</b></button>
          <!-- <div class="d-flex" align="center"> -->
            <!-- <b-col class="p-0 pt-2 ml-auto"><b-button size="sm" variant="primary" @click="gotoBack"><b>뒤로</b></b-button></b-col> -->
          <!-- </div> -->
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupView',
  methods: {
    gotoBack() {
      this.$router.go(-1)
    },
    signup() {
      if(this.signupData.password1 !== this.signupData.password2) {
        alert("비밀번호가 일치하지 않습니다.")
        return
      }
      if(this.signupData.isTerm == false) {
        alert("약관을 읽고 약관에 동의해 주세요.")
        return
      }
      this.$emit('submit-signup-data', this.signupData)
    }
  },
  data() {
    return {
      signupData:{
        email: null,
        name: null,
        password1: null,
        password2: null,
        isTerm: false,
      },
      isLogin: false
    }
  }
}   
</script>

<style scoped>
.login_button {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 4px 6px;
}

.loginView {
    text-align: left;
    margin: 10px 40px;
}

.form-group {
  margin: 0px 0px 10px;
}

.container {
  width: 100%;
}

</style>
