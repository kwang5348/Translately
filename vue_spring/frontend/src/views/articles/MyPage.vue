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
        <h1>마이페이지입니다.</h1>
        <img src='../../../src/img/img-mypage.png' alt="마이페이지 임시 이미지" height="400"  style="margin-top: 50px;"/>
    </div>
</div>
</template>


<script>
export default {

}
</script>

<style>

</style>