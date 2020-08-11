<template>
  <div> 
  <!-- Image and text  이미지에 글자 -->
  <nav class="navbar navbar-expand-lg navbar-white bg-white" id="whole-navbar">
    <a class="navbar-brand ml-5" href="/">
      <img src='../img/logo-letter2.png' alt="서비스 로고" width="200"/>
    </a>
      <ul class="navbar-nav mx-auto" stylte="margin: 10px;">
        <!-- <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li> -->
        <!-- <li class="nav-seas -->
        <li class="nav-item active ml-2" style="color: black; font-size: 19px;">
          <a class="nav-link"><router-link to="/contents/tutorial">서비스 튜토리얼</router-link><span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item ml-2" style="color: black; font-size: 19px;">
          <a class="nav-link"><router-link to="/contents/tutorial">자막 변환</router-link><span class="sr-only"></span></a>
        </li>
        <li class="nav-item ml-2" style="color: black; font-size: 19px;">
          <a class="nav-link"><router-link to="/contents/tutorial">다운로드</router-link><span class="sr-only"></span></a>
        </li>
        <li class="nav-item ml-2" style="color: black; font-size: 19px;">
          <a class="nav-link"><router-link to="/contents/tutorial">FAQ</router-link><span class="sr-only"></span></a>
        </li>
      </ul>
      <ul class="navbar-nav mr-5">  
        <li class="nav-item form-inline my-2 my-lg-0" style="font-size: 19px;">
          <b-link class="nav-link" v-if="isLogin" @click="logout">
            Logout
          </b-link>
          <a v-else>
            <a><router-link to="/accounts/login">
              LOGIN</router-link></a>
            <!-- <a class="ml-3"><router-link to="/accounts/signup">Sign up</router-link></a> -->
          </a>
          <div id="lan">
            <b-link class="mx-1 font-weight-bolder" href="#">KOR</b-link>
            <b-link class="mx-1 font-weight-bolder text-muted" href="#">ENG</b-link>
          </div>
        </li>
      </ul>
  </nav>
  </div>
</template>

<script>
export default {
  name: 'Navbar1',
  props: {
    isLogin: {
      type: Boolean
    }
  },
  methods: {
    logout() {
      this.$emit('logout')
    }
  }
}
</script>

<style>

</style>