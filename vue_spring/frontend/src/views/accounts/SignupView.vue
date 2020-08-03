<template>
  <div>
    <div>
      <b-card
        tag="article"
        style="max-width: 500px;"
        class="mt-5 mx-auto"
      >
      <h2>회원가입</h2>
      <p style="font-size: 15px;">회원가입하세요. 가입하면 게시판을 열람할 수 있습니다.</p>
        <div class="loginView">
            <div class="form-group">
                <label for="exampleInputEmail1">아이디</label>
                <input v-model="signupData.username" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
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
                <span>약관에 동의합니다</span>
              </label>
              <b-button v-b-modal.my-modal class="go-term">약관 보기</b-button>
              <b-modal id="my-modal">
                <p>Translately 회원가입 약관</p>
                <p>제1조 (목적) 이 약관은 Translately를 이용함에 있어 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.</p>
              </b-modal>
            </div>
            <button @click="signup" type="submit" class="btn btn-primary btn-lg btn-block" id="signup">가입하기</button>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
    name: 'SignupView',
    data() {
        return {
            signupData: {
                username: null,
                password1: null,
                password2: null,
            }
        }
    },
    methods: {
        signup() {
          // const idtext = /^[A-Za-z0-9+]{1,10}$/;
          // if (idtext.test(this.signupData.id) == false) {
          //     alert("아이디 양식을 지켜주세요!")
          //     return
          // }
          // if(this.signupData.isTerm == false) {
          //     alert("약관을 읽고 약관에 동의해 주세요.")
          //     return
          // }
          // if(this.signupData.password !== this.signupData.passwordConfirm) {
          //     alert("비밀번호가 일치하지 않습니다.")
          //     return
          // }
          this.$emit('submit-signup-data', this.signupData)
        }
    },
    // data() {
    //     return {
    //         signupData:{
    //             id: '',
    //             password: '',
    //             passwordConfirm: '',
    //             isTerm: false,
    //             passwordType:"password",
    //             passwordConfirmType:"password",
    //         },
    //         isLogin: false
    //     }
    // }

}   
</script>

<style scoped>
.loginView {
    text-align: left;
    margin: 50px;
}

#signup {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 5px;
}

</style>
