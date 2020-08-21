<template>
  <div> 
  <nav class="navbar navbar-expand-lg navbar-white bg-white m-0" id="whole-navbar">
    <a class="navbar-brand ml-5" href="/">
      <img src='../img/logo-letter2.png' alt="서비스 로고" width="180"/>
    </a>
    <ul class="navbar-nav mx-auto" style="margin: 0px; color: black;">
      <li class="nav-item active ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" href="/contents/tutorial">서비스 튜토리얼<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" v-if="isLogin" href="/contents/translate">자막 변환<span class="sr-only"></span></a>
        <a class="nav-link" v-else href="/accounts/login">자막 변환<span class="sr-only"></span></a>
      </li>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" v-b-modal.modal-1 ><router-link to="#">크롬 다운로드</router-link><span class="sr-only"></span></a>
      </li>  
      <b-modal class="navbar" id="modal-1" title="크롬 익스텐션 기능을 준비 중입니다." ok-only>
        <p class="my-3 navbar">빠른 시일 내에 찾아뵐게요.</p>
        <img src='../img/extension.png' width="450px"/>
      </b-modal>
      <li class="nav-item ml-2" style="color: black; font-size: 19px;">
        <a class="nav-link" href="/faq">공지사항<span class="sr-only"></span></a>
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
        </a>
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

#modal-1 {
  font-family: 'InfinitySans-RegularA1';
  width: 800px;

}

</style>