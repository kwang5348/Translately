<template>
  <div> 
  <!-- Image and text  이미지에 글자 -->
  <nav class="navbar navbar-expand-lg navbar-white bg-white" id="whole-navbar">
    <a class="navbar-brand ml-5" href="/">
      <img src='../img/logo-letter2.png' alt="서비스 로고" width="180"/>
    </a>
    <ul class="navbar-nav mx-auto" style="margin: 0px; color: black;">
      <li class="nav-item active ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" href="/contents/tutorial">서비스 튜토리얼<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" href="/contents/translate">자막 변환<span class="sr-only"></span></a>
      </li>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" v-b-modal.modal-1 ><router-link to="#">다운로드</router-link><span class="sr-only"></span></a>
      </li>
      <b-modal id="modal-1">
        <p class="my-3">준비중입니다.</p>
      </b-modal>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" v-b-modal.modal-1><router-link to="#">FAQ</router-link><span class="sr-only"></span></a>
      </li>
    </ul>
    <ul class="navbar-nav">  
      <li class="nav-item form-inline mr-5" style="font-size: 19px;">
        <b-link class="nav-link" v-if="isLogin" @click="logout">
          로그아웃</b-link>
        <a v-else>
          <a><router-link to="/accounts/login">
            로그인 </router-link></a>
          <a>/</a>
          <a class="mr-3"><router-link to="/accounts/signup">
            회원가입</router-link></a>
          <!-- <a class="ml-3"><router-link to="/accounts/signup">Sign up</router-link></a> -->
        </a>
        <!-- <div id="lan">
          <b-link class="mx-1 font-weight-bolder" href="#">KOR</b-link>
          <b-link class="mx-1 font-weight-bolder text-muted" href="#">ENG</b-link>
        </div> -->
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

<style scoped>
.navbar {
  font-family: 'InfinitySans-RegularA1';
}

.v-application a {
    color: black;
}

</style>