<template>
  <div class="font-ment" style="margin: 40px;">
    <div class="container MyPage_container">
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
            <p>{{ remainTime }} 초</p>
            </b-col>
            <b-col sm="4"></b-col>
          </b-row>

          <b-row class="my-1">
            <b-col sm="2"></b-col>
            <b-col sm="3">
            <label for="input-default">닉네임</label>
            </b-col>
            <b-col sm="3">
            <p>{{ userdata.name }}</p>
            </b-col>
            <b-col sm="4"></b-col>
          </b-row>

        <br>
        <b-row class="my-1">
          <b-col sm="2"></b-col>
          <b-col sm="6">
          <b-button @click="modify" variant="outline-dark">개인정보 수정</b-button>
          </b-col>
          <b-col sm="4"></b-col>
        </b-row>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3a511.p.ssafy.io/'

export default {
  name : 'MyPage',
  data() {
    return {
      pw_input1:'',
      pw_input2:'',
      name_input: '',
      userdata: []
    }
  },
  props: {
    remainTime: {
      type: Number
    }
  },
  methods: {
    modify() {
      this.$router.push('/contents/modify')
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
            
        })
    },
  },
  created() {
    this.mypage()
  }
  
}
</script>

<style>
.MyPage_container {
  margin-left: 20vh !important;
  width: 70% !important;
}
.font-ment {
  font-family: 'InfinitySans-RegularA1';
}
</style>