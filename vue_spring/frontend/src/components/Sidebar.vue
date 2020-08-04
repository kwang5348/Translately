<template>
	<!-- Sidebar  -->
  <nav id="sidebar" class="fixed-top inline-block">
    <div class="sidebar-header">
      <button type="button" id="sidebarCollapse" class="btn">
        <i class="fa fa-bars fa-inverse"></i>
      </button>
    </div>

    <ul class="list-unstyled components">
      <li class="active">
        <a href="/contents/tutorial">
          <i class="fas fa-home"></i>
          튜토리얼
        </a>
      </li>
      <li>
        <a href="/contents/myproject">
          <i class="fas fa-briefcase"></i>
          내 프로젝트
        </a>
      </li>
      <li>
        <a href="/contents/community">
          <i class="fas fa-image"></i>
          커뮤니티
        </a>
      </li>
      <li>
        <a href="/contents/mypage">
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

    <ul class="list-unstyled CTAs mt-5 mt-5">
      <li>
        <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download Tutorial PDF</a>
      </li>
      <li>
        <a href="/" class="article">Back To HOME</a>
      </li>
    </ul>
    <ul class="list-unstyled components mb-2">
      <li>
        <a href="#">
          <span><i class="fa fa-power-off"></i></span>
          <span><router-link @click.native="logout" to="/">로그아웃</router-link></span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name : 'Sidebar',
}
</script>

<style>

</style>