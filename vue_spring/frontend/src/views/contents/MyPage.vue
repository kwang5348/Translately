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
          <label for="input-default">잔여시간</label>
          </b-col>
          <b-col sm="5">
          <p>{{ userdata.remaintime }} 초</p>
          </b-col>
        </b-row>

        <b-row class="my-1">
          <b-col sm="3">
          <label for="input-default">이름</label>
          </b-col>
          <b-col sm="5">
          <p>{{ userdata.name }}</p>
          </b-col>
        </b-row>

      <br>
      <div>
        <b-button @click="modify" variant="outline-primary">개인정보 수정</b-button>
      <!-- style="background-color:#564892;" -->
      </div>
    <!-- </b-col> -->
      </b-container>
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
  methods: {
    modify() {
      this.$router.push('/contents/modify')
    },
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
.font-ment {
  font-family: 'InfinitySans-RegularA1';
}
</style>