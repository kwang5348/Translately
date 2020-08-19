<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light" class="navbar2">
      <b-nav-text class="ml-auto mr-4" style="font-size: 15px;"><b><i class="fas fa-coins"></i> 잔여시간 : <b style="color: black; font-size: 1.2rem;">{{ userdata.remaintime }}</b> 초</b></b-nav-text>
      <b-nav-text class="ml-6 mr-4" style="font-size: 15px;"><b> <b style="color: black; font-size: 1.5rem;">{{ userdata.name }}</b> 님 안녕하세요!</b></b-nav-text>
    </b-navbar>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3a511.p.ssafy.io'

export default {
  name : 'Navbar2',
  data() {
    return {
      userdata: [],
      // email: null
    }
  },
  methods: {
    navbar() {
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
    // userstatus() {
    //   axios.get(`${SERVER_URL}/api/account/findByEmail`, this.email, {
    //     headers: {
    //       "jwt-auth-token": this.$cookies.get("auth-token")
    //     }
    //   })
    // }
      
  },
  created() {
    this.navbar()
  }
}
</script>

<style>
.navbar2 {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}


</style>