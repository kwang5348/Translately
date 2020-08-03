<template>
    <div class="wrapper">
        <!-- Sidebar  -->
        <nav id="sidebar">
            <div class="sidebar-header">
                <button type="button" id="sidebarCollapse" class="btn">
                    <i class="fa fa-bars fa-inverse"></i>
                </button>
            </div>

            <ul class="list-unstyled components">
                <li class="active">
                    <a href="/tutorial">
                    <!-- <a href="/tutorial" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"> -->
                        <i class="fas fa-home"></i>
                        튜토리얼
                    </a>
                </li>
                <li>
                    <a href="/myproject">
                        <i class="fas fa-briefcase"></i>
                        내 프로젝트
                    </a>
                </li>
                <li>
                    <a href="/articles">
                        <i class="fas fa-image"></i>
                        커뮤니티
                    </a>
                </li>
                <li>
                    <a href="/mypage">
                        <i class="fas fa-question"></i>
                        마이 페이지
                    </a>
                </li>
                <li>
                    <!-- <a href="#" @click.native="logout" to="/accounts/logout">
                        <i class="fa fa-power-off"></i>
                        Logout
                    </a> -->
                    <!-- <a href="#">
                        <i class="fa fa-power-off"></i>
                        <router-link @click.native="logout" to="/accounts/logout">로그아웃</router-link>
                    </a> -->
                </li>
            </ul>

            <ul class="list-unstyled CTAs">
                <li>
                    <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download Tutorial PDF</a>
                </li>
                <li>
                    <a href="/" class="article">Back To HOME</a>
                </li>
            </ul>
            <ul class="list-unstyled components">
                <li>
                    <a href="#">
                        <span><i class="fa fa-power-off"></i></span>
                        <span><router-link @click.native="logout" to="/">로그아웃</router-link></span>
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Page Content  -->
        <div class="img mb-5" style="margin-top: 70px; margin-left: 300px">
            <h1>나의 프로젝트를 볼 수 있습니다.</h1>
            <img src='../../../src/img/img-myproject.png' alt="마이프로젝트 임시 이미지" height="400" style="margin-top: 50px;"/>
        </div>
        
        
    </div>
</template>

<script>
  import axios from 'axios'

  const SERVER_URL = 'http://127.0.0.1:8000'

  export default {
    name: 'MyProject',
    
    methods: {
      getUser() {
        axios.get(SERVER_URL + '/project')
      },
    }
  }
</script>

<style scoped>

</style>