<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light" class="navbar2">
      <b-nav-text class="ml-auto mr-4" style="font-size: 15px;"><b> {{ userdata.name }} 님 안녕하세요!</b></b-nav-text>
    </b-navbar>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3a511.p.ssafy.io/'

export default {
  name : 'Navbar2',
  data() {
    return {
      userdata: []
    }
  },
  methods: {
    setCookie(key) {
      this.$cookies.set('auth-token', key)
      },
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
        }
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