<template>
  <div>
    <div>
      <b-card tag="article" style="max-width: 500px;" class="mt-5 mx-auto">
      <img src='../../../src/img/logo-head.png' class="mt-4" alt="로고" height="40"/>
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
            <b-button squared variant="primary" size="sm" v-b-modal.modal-scrollable class="go-term"><b>약관 보기</b></b-button>
            <b-modal id="modal-scrollable" scrollable>
              <p class="my-2">
              <b>Translately 회원가입 약관</b><br>
              제1조 (목적) 이 약관은 Translately를 이용함에 있어 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.
              
              </p>
            </b-modal>
          </div>
          <button @click="signup" class="mt-2 btn btn-primary btn-lg btn-block" id="signupbtn"><b>가입하기</b></button>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupView',
  methods: {
    signup() {
      // const idtext = /^[A-Za-z0-9+]{8,}$/;
      // if (idtext.test(this.signupData.email) == false) {
      //   alert("아이디를 8자 이상 작성해주세요.")
      //   return
      // }
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
.loginView {
    text-align: left;
    margin: 10px 40px;
}

.form-group {
  margin: 0px 0px 10px;
}

#signupbtn {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 6px;
}

</style>
