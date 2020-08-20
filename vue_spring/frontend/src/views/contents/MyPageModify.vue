<template>
  <div class="font-ment" style="margin: 40px;">
    <div style="justify-content: center;">
      <h2 style="text-align: left;">{{ userdata.name }} 님의 프로필 <b-icon icon="person-fill"></b-icon></h2>
    </div>
    <hr>
    <br>
    <div>
      <h4 style="text-align: left;">회원 정보 수정</h4>
    </div>
    <br>
    <div>
      <b-container fluid>
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
          <label for="input-default">이메일 주소</label>
          </b-col>
          <b-col sm="3">
          <p>{{ userdata.email }}</p>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>
        
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
          <label for="input-default">잔여시간</label>
          </b-col>
          <b-col sm="3">
          <p>{{ userdata.remaintime }} 초</p>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>

        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
            <label for="input-none">이름</label>
          </b-col>
          <b-col sm="3">
            <b-form-input
              id="input-none" 
              :state="null"
              v-model="name_input"
              placeholder="닉네임을 입력하세요."
            ></b-form-input>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>
        
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
          <label for="input-live">기존 비밀번호</label>
          </b-col>
          <b-col sm="3">
          <div role="group">
            <b-form-input
              id="input-live"
              type="password"
              v-model="pw_input1"
              :state="PwState1"
              aria-describedby="input-live-help input-live-feedback"
              placeholder="현재 비밀번호를 입력하세요."
              trim
            ></b-form-input>
            <b-form-invalid-feedback id="input-live-feedback">
            Your present password.
            </b-form-invalid-feedback>
          </div>
          </b-col>
          <b-col sm="4">
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
          <label for="input-live">새 비밀번호</label>
          </b-col>
          <b-col sm="3">
          <div role="group">
            <b-form-input
              id="input-live"
              type="password"
              v-model="pw_input2"
              :state="PwState2"
              aria-describedby="input-live-help input-live-feedback"
              placeholder="새 비밀번호를 입력하세요."
              trim
            ></b-form-input>
            <b-form-invalid-feedback id="input-live-feedback">
            Longer than 8 characters.
            </b-form-invalid-feedback>
          </div>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="3">
          <label for="input-live">새 비밀번호</label>
          </b-col>
          <b-col sm="3">
          <div role="group">
            <b-form-input
              id="input-live"
              type="password"
              v-model="pw_input3"
              :state="PwState3"
              aria-describedby="input-live-help input-live-feedback"
              placeholder="새 비밀번호와 일치하는지 확인하세요."
              trim
            ></b-form-input>
            <b-form-invalid-feedback id="input-live-feedback">
            Longer than 8 characters.
            </b-form-invalid-feedback>
          </div>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>
      <br>
      <b-row class="my-1">
        <b-col sm="4"></b-col>
        <b-col sm="1">
        <b-button @click="change_pw" variant="outline-dark">저장</b-button>
        </b-col>
        <b-col sm="1">
        <b-button @click="go_to_mypage" variant="outline-dark">뒤로</b-button>
        </b-col>
        <b-col sm="6"></b-col>
      </b-row>
      </b-container>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3a511.p.ssafy.io/'

export default {
  name : 'MyPage',
  computed: {
    PwState1() {
      return this.pw_input1 == this.userdata.password? true : false
    },
    PwState2() {
      return this.pw_input2.length >= 8? true : false
    },
    PwState3() {
      return this.pw_input3 == this.pw_input2? true : false
    },
  },
  data() {
    return {
      pw_input1:'',
      pw_input2:'',
      pw_input3: null,
      name_input: '',
      userdata: []
    }
  },
  methods: {
    go_to_mypage() {
      this.$router.push('/contents/mypage')
    },
    setCookie(key) {
    this.$cookies.set('auth-token', key, "180MIN")
    },
    mypage() {
        axios.get(`${SERVER_URL}/api/account/info`, {
            headers: {
                "jwt-auth-token": this.$cookies.get("auth-token")
            }
        })
        .then(response => {
            this.userdata = response.data.object
            this.name_input = this.userdata.name
        })
    },
    change_pw() {
      const data = {
        "password": this.pw_input2,
        "name": this.name_input,
        "email": this.userdata.email
      }
      const login_data = {
        "email": this.userdata.email,
        "password": this.pw_input2
      }
      if (this.pw_input1 == this.userdata.password && this.pw_input3 == this.pw_input2) {

        if (this.pw_input2.length >= 8) {
          axios.post(`${SERVER_URL}/api/account/modify`, data , {
            headers: {
              "jwt-auth-token": this.$cookies.get("auth-token")
          }
          })
          .then(() => {
            console.log("비밀번호 변경 성공")
            this.userdata.password = this.pw_input2
            this.$cookies.remove('auth-token')
            axios.post(`${SERVER_URL}/api/account/login`, login_data)
            .then(response => {
              this.setCookie(response.data.object.token)
              alert("개인정보 수정완료되었습니다.")
              this.$router.push('/contents/mypage')
            })
          })
        } else {
          alert("비밀번호 형식이 틀렸습니다.")
        }
      } else{
        alert("비밀번호가 일치하지 않습니다.")
      }
    this.pw_input1 = ''
    this.pw_input2 = ''
    }
  },
  created() {
    this.mypage()
  }
  
}
</script>

<style>
.font-ment {
  font-family: 'InfinitySans-RegularA1';
}
</style>