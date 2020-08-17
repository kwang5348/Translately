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
          <b-col sm="3">
          <label for="input-default">이메일 주소</label>
          </b-col>
          <b-col sm="5">
          <p>{{ userdata.email }}</p>
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="3">
          <label for="input-default">닉네임</label>
          </b-col>
          <b-col sm="5">
          <p>{{ userdata.name }}</p>
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="3">
          <label for="input-live">기존 비밀번호</label>
          </b-col>
          <b-col sm="5">
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
        </b-row>
        <b-row class="my-1">
          <b-col sm="3">
          <label for="input-live">새 비밀번호</label>
          </b-col>
          <b-col sm="5">
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
        </b-row>
      </b-container>
    </div>
    <div>
      <b-button @click="change_pw" variant="outline-primary">Button</b-button>
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
  },
  data() {
    return {
      pw_input1:'',
      pw_input2:'',
      userdata: []
    }
  },
  methods: {
    setCookie(key) {
    this.$cookies.set('auth-token', key, "30MIN")
    },
    mypage() {
        console.log("start")
        axios.get(`${SERVER_URL}/api/account/info`, {
            headers: {
                "jwt-auth-token": this.$cookies.get("auth-token")
            }
        })
        .then(response => {
            console.log(response)
            this.userdata = response.data.object
        })
    },
    change_pw() {
      const data = {
        "password": this.pw_input2,
        "name": this.userdata.name,
        "email": this.userdata.email
      }
      if (this.pw_input2.length >= 8) {
        console.log("비밀번호 변경 성공")
        axios.post(`${SERVER_URL}/api/account/modify`, data , {
        headers: {
            "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
        .then(response => {
            console.log(response)
            // this.$cookies.remove('auth-token')
            // this.setCookie(response.data.object.token)
        })
        alert("비밀번호 변경 성공")
        // this.$router.push('/contents/mypage')
        this.mypage()
      } else {
        alert("8글자 이상으로")
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