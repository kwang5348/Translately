<template>
  <div class="container-fluid m-0 p-0" style="height:100vh;">
    <div class="row">
      <div class="col-2" style="height:100vh;">
        <b-list-group style="height:100vh; border-style:none;">
          <b-list-group-item href="/" class="text-white pt-3" style="background-color:#564892; height:200px; font-size:1.2rem">
          <img src='../img/logo-letter-w.png' alt="서비스 로고" width="200"/></b-list-group-item>
          <b-list-group-item href="/contents/tutorial" class="text-white " style="background-color:#564892; height:150px; font-size:1.2rem">튜토리얼</b-list-group-item>
          <b-list-group-item href="/contents/myproject" class="text-white " style="background-color:#564892; height:150px; font-size:1.2rem">내 프로젝트</b-list-group-item>
          <b-list-group-item href="/contents/community" class="text-white " style="background-color:#564892; height:150px; font-size:1.2rem">커뮤니티</b-list-group-item>
          <b-list-group-item href="/contents/mypage" class="text-white " style="background-color:#564892; height:150px; font-size:1.2rem">마이 페이지</b-list-group-item>
          <b-list-group-item style="background-color:#564892; height:100vh;">
            <a href="#" @click="logout">
              <span><i class="fa fa-power-off text-white"></i></span>
              <span class="text-white ">로그아웃</span>
            </a>
          </b-list-group-item>
          <!-- <b-list-group-item style="background-color:#564892;">dsfs</b-list-group-item> -->
        </b-list-group>
      </div>
      <div class="col-10">
        <Navbar2/>
        <router-view @upload-file="uploadFile" @submit-upload-option="uploadOption"/>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar2 from "@/components/Navbar2.vue"
// import Sidebar from "@/components/Sidebar.vue"
// import Footer from "@/components/Footer.vue"


export default {
  name : 'Contents',
  components : {
    // Sidebar,
    Navbar2,
    // Footer,
  },
  methods: {
    logout() {
      this.$emit('logout')
      },
    uploadFile(video) {
        this.$emit('upload-file', video)
      },
    uploadOption(uploadOption) {
        this.$emit('submit-upload-option', uploadOption)
      },
    },
  }

</script>

<style scoped>
/* .list-group-item {
  boder-style: none;
} */
.list-group-item:first-child{
  border-top-left-radius:0rem;
  border-top-right-radius:0rem;
  }
.list-group-item:last-child{
  border-bottom-right-radius:0rem;
  border-bottom-left-radius:0rem;
  }
</style>