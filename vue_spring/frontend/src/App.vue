<template>
  <div id="app">
     <!-- v-if="navbar" -->
    <div class="container-fluid m-0 p-0"><router-view @upload-file="uploadFile" :video="video" :subtitles="subtitles" @submit-u-d="upload"/></div>
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
      axios.post(`${SERVER_URL}/rest-auth/login/`, loginData)
      // .then(res => {console.log(res)})
      .then(response => {
        this.setCookie(response.data.key)
        this.isLogin = true
        this.navbar = false
      })
      .catch(err => console.log(err))
      this.$router.push('/user')
    },
    logout() {
      if (this.$cookies.isKey('auth-token')) {
        const requestHeader = {
          headers: {
            Authorization: `Token ${this.$cookies.get('auth-token')}`
          }
        }
        axios.post(`${SERVER_URL}/rest-auth/logout/`, null, requestHeader)
        .then(() => {
          this.$cookies.remove('auth-token')
          this.isLogin = false
          this.navbar = true
          this.$router.push('/')
        })
      } else {
        // const auth2 = gapi.auth2.getAuthInstance();
        // auth2.signOut().then(function () {
        //   console.log('User signed out.');
        // });
      }
    },
    signup(signupData) {
      axios.post(`${SERVER_URL}/rest-auth/signup/`, signupData)
      .then(response => {
        this.setCookie(response.data.key)
        this.isLogin = true
        this.$router.push('/')
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
        this.subtitles = [{"eng":"Some of you may have seen in school that the surface area of a sphere is 4 pi r squared formula given that it's a clean multiple of the more popular pi r squared the area of a circle with the same radius, but have you ever wondered why this is true and I don't just mean proving the for pi r squared formula. ","kor":"여러분 중 몇몇은 학교에서 구면의 표면적이 4 pi r 제곱 공식이라는 것을 봤을지도 ","startTime":2.9,"endTime":8.133898305084749},{"eng":"Some of you may have seen in school that the surface area of a sphere is 4 pi r squared formula given that it's a clean multiple of the more popular pi r squared the area of a circle with the same radius, but have you ever wondered why this is true and I don't just mean proving the for pi r squared formula. ","kor":"모르지만, 그것이 같은 반지름을 가진 원의 면적을 제곱한 보다 더 인기 있는 pi ","startTime":8.133898305084749,"endTime":13.149717514124301},{"eng":"Some of you may have seen in school that the surface area of a sphere is 4 pi r squared formula given that it's a clean multiple of the more popular pi r squared the area of a circle with the same radius, but have you ever wondered why this is true and I don't just mean proving the for pi r squared formula. ","kor":"r의 깨끗한 배수라는 것을 본 적이 있다. 하지만 왜 이것이 사실인지 궁금해한 ","startTime":13.149717514124301,"endTime":17.947457627118624},{"eng":"Some of you may have seen in school that the surface area of a sphere is 4 pi r squared formula given that it's a clean multiple of the more popular pi r squared the area of a circle with the same radius, but have you ever wondered why this is true and I don't just mean proving the for pi r squared formula. ","kor":"적이 있는가? 나는 단지 pi r 제곱 공식을 증명하려는 것이 아니다.","startTime":17.947457627118624,"endTime":22.2},{"eng":"I mean viscerally healing to your bones a connection between this surface area and these four circles how lovely would it be if there were some subtle shift in perspective that shows how you could nicely and perfectly fit these four circles on to the sphere's surface. ","kor":"내 말은 이 네 개의 원과 이 네 개의 원 사이의 연관성을 뼈에 본능적으로 ","startTime":22.2,"endTime":27.200000000000056},{"eng":"I mean viscerally healing to your bones a connection between this surface area and these four circles how lovely would it be if there were some subtle shift in perspective that shows how you could nicely and perfectly fit these four circles on to the sphere's surface. ","kor":"치유한다는 뜻이야. 만약 이 네 개의 원들을 어떻게 구 표면에 잘 완벽하게 ","startTime":27.200000000000056,"endTime":32.20000000000011},{"eng":"I mean viscerally healing to your bones a connection between this surface area and these four circles how lovely would it be if there were some subtle shift in perspective that shows how you could nicely and perfectly fit these four circles on to the sphere's surface. ","kor":"맞출 수 있는지를 보여주는 미묘한 관점의 변화가 있다면 얼마나 사랑스러울까.","startTime":32.20000000000011,"endTime":37.2},{"eng":"Nothing can be quite that simple since the curvature of a sphere surface is different from the curvature of a flat plane, which is why trying to fit say a piece of paper around the sphere it it just doesn't work. ","kor":"구면의 곡률이 평면의 곡률과 다르기 때문에 ","startTime":37.2,"endTime":41.492957746478865},{"eng":"Nothing can be quite that simple since the curvature of a sphere surface is different from the curvature of a flat plane, which is why trying to fit say a piece of paper around the sphere it it just doesn't work. ","kor":"그렇게 간단한 것은 없다. 그래서 구면 둘레에 ","startTime":41.492957746478865,"endTime":46.143661971830966},{"eng":"Nothing can be quite that simple since the curvature of a sphere surface is different from the curvature of a flat plane, which is why trying to fit say a piece of paper around the sphere it it just doesn't work. ","kor":"종이 한 장씩을 붙이려고 하는 것이다.","startTime":46.143661971830966,"endTime":49.9},{"eng":"Nevertheless, I would like to show you to separate ways of thinking about the surface. ","kor":"그럼에도 불구하고, 나는 여러분에게 표면에 대해 생각하는 방법을 분리하는 것을 보여주고 싶다.","startTime":50.9,"endTime":54.9}]
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
