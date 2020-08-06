<template>
  <div> 
  <!-- Image and text  이미지에 글자 -->
  <nav class="navbar navbar-expand-lg navbar-white bg-white" id="whole-navbar">
    <a class="navbar-brand" href="/">
        <img src='../img/logo-color2.png' alt="서비스 로고" width="140"/>
    </a>
    <!-- 햄버거버튼 -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <!-- <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li> -->
        <li class="nav-searchbar">
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="키워드 검색" aria-label="Search" id="search-blank">
            <button class="btn btn-dark my-2 my-sm-0" type="submit" id="sub-button">자막 검색</button>
          </form>
        </li>
        <li class="nav-item active ml-2" style="color: black; font-weight: bold; font-size: 19px;">
          <a class="nav-link"><router-link to="/contents/tutorial">서비스 더보기 ▶</router-link><span class="sr-only">(current)</span></a>
        </li>
      </ul>  
        <li class="nav-item form-inline my-2 my-lg-0">
          <a v-if="isLogin">
            <router-link @click.native="logout" to="/accounts/logout">Logout</router-link>
          </a>
          <a v-else>
            <a><router-link to="/accounts/login">
              <button type="button" class="btn btn-primary mr-1 font-weight-bold" id="login-button">LOGIN</button>
            </router-link></a>
            <a class="mr-3 font-weight-bold" style="font-size:16px;">을 하면 즐거운 일이!</a>
            <!-- <a class="ml-3"><router-link to="/accounts/signup">Sign up</router-link></a> -->
          </a>
          <div id="lan">
            <b-link class="mx-1 font-weight-bolder" href="#">KOR</b-link>
            <b-link class="mx-1 font-weight-bolder text-muted" href="#">ENG</b-link>
          </div>
        </li>
    </div>
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
  }
}
</script>

<style>

</style>