<template>
  <div id="app">
     <!-- v-if="navbar" -->
    <div class="container-fluid m-0 p-0"><router-view 
      @upload-file="uploadFile" 
      :isLogin="isLogin" 
      :video="video" 
      :subtitles="subtitles"
      :translateBusy="translateBusy" 
      @submit-login-data="login" 
      @submit-upload-option="uploadOption"
      @submit-signup-data="signup"
      @logout="logout" />
    </div>

  </div>


</template>

<script>
import axios from 'axios';
// import { delete } from 'vue/types/umd';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8301'

export default {
  name: 'app',
  components: {
  },
  data() {
    return {
      isLogin: false,
      navbar : true,
      subtitles: undefined,
      video: undefined,
      uploadData: null,
      translateBusy: true,
      subTranslateData: {
        "buildId": 0,
        "finalBuild": 0,
        "transcript": null,
        "fileInfo": undefined,
        "vttResult": null 
      }
    }
  },
  created() {
     if (this.$cookies.isKey('auth-token')) {
      this.isLogin = true
    } else {
      this.isLogin = false
    }
  },
  methods: {
    setCookie(key) {
      this.$cookies.set('auth-token', key)
    },
    socialLogin(){
      this.isLogin = true
      this.navbar = false
      this.$router.push('/user')
    },
    login(loginData) {
      const data = {
        "email": loginData.uid,
        "password": loginData.password
      }
      axios.post(`${SERVER_URL}/api/account/login`, data)
      // .then(res => {console.log(res)})
      .then(response => {
        this.setCookie(response.data.object.token)
        this.isLogin = true
        this.navbar = false
        this.$router.push('/contents/tutorial')
      })
      .catch(err => {
        console.log(err)
        alert('이메일과 비밀번호를 확인해 주세요!')
      })
    },
    logout() {
      this.$cookies.remove('auth-token')
      this.isLogin = false
      this.navbar = true
      this.$router.push({name: "Home"})
      // if (this.$cookies.isKey('auth-token')) {
      //   const requestHeader = {
      //     headers: {
      //       Authorization: `Token ${this.$cookies.get('auth-token')}`
      //     }
      //   }
      //   axios.post(`${SERVER_URL}/rest-auth/logout/`, null, requestHeader)
      //   .then(() => {
      //     this.$cookies.remove('auth-token')
      //     this.isLogin = false
      //     this.navbar = true
      //     this.$router.push('/')
      //   })
      // } else {
        // const auth2 = gapi.auth2.getAuthInstance();
        // auth2.signOut().then(function () {
        //   console.log('User signed out.');
        // });
      // }
    },
    signup(signupData) {
      const data = {
        email: signupData.email,
        password: signupData.password1,
        name: signupData.name,
      }
      axios.post(`${SERVER_URL}/api/account/join/`, data)
      .then(response => {
        this.setCookie(response.data.object.token)
        this.setCookie(response.data.object.token)
        this.isLogin = true
        this.$router.push('/contents/tutorial')
        })
      .catch(err => {console.log(err)})
    },
    uploadFile(video) {
      this.video = video
    },
    uploadOption(ud) {
      console.log("파일을 분할합니다.")
      this.uploadData = ud
      delete this.uploadData.option1
      delete this.uploadData.option2
      console.log(this.uploadData)
      if (this.$cookies.isKey("auth-token")) {
        axios.post(`${SERVER_URL}/api/wav/analysis/`, this.uploadData, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
        .then(response => {
          console.log(response)
          const translateCount = parseInt(response.data.data.replace("개의 파일분할이 가능합니다.", ""))
          this.subTranslateData.finalBuild = translateCount - 1
          this.subTranslateData.fileInfo = response.data.object
          for (let i = 0; i < translateCount; i++) {
            console.log(`${i+1}번째 번역을 시작합니다.`)
            this.subTranslateData.buildId = i
            console.log(this.subTranslateData)
            axios.post(`${SERVER_URL}/api/wav/subTranslate/`, this.subTranslateData, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
            .then(response => {
              console.log(response)
              console.log(`${i+1} 번째 번역이 끝났습니다.`)
              this.subtitles = response.data.object.transcript
              this.subTranslateData.transcript = response.data.object.transcript
              this.subTranslateData.vttResult = response.data.object.vttResult
            })
          }
          this.translateBusy = false
        })
        .catch(response => {
          console.log(response)
          this.subtitles = [{"eng":"ERROR ", "kor":"에러", "startTime":0 , "endTime":0}]
          this.translateBusy = false
          this.$router.push('/createcaption')
        })
      } else {
        this.$router.push("/accounts/login")
      } 
    },
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#whole-navbar {
  margin-bottom: 0;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

#login-button {
  color: white;
  background-color: rgb(89, 94, 158);
  border-style: none;
  border-radius: 0;
  font-size: 1rem;
  padding: 4px 6px;
}

#sub-button {
  border-radius: 0;
}

#search-blank {
  width: 400px;
  border-radius: 0;
}

#lan {
  padding: 10px;
  /* margin: 10px; */
}

@font-face {
  font-family: 'InfinitySans-RegularA1';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

</style>
