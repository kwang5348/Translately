<template>
  <div id="app">
     <!-- v-if="navbar" -->
    <div class="container-fluid m-0 p-0"><router-view 
      @upload-file="uploadFile" 
      :isLogin="isLogin" 
      :video="video" 
      :subtitles="subtitles" 
      @submit-login-data="login" 
      @submit-u-d="upload"
      @submit-signup-data="signup"
      @logout="logout" />
    </div>
    <!-- <router-link @click.native="logout" to="/accounts/logout">Logout</router-link> -->
 
  </div>


</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'

export default {
  name: 'app',
  data() {
    return {
      isLogin: false,
      navbar : true,
      ud: {},
      subtitles: undefined,
      video: undefined,
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
        console.log(response)
        this.setCookie("coooooookies")
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
        console.log(response)
        this.setCookie('cooookieees')
        this.isLogin = true
        this.$router.push('/contents/tutorial')
        })
      .catch(err => {console.log(err)})
    },
    uploadFile(video) {
      this.video = video
    },
    upload(uploadData) {
      this.ud = uploadData
      console.log(uploadData)
      axios.get(`${SERVER_URL}/api/translate?start=${uploadData.start}&target=${uploadData.target}&fileName=${uploadData.name}`)
      .then(response => {
        console.log(response)
        this.subtitles = response.data.object
        this.$router.push('/createcaption')
        })
      .catch(response => {
        console.log(response)
        this.subtitles = [{"eng":"ERROR ", "kor":"에러", "startTime":0 , "endTime":0}]
        this.$router.push('/createcaption')
      })
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

</style>
