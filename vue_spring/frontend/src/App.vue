<template>
  <v-app id="app">
    <div class="container-fluid m-0 p-0"><router-view 
      @upload-file="uploadFile" 
      @create-navbar="createNavbar"
      :isLogin="isLogin" 
      :video="video" 
      :subtitles="subtitles"
      :remainTime="remainTime"
      :translateBusy="translateBusy"
      :translateProgress="translateProgress"
      :downloadUrl="downloadUrl"
      :downloadUrl1="downloadUrl1"
      @submit-login-data="login" 
      @submit-upload-option="uploadOption"
      @submit-signup-data="signup"
      @logout="logout"
      @destroy-create-caption="destroyCreateCaption"
      @submit-complete-translate="submitCompleteTranslate" />
    </div>
  
  </v-app>
</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'

export default {
  name: 'App',

  components: {
  },
  data() {
    return {
      remainTime: 0,
      isLogin: false,
      navbar : true,
      subtitles: undefined,
      video: undefined,
      uploadData: null,
      translateBusy: true,
      translateProgress: 0,
      downloadUrl: "",
      downloadUrl1: "",
      subTranslateData: {
        "buildId": 0,
        "finalBuild": 0,
        "transcript": null,
        "fileInfo": undefined,
        "vttResult": null 
      },
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
    submitCompleteTranslate(subtitleData) {
      this.subtitles = subtitleData.subtitles
      this.translateProgress =  100
      this.translateBusy = false
      this.downloadUrl = "http://i3a511.p.ssafy.io/api/vtt/download?fileLink=temp/" + subtitleData.subtitleName + "_" + subtitleData.start_sub_code + "_" + subtitleData.target_sub_code
      this.$router.push('/contents/createcaption')
    },
    translate(i) {
      console.log(`${i}번째 번역을 시작합니다.`)
      this.subTranslateData.buildId = i
      axios.post(`${SERVER_URL}/api/wav/subTranslate`, this.subTranslateData, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
      .then(response => {
        console.log(`${i} 번째 번역이 끝났습니다.`)
        const resSubtitles = response.data.object.transcript
        this.subtitles = resSubtitles
        this.translateProgress =  Math.round(100 * (i + 1) / (this.subTranslateData.finalBuild + 1))
        this.downloadUrl = "http://i3a511.p.ssafy.io/api/vtt/download?fileLink=" + response.data.object.fileInfo.subtitle_file + "_" + response.data.object.fileInfo.start_sub_code + "_" + response.data.object.fileInfo.target_sub_code
        this.subTranslateData.transcript = resSubtitles
        this.subTranslateData.vttResult = response.data.object.vttResult
        return response
      })
      .then(response => {
        if (this.subTranslateData.buildId >= response.data.object.finalBuild) {
          this.subTranslateData = {
            "buildId": 0,
            "finalBuild": 0,
            "transcript": null,
            "fileInfo": undefined,
            "vttResult": null
          }
          this.downloadUrl1 = this.downloadUrl
          this.getRemaintime()
          this.translateBusy = false
          return
        } else {
          this.translate(i+1)
          this.getRemaintime()
        }
      })
      .catch(err => {
        console.log(err)
        this.subtitles = [{"startsub":"ERROR ", "targetsub":"XXXXXXXXXXXXXXXXXXXXXXXerrXXXXXXXXXXXXXXXXXXXXXXX", "startTime":0 , "endTime":0}]
        this.translateBusy = false
      })
    },
    getRemaintime() {
      axios.get(`${SERVER_URL}/api/account/remainTime`, {
        headers: {"jwt-auth-token": this.$cookies.get("auth-token")}
      })
      .then(response => {
        if (response.data.status) {
          this.remainTime = response.data.object
          return response.data.object
        } else {
          alert("로그인 시간이 만료되었습니다. 다시 로그인 해 주세요!")
        }
      })
    },
    setCookie(key) {
      this.$cookies.set('auth-token', key, "180MIN")
    },
    socialLogin(){
      this.isLogin = true
      this.navbar = false
      this.$router.push('/user')
    },
    login(loginData) {
      const data = {
        "email": loginData.email,
        "password": loginData.password
      }
      axios.post(`${SERVER_URL}/api/account/login`, data)
      .then(response => {
        this.setCookie(response.data.object.token)
        this.isLogin = true
        this.navbar = false
        this.$router.push('/')
      })
      .catch(() => {
        alert('이메일과 비밀번호를 확인해 주세요!')
      })
    },
    logout() {
      this.$cookies.remove('auth-token')
      this.isLogin = false
      this.navbar = true
      this.$router.push({name: "Home"})
    },
    signup(signupData) {
      const data = {
        email: signupData.email,
        password: signupData.password1,
        name: signupData.name,
      }
      axios.post(`${SERVER_URL}/api/account/join`, data)
      .then(() => {
        delete data.name
        this.login(data)
        })
      .catch(err => {console.log(err)})
    },
    uploadFile(video) {
      this.video = video
    },
    uploadOption(ud) {
      this.subtitles = undefined
      this.translateBusy = true
      console.log("파일을 분할합니다.")
      this.uploadData = ud
      delete this.uploadData.option1
      delete this.uploadData.option2
      if (this.$cookies.isKey("auth-token")) {
        axios.post(`${SERVER_URL}/api/wav/analysis`, this.uploadData, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
        .then(response => {
          this.getRemaintime()
          if (this.remainTime >= response.data.object.duration) {
            this.$router.push('/contents/createcaption')
            const translateCount = parseInt(response.data.data.replace("개의 파일분할이 가능합니다.", ""))
            this.subTranslateData.finalBuild = translateCount - 1
            this.subTranslateData.fileInfo = response.data.object
            this.translate(0)
          } else {
            alert("잔여시간이 부족합니다.")
          }
        })
        .catch(() => {
          this.subtitles = [{"eng":"ERROR ", "kor":"에러", "startTime":0 , "endTime":0}]
          this.translateBusy = false
          this.$router.push('/contents/createcaption')
        })
      } else {
        this.$router.push("/accounts/login")
      } 
    },
    destroyCreateCaption() {
      this.subtitles = undefined
      this.downloadUrl = ""
      this.translateProgress = 0
    },
    createNavbar() {
      this.getRemaintime()
    }
  },
}

</script>

<style scoped>
.container-fluid {
   height: 100%;
}

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
}

@font-face {
  font-family: 'InfinitySans-RegularA1';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

</style>