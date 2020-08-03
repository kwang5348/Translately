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
                    <a href="#">
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
            <img src='../../../src/img/head-tutorial.png' alt="튜토리얼 타이틀" height="80"/>
        </div>
        <div class="img mb-5" style="margin-top: 210px; margin-left: -580px">
            <img src='../../../src/img/prepare.png' alt="준비중 로고" height="320"/>
        </div>
    </div>

</template>

<script>

export default {
  name: 'TutorialView',
  data() {
    return {
      articles: []
    }
  },
}
</script>

<style>

</style>